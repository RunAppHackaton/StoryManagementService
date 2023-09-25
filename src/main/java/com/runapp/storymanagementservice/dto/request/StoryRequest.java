package com.runapp.storymanagementservice.dto.request;

import com.runapp.storymanagementservice.model.StoryModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryRequest {

    private String tittle;

    private String description;

    private String category;

    private int difficultLevel;

    public StoryModel toStoryModel() {
        StoryModel storyModel = new StoryModel();
        storyModel.setTittle(this.tittle);
        storyModel.setDescription(this.description);
        storyModel.setCategory(this.category);
        storyModel.setDifficultLevel(this.difficultLevel);
        storyModel.setStoryImageUrl("DEFAULT");
        return storyModel;
    }
}
