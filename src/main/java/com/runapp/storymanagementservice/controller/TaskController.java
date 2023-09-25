package com.runapp.storymanagementservice.controller;

import com.runapp.storymanagementservice.dto.request.DeleteStorageRequest;
import com.runapp.storymanagementservice.dto.request.StoryDeleteRequest;
import com.runapp.storymanagementservice.dto.request.TaskDeleteRequest;
import com.runapp.storymanagementservice.dto.request.TaskRequest;
import com.runapp.storymanagementservice.dto.response.DeleteResponse;
import com.runapp.storymanagementservice.feignClient.StorageServiceClient;
import com.runapp.storymanagementservice.model.StoryModel;
import com.runapp.storymanagementservice.model.TaskModel;
import com.runapp.storymanagementservice.service.StoryService;
import com.runapp.storymanagementservice.service.TaskService;
import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Task Management", description = "Operations related to tasks")
public class TaskController {

    private final TaskService taskService;

    @Value("${storage-directory}")
    private String storageDirectory;

    @Autowired
    private StorageServiceClient storageServiceClient;
    private final StoryService storyService;

    @Autowired
    public TaskController(TaskService taskService, StoryService storyService) {
        this.taskService = taskService;
        this.storyService = storyService;
    }

    @GetMapping
    @Operation(summary = "Get all tasks", description = "Retrieve a list of all tasks")
    public List<TaskModel> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "Get a task by ID", description = "Retrieve a task by its ID")
    @ApiResponse(responseCode = "200", description = "Task found", content = @Content(schema = @Schema(implementation = TaskModel.class)))
    @ApiResponse(responseCode = "404", description = "Task not found")
    public ResponseEntity<TaskModel> getTaskById(
            @Parameter(description = "Task ID", required = true)
            @PathVariable int taskId) {
        Optional<TaskModel> task = taskService.getTaskById(taskId);
        return task.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Create a new task", description = "Create a new task with the provided data")
    @ApiResponse(responseCode = "201", description = "Task created", content = @Content(schema = @Schema(implementation = TaskModel.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<Object> createTask(
            @Parameter(description = "Task data", required = true)
            @RequestBody TaskRequest taskRequest) {
        Optional<StoryModel> storyModel = storyService.getStoryById(taskRequest.getStory_id());
        if (storyModel.isEmpty())
            return ResponseEntity.badRequest().body("Story with id " + taskRequest.getStory_id() + " not found");
        TaskModel createdTask = taskService.createTask(taskRequest.toTaskModel(storyModel.orElse(null)));
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}")
    @Operation(summary = "Update a task", description = "Update an existing task with the provided data")
    @ApiResponse(responseCode = "200", description = "Task updated", content = @Content(schema = @Schema(implementation = TaskModel.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Task not found")
    public ResponseEntity<Object> updateTask(
            @Parameter(description = "Updated task data", required = true)
            @PathVariable("taskId") int taskId,
            @RequestBody TaskRequest taskRequest) {
        try {
            Optional<TaskModel> task = taskService.getTaskById(taskId);
            if (task.isEmpty())
                return new ResponseEntity<>("task with id " + taskId + " not found", HttpStatus.NOT_FOUND);
            Optional<StoryModel> storyModel = storyService.getStoryById(taskRequest.getStory_id());
            if (storyModel.isEmpty())
                return new ResponseEntity<>("story with id " + taskRequest.getStory_id() + " not found", HttpStatus.NOT_FOUND);
            TaskModel taskModel = taskRequest.toTaskModel(storyModel.orElse(null));
            taskModel.setId(taskId);
            taskService.updateTask(taskModel);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "Delete a task", description = "Delete a task by its ID")
    @ApiResponse(responseCode = "204", description = "Task deleted")
    @ApiResponse(responseCode = "404", description = "Task not found")
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "Task ID", required = true)
            @PathVariable int taskId) {
        try {
            taskService.deleteTask(taskId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/upload-image")
    @Operation(summary = "Upload an image for a task", description = "Upload an image file for a specific task by providing the file and task ID.")
    @ApiResponse(responseCode = "200", description = "Image uploaded successfully", content = @Content(schema = @Schema(implementation = TaskModel.class), mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "Task not found")
    public ResponseEntity<Object> uploadImage(
            @Parameter(description = "Image file to upload", required = true) @RequestParam("file") MultipartFile file,
            @Parameter(description = "ID of the task to associate with the uploaded image", required = true) @RequestParam("task_id") int taskId
    ) {
        Optional<TaskModel> optionalTaskModel = taskService.getTaskById(taskId);
        if (optionalTaskModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with id " + taskId + " not found");
        } else {
            TaskModel taskModel = optionalTaskModel.orElse(null);
            taskModel.setTaskImageUrl(storageServiceClient.uploadFile(file, storageDirectory).getFile_uri());
            taskService.updateTask(taskModel);
            return ResponseEntity.ok().body(taskModel);
        }
    }

    @DeleteMapping("/delete-image")
    @Operation(summary = "Delete an image associated with a task", description = "Delete the image associated with a task by providing the image URI and task details.")
    @ApiResponse(responseCode = "200", description = "Image deleted successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Object> deleteImage(@Parameter(description = "Request body containing task ID and image URI", required = true) @RequestBody TaskDeleteRequest taskDeleteRequest) {
        Optional<TaskModel> optionalTaskModel = taskService.getTaskById(taskDeleteRequest.getTask_id());
        if (optionalTaskModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with id " + taskDeleteRequest.getTask_id() + " not found");
        }
        TaskModel taskModel = optionalTaskModel.orElse(null);
        taskModel.setTaskImageUrl("DEFAULT");
        taskService.updateTask(taskModel);
        try {
            storageServiceClient.deleteFile(new DeleteStorageRequest(taskDeleteRequest.getFile_uri(), storageDirectory));
            // Обработка успешного удаления
            return ResponseEntity.ok().build();
        } catch (FeignException.InternalServerError e) {
            // Обработка исключения, которое может возникнуть при вызове deleteFile
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DeleteResponse("the image does not exist or the data was transferred incorrectly"));
        }
    }
}
