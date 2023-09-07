package com.runapp.storymanagementservice.repository;

import com.runapp.storymanagementservice.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, Integer> {
}
