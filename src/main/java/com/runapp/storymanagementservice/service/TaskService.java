package com.runapp.storymanagementservice.service;

import com.runapp.storymanagementservice.model.TaskModel;
import com.runapp.storymanagementservice.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    public TaskModel createTask(TaskModel task) {
        LOGGER.info("Task add: {}", task);
        return taskRepository.save(task);
    }

    public List<TaskModel> getAllTasks() {
        LOGGER.info("Task get all");
        return taskRepository.findAll();
    }
    public Optional<TaskModel> getTaskById(int taskId) {
        LOGGER.info("Task get by id: {}", taskId);
        return taskRepository.findById(taskId);
    }

    public TaskModel updateTask(TaskModel updatedTask) {
        LOGGER.info("Task update: {}", updatedTask);
        // Check if the task with the given ID exists
        Optional<TaskModel> existingTask = taskRepository.findById(updatedTask.getId());
        if (existingTask.isPresent()) {
            // Set the ID of the updated task to match the existing task's ID
            return taskRepository.save(updatedTask);
        } else {
            // Task with the given ID does not exist
            throw new IllegalArgumentException("Task with ID " + updatedTask.getId() + " not found.");
        }
    }
    public void deleteTask(int taskId) {
        LOGGER.info("Task delete by id: {}", taskId);
        // Check if the task with the given ID exists
        Optional<TaskModel> existingTask = taskRepository.findById(taskId);
        if (existingTask.isPresent()) {
            taskRepository.deleteById(taskId);
        } else {
            // Task with the given ID does not exist
            throw new IllegalArgumentException("Task with ID " + taskId + " not found.");
        }
    }
}
