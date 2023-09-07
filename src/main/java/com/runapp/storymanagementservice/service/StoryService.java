package com.runapp.storymanagementservice.service;

import com.runapp.storymanagementservice.model.StoryModel;
import com.runapp.storymanagementservice.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoryService {

    private final StoryRepository storyRepository;

    @Autowired
    public StoryService(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }
    public StoryModel createStory(StoryModel story) {
        return storyRepository.save(story);
    }

    // Get all stories
    public List<StoryModel> getAllStories() {
        return storyRepository.findAll();
    }
    public Optional<StoryModel> getStoryById(int storyId) {
        return storyRepository.findById(storyId);
    }
    public StoryModel updateStory(StoryModel updatedStory) {
        int storyId = updatedStory.getId();
        // Check if the story with the given ID exists
        Optional<StoryModel> existingStory = storyRepository.findById(storyId);
        if (existingStory.isPresent()) {
            // Set the ID of the updated story to match the existing story's ID
            updatedStory.setId(storyId);
            return storyRepository.save(updatedStory);
        } else {
            // Story with the given ID does not exist
            throw new IllegalArgumentException("Story with ID " + storyId + " not found.");
        }
    }
    public void deleteStory(int storyId) {
        // Check if the story with the given ID exists
        Optional<StoryModel> existingStory = storyRepository.findById(storyId);
        if (existingStory.isPresent()) {
            storyRepository.deleteById(storyId);
        } else {
            // Story with the given ID does not exist
            throw new IllegalArgumentException("Story with ID " + storyId + " not found.");
        }
    }
}
