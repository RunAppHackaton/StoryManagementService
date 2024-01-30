package com.runapp.storymanagementservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.runapp.storymanagementservice.dto.request.StoryRequest;
import com.runapp.storymanagementservice.exceptions.GlobalExceptionHandler;
import com.runapp.storymanagementservice.model.StoryModel;
import com.runapp.storymanagementservice.service.StoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class StoryControllerTest {

    @Mock
    private StoryService storyService;

    @InjectMocks
    private StoryController storyController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(storyController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void testGetAllStories() throws Exception {
        // Prepare test data
        List<StoryModel> stories = new ArrayList<>();
        // Add some StoryModel objects to the stories list

        // Mock the necessary dependencies
        when(storyService.getAllStories()).thenReturn(stories);

        // Perform the GET request
        mockMvc.perform(get("/stories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(stories.size())))
                // Add assertions for the properties of the expected response list
                .andReturn();
    }

    @Test
    public void testGetStoryById() throws Exception {
        // Prepare test data
        int storyId = 1;

        Optional<StoryModel> story = Optional.of(new StoryModel());
        // Set the properties of the story object

        // Mock the necessary dependencies
        when(storyService.getStoryById(storyId)).thenReturn(story);

        // Perform the GET request
        mockMvc.perform(get("/stories/{storyId}", storyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(story.get().getId()))
                // Add assertions for other properties of the expected response
                .andReturn();
    }

    @Test
    public void testGetStoryByIdNotFound() throws Exception {
        // Prepare test data
        int storyId = 1;

        Optional<StoryModel> story = Optional.empty();

        // Mock the necessary dependencies
        when(storyService.getStoryById(storyId)).thenReturn(story);

        // Perform the GET request
        mockMvc.perform(get("/stories/{storyId}", storyId))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void testCreateStory() throws Exception {
        // Prepare test data
        StoryRequest storyRequest = new StoryRequest();
        // Set the properties of the storyRequest object

        StoryModel createdStory = new StoryModel();
        // Set the properties of the createdStory object

        // Mock the necessary dependencies
        when(storyService.createStory(any(StoryModel.class))).thenReturn(createdStory);

        // Perform the POST request
        mockMvc.perform(post("/stories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(storyRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(createdStory.getId()))
                // Add assertions for other properties of the expected response
                .andReturn();
    }

    @Test
    public void testUpdateStory() throws Exception {
        // Prepare test data
        int storyId = 1;

        StoryRequest storyRequest = new StoryRequest();
        // Set the properties of the storyRequest object

        StoryModel updatedStory = new StoryModel();
        updatedStory.setId(1);
        // Set the properties of the updatedStory object

        // Mock the necessary dependencies
        when(storyService.updateStory(any(StoryModel.class))).thenReturn(updatedStory);

        // Perform the PUT request
        mockMvc.perform(put("/stories/{storyId}", storyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(storyRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedStory.getId()))
                // Add assertions for other properties of the expected response
                .andReturn();
    }

    @Test
    public void testUpdateStoryNotFound() throws Exception {
        int storyId = 1;

        StoryRequest storyRequest = new StoryRequest();

        // Mock the necessary dependencies
        when(storyService.updateStory(any(StoryModel.class))).thenThrow(new IllegalArgumentException());

        // Perform the PUT request
        mockMvc.perform(put("/stories/{storyId}", storyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(storyRequest)))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void testDeleteStory() throws Exception {
        // Prepare test data
        int storyId = 1;

        // Perform the DELETE request
        mockMvc.perform(delete("/stories/{storyId}", storyId))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    // Helper method to convert an object to JSON string
    private static String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}