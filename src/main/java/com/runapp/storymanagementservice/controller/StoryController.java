package com.runapp.storymanagementservice.controller;

import com.runapp.storymanagementservice.dto.request.StoryRequest;
import com.runapp.storymanagementservice.model.StoryModel;
import com.runapp.storymanagementservice.service.StoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stories")
@Tag(name = "Story Management", description = "Operations related to stories")
public class StoryController {

    private final StoryService storyService;

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
}
