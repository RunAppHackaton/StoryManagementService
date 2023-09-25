package com.runapp.storymanagementservice.controller;

import com.runapp.storymanagementservice.dto.request.DeleteStorageRequest;
import com.runapp.storymanagementservice.dto.request.StoryDeleteRequest;
import com.runapp.storymanagementservice.dto.request.StoryRequest;
import com.runapp.storymanagementservice.dto.response.DeleteResponse;
import com.runapp.storymanagementservice.feignClient.StorageServiceClient;
import com.runapp.storymanagementservice.model.StoryModel;
import com.runapp.storymanagementservice.service.StoryService;
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
@RequestMapping("/stories")
@Tag(name = "Story Management", description = "Operations related to stories")
public class StoryController {

    private final StoryService storyService;

    @Value("${storage-directory}")
    private String storageDirectory;

    @Autowired
    private StorageServiceClient storageServiceClient;

    @Autowired
    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping
    @Operation(summary = "Get all stories", description = "Retrieve a list of all stories")
    public List<StoryModel> getAllStories() {
        return storyService.getAllStories();
    }

    @GetMapping("/{storyId}")
    @Operation(summary = "Get a story by ID", description = "Retrieve a story by its ID")
    @ApiResponse(responseCode = "200", description = "Story found", content = @Content(schema = @Schema(implementation = StoryModel.class)))
    @ApiResponse(responseCode = "404", description = "Story not found")
    public ResponseEntity<StoryModel> getStoryById(
            @Parameter(description = "Story ID", required = true)
            @PathVariable int storyId) {
        Optional<StoryModel> story = storyService.getStoryById(storyId);
        return story.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Create a new story", description = "Create a new story with the provided data")
    @ApiResponse(responseCode = "201", description = "Story created", content = @Content(schema = @Schema(implementation = StoryRequest.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<StoryModel> createStory(
            @Parameter(description = "Story data", required = true)
            @RequestBody StoryRequest storyRequest) {
        StoryModel createdStory = storyService.createStory(storyRequest.toStoryModel());
        return new ResponseEntity<>(createdStory, HttpStatus.CREATED);
    }

    @PutMapping("/{storyId}")
    @Operation(summary = "Update a story", description = "Update an existing story with the provided data")
    @ApiResponse(responseCode = "200", description = "Story updated", content = @Content(schema = @Schema(implementation = StoryRequest.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Story not found")
    public ResponseEntity<StoryModel> updateStory(
            @Parameter(description = "Updated story data", required = true)
            @PathVariable("storyId") int storyId,
            @RequestBody StoryRequest storyRequest) {
        try {
            StoryModel story = storyRequest.toStoryModel();
            story.setId(storyId);
            storyService.updateStory(story);
            return new ResponseEntity<>(story, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{storyId}")
    @Operation(summary = "Delete a story", description = "Delete a story by its ID")
    @ApiResponse(responseCode = "204", description = "Story deleted")
    @ApiResponse(responseCode = "404", description = "Story not found")
    public ResponseEntity<Void> deleteStory(
            @Parameter(description = "Story ID", required = true)
            @PathVariable int storyId) {
        try {
            storyService.deleteStory(storyId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/upload-image")
    @Operation(summary = "Upload an image for a story", description = "Upload an image file for a specific story by providing the file and story ID.")
    @ApiResponse(responseCode = "200", description = "Image uploaded successfully", content = @Content(schema = @Schema(implementation = StoryModel.class), mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "Story not found")
    public ResponseEntity<Object> uploadImage(
            @Parameter(description = "Image file to upload", required = true) @RequestParam("file") MultipartFile file,
            @Parameter(description = "ID of the story to associate with the uploaded image", required = true) @RequestParam("story_id") int storyId) {
        Optional<StoryModel> optionalStoryModel = storyService.getStoryById(storyId);
        if (optionalStoryModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Story with id " + storyId + " not found");
        } else {
            StoryModel storyModel = optionalStoryModel.orElse(null);
            storyModel.setStoryImageUrl(storageServiceClient.uploadFile(file, storageDirectory).getFile_uri());
            storyService.updateStory(storyModel);
            return ResponseEntity.ok().body(storyModel);
        }
    }

    @DeleteMapping("/delete-image")
    @Operation(summary = "Delete an image associated with a story", description = "Delete the image associated with a story by providing the image URI and story details.")
    @ApiResponse(responseCode = "200", description = "Image deleted successfully")
    @ApiResponse(responseCode = "404", description = "Story not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Object> deleteImage(@Parameter(description = "Request body containing story ID and image URI", required = true) @RequestBody StoryDeleteRequest storyDeleteRequest) {
        Optional<StoryModel> optionalStoryModel = storyService.getStoryById(storyDeleteRequest.getStory_id());
        if (optionalStoryModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Story with id " + storyDeleteRequest.getStory_id() + " not found");
        }
        StoryModel storyModel = optionalStoryModel.orElse(null);
        storyModel.setStoryImageUrl("DEFAULT");
        storyService.updateStory(storyModel);
        try {
            storageServiceClient.deleteFile(new DeleteStorageRequest(storyDeleteRequest.getFile_uri(), storageDirectory));
            return ResponseEntity.ok().build();
        } catch (FeignException.InternalServerError e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DeleteResponse("the image does not exist or the data was transferred incorrectly"));
        }
    }
}
