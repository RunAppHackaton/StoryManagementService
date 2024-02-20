package com.runapp.storymanagementservice.service;

import com.runapp.storymanagementservice.model.StoryModel;
import com.runapp.storymanagementservice.repository.StoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class StoryServiceTest {

    @Mock
    private StoryRepository storyRepository;

    @InjectMocks
    private StoryService storyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createStoryTest() {
        StoryModel story = new StoryModel();
        when(storyRepository.save(story)).thenReturn(story);

        StoryModel savedStory = storyService.createStory(story);

        assertNotNull(savedStory);
        verify(storyRepository, times(1)).save(story);
    }

    @Test
    void getAllStoriesTest() {
        List<StoryModel> storyList = new ArrayList<>();
        when(storyRepository.findAll()).thenReturn(storyList);

        List<StoryModel> retrievedStories = storyService.getAllStories();

        assertEquals(storyList, retrievedStories);
        verify(storyRepository, times(1)).findAll();
    }

    @Test
    void getStoryByIdTest() {
        int storyId = 1;
        StoryModel story = new StoryModel();
        Optional<StoryModel> optionalStory = Optional.of(story);
        when(storyRepository.findById(storyId)).thenReturn(optionalStory);

        Optional<StoryModel> retrievedStory = storyService.getStoryById(storyId);

        assertTrue(retrievedStory.isPresent());
        assertEquals(story, retrievedStory.get());
        verify(storyRepository, times(1)).findById(storyId);
    }

    @Test
    void updateExistingStoryTest() {
        StoryModel updatedStory = new StoryModel();
        updatedStory.setId(1);
        Optional<StoryModel> existingStory = Optional.of(new StoryModel());

        when(storyRepository.findById(updatedStory.getId())).thenReturn(existingStory);
        when(storyRepository.save(updatedStory)).thenReturn(updatedStory);

        StoryModel savedStory = storyService.updateStory(updatedStory);

        assertNotNull(savedStory);
        assertEquals(updatedStory.getId(), savedStory.getId());
        verify(storyRepository, times(1)).findById(updatedStory.getId());
        verify(storyRepository, times(1)).save(updatedStory);
    }

    @Test
    void updateNonExistingStoryTest() {
        StoryModel updatedStory = new StoryModel();
        updatedStory.setId(1);

        when(storyRepository.findById(updatedStory.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> storyService.updateStory(updatedStory));
        verify(storyRepository, times(1)).findById(updatedStory.getId());
        verify(storyRepository, never()).save(updatedStory);
    }

    @Test
    void deleteExistingStoryTest() {
        int storyId = 1;
        Optional<StoryModel> existingStory = Optional.of(new StoryModel());

        when(storyRepository.findById(storyId)).thenReturn(existingStory);

        storyService.deleteStory(storyId);

        verify(storyRepository, times(1)).findById(storyId);
        verify(storyRepository, times(1)).deleteById(storyId);
    }

    @Test
    void deleteNonExistingStoryTest() {
        int storyId = 1;

        when(storyRepository.findById(storyId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> storyService.deleteStory(storyId));
        verify(storyRepository, times(1)).findById(storyId);
        verify(storyRepository, never()).deleteById(storyId);
    }
}
