package com.runapp.storymanagementservice.repository;

import com.runapp.storymanagementservice.model.StoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends JpaRepository<StoryModel, Integer> {
}
