package com.runapp.storymanagementservice.service;

import com.runapp.storymanagementservice.model.TaskModel;
import com.runapp.storymanagementservice.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    public TaskModel createTask(TaskModel task) {
        return taskRepository.save(task);
    }

    // Get all tasks
    public List<TaskModel> getAllTasks() {
        return taskRepository.findAll();
    }
    public Optional<TaskModel> getTaskById(int taskId) {
        return taskRepository.findById(taskId);
    }

    // Update an existing task
    public TaskModel updateTask(TaskModel updatedTask) {
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
