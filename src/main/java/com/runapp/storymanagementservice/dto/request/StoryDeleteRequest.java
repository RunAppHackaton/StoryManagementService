package com.runapp.storymanagementservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryDeleteRequest {

    private String file_uri;
    private int story_id;
}
