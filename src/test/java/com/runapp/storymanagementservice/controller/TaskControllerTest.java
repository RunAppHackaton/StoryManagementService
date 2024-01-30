package com.runapp.storymanagementservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.runapp.storymanagementservice.dto.request.TaskRequest;
import com.runapp.storymanagementservice.exceptions.GlobalExceptionHandler;
import com.runapp.storymanagementservice.model.TaskModel;
import com.runapp.storymanagementservice.service.StoryService;
import com.runapp.storymanagementservice.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void testDeleteTask_TaskNotFound() throws Exception {
        // Mock the taskService
        TaskService taskService = Mockito.mock(TaskService.class);
        Mockito.doThrow(new IllegalArgumentException()).when(taskService).deleteTask(1);
        // Create the TaskController instance
        TaskController taskController = new TaskController(taskService, null);
        // Call the deleteTask() method

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(taskController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        ResultActions resultActions = mockMvc.perform(delete("/tasks/1"));
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateTask_TaskNotFound() throws Exception {
        TaskService taskService = Mockito.mock(TaskService.class);
        StoryService storyService = Mockito.mock(StoryService.class);
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setStory_id(1);
        // Mock behavior for taskService
        Mockito.when(taskService.getTaskById(1)).thenReturn(Optional.empty());
        // Mock behavior for storyService
        Mockito.when(storyService.getStoryById(1)).thenReturn(Optional.empty());
        // Create the TaskController instance
        TaskController taskController = new TaskController(taskService, storyService);
        // Set up MockMvc
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(taskController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        // Perform the request and assert the response
        ResultActions resultActions = mockMvc.perform(put("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(taskRequest)));

        // Assert and provide additional information in case of failure
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(content().string("task with id 1 not found"))
                .andDo(MockMvcResultHandlers.print()); // Print detailed information in case of failure
    }

    @Test
    public void testUpdateTask_InvalidStoryId() throws Exception {
        //Mock the taskService and storyService
        TaskService taskService = Mockito.mock(TaskService.class);
        StoryService storyService = Mockito.mock(StoryService.class);
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setStory_id(1);
        Mockito.when(taskService.getTaskById(1)).thenReturn(Optional.of(new TaskModel()));
        Mockito.when(storyService.getStoryById(1)).thenReturn(Optional.empty());

        // Create the TaskController instance
        TaskController taskController = new TaskController(taskService, storyService);

        MockHttpServletRequestBuilder requestBuilder = put("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content((new ObjectMapper()).writeValueAsString(taskRequest));
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(taskController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        ResultActions actualPerformResult = mockMvc.perform(requestBuilder);

        // Assert the result
        actualPerformResult.andExpect(status().isNotFound())
                .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(content().string("story with id 1 not found"));
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
