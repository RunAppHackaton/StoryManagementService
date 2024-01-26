package com.runapp.storymanagementservice.service;

import com.runapp.storymanagementservice.model.StoryModel;
import com.runapp.storymanagementservice.repository.StoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoryService {

    private final StoryRepository storyRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(StoryService.class);

    @Autowired
    public StoryService(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }
    public StoryModel createStory(StoryModel story) {
        LOGGER.info("Story add: {}", story);
        return storyRepository.save(story);
    }

    // Get all stories
    public List<StoryModel> getAllStories() {
        LOGGER.info("Story get all");
        return storyRepository.findAll();
    }
    public Optional<StoryModel> getStoryById(int storyId) {
        LOGGER.info("Story get by id: {}", storyId);
        return storyRepository.findById(storyId);
    }
    public StoryModel updateStory(StoryModel updatedStory) {
        LOGGER.info("Story update: {}", updatedStory);
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
        LOGGER.info("Story delete by id: {}", storyId);
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
