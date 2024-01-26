package com.runapp.storymanagementservice.controller;

import com.runapp.storymanagementservice.dto.request.TaskRequest;
import com.runapp.storymanagementservice.model.TaskModel;
import com.runapp.storymanagementservice.service.StoryService;
import com.runapp.storymanagementservice.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskControllerTest {

    @Test
    public void testDeleteTask() {
        // Mock the taskService
        TaskService taskService = Mockito.mock(TaskService.class);

        // Create the TaskController instance
        TaskController taskController = new TaskController(taskService, null);

        // Call the deleteTask() method
        ResponseEntity<Void> result = taskController.deleteTask(1);

        // Assert the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    public void testDeleteTask_TaskNotFound() {
        // Mock the taskService
        TaskService taskService = Mockito.mock(TaskService.class);
        Mockito.doThrow(new IllegalArgumentException()).when(taskService).deleteTask(1);
        // Create the TaskController instance
        TaskController taskController = new TaskController(taskService, null);
        // Call the deleteTask() method
        ResponseEntity<Void> result = taskController.deleteTask(1);
        // Assert the result
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testUpdateTask_TaskNotFound() {
        // Mock the taskService and storyService
        TaskService taskService = Mockito.mock(TaskService.class);
        StoryService storyService = Mockito.mock(StoryService.class);
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setStory_id(1);
        Mockito.when(taskService.getTaskById(1)).thenReturn(Optional.empty());

        // Create the TaskController instance
        TaskController taskController = new TaskController(taskService, storyService);

        // Call the updateTask() method
        ResponseEntity<Object> result = taskController.updateTask(1, taskRequest);

        // Assert the result
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("task with id 1 not found", result.getBody());
    }

    @Test
    public void testUpdateTask_InvalidStoryId() {
        //Mock the taskService and storyService
        TaskService taskService = Mockito.mock(TaskService.class);
        StoryService storyService = Mockito.mock(StoryService.class);
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setStory_id(1);
        Mockito.when(taskService.getTaskById(1)).thenReturn(Optional.of(new TaskModel()));
        Mockito.when(storyService.getStoryById(1)).thenReturn(Optional.empty());

        // Create the TaskController instance
        TaskController taskController = new TaskController(taskService, storyService);

        // Call the updateTask() method
        ResponseEntity<Object> result = taskController.updateTask(1, taskRequest);

        // Assert the result
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("story with id 1 not found", result.getBody());
    }


    @Test
    public void testGetTaskById() {
        // Mock the taskService
        TaskService taskService = Mockito.mock(TaskService.class);
        TaskModel task = new TaskModel();
        Mockito.when(taskService.getTaskById(1)).thenReturn(Optional.of(task));

        // Create the TaskController instance
        TaskController taskController = new TaskController(taskService, null);

        // Call the getTaskById() method
        ResponseEntity<TaskModel> result = taskController.getTaskById(1);

        // Assert the result
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(task, result.getBody());
    }
}
