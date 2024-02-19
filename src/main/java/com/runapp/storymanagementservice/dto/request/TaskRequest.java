package com.runapp.storymanagementservice.dto.request;

import com.runapp.storymanagementservice.model.StoryModel;
import com.runapp.storymanagementservice.model.TaskModel;
import lombok.Data;

@Data
public class TaskRequest {

    private String taskTitle;

    private String taskDescription;

    private int taskRewardId;

    private String taskImageUrl;

    private int story_id;

    public TaskModel toTaskModel(StoryModel storyModel) {
        TaskModel taskModel = new TaskModel();
        taskModel.setTaskTitle(this.taskTitle);
        taskModel.setTaskDescription(this.taskDescription);
        taskModel.setTaskRewardId(this.taskRewardId);
        taskModel.setTaskImageUrl(this.taskImageUrl);
        return taskModel;
    }
}
