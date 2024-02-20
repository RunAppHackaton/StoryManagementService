package com.runapp.storymanagementservice.service;

import com.runapp.storymanagementservice.model.TaskModel;
import com.runapp.storymanagementservice.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private Logger logger;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTaskTest() {
        TaskModel task = new TaskModel();
        when(taskRepository.save(task)).thenReturn(task);

        TaskModel savedTask = taskService.createTask(task);

        assertNotNull(savedTask);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void getAllTasksTest() {
        List<TaskModel> taskList = new ArrayList<>();
        when(taskRepository.findAll()).thenReturn(taskList);

        List<TaskModel> retrievedTasks = taskService.getAllTasks();

        assertEquals(taskList, retrievedTasks);
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getTaskByIdTest() {
        int taskId = 1;
        TaskModel task = new TaskModel();
        Optional<TaskModel> optionalTask = Optional.of(task);
        when(taskRepository.findById(taskId)).thenReturn(optionalTask);

        Optional<TaskModel> retrievedTask = taskService.getTaskById(taskId);

        assertTrue(retrievedTask.isPresent());
        assertEquals(task, retrievedTask.get());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void updateExistingTaskTest() {
        TaskModel updatedTask = new TaskModel();
        updatedTask.setId(1);
        Optional<TaskModel> existingTask = Optional.of(new TaskModel());

        when(taskRepository.findById(updatedTask.getId())).thenReturn(existingTask);
        when(taskRepository.save(updatedTask)).thenReturn(updatedTask);

        TaskModel savedTask = taskService.updateTask(updatedTask);

        assertNotNull(savedTask);
        assertEquals(updatedTask.getId(), savedTask.getId());
        verify(taskRepository, times(1)).findById(updatedTask.getId());
        verify(taskRepository, times(1)).save(updatedTask);
    }

    @Test
    void updateNonExistingTaskTest() {
        TaskModel updatedTask = new TaskModel();
        updatedTask.setId(1);

        when(taskRepository.findById(updatedTask.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> taskService.updateTask(updatedTask));
        verify(taskRepository, times(1)).findById(updatedTask.getId());
        verify(taskRepository, never()).save(updatedTask);
    }

    @Test
    void deleteExistingTaskTest() {
        int taskId = 1;
        Optional<TaskModel> existingTask = Optional.of(new TaskModel());

        when(taskRepository.findById(taskId)).thenReturn(existingTask);

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    void deleteNonExistingTaskTest() {
        int taskId = 1;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> taskService.deleteTask(taskId));
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, never()).deleteById(taskId);
    }
}
