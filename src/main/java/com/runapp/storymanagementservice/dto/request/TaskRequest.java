package com.runapp.storymanagementservice.dto.request;

import com.runapp.storymanagementservice.model.StoryModel;
import com.runapp.storymanagementservice.model.TaskModel;
import lombok.Data;

@Data
public class TaskRequest {

    private String taskTittle;

    private String taskDescription;

    private int taskRewardId;

    private String taskImageUrl;

    private int story_id;

    public TaskModel toTaskModel(StoryModel storyModel) {
        TaskModel taskModel = new TaskModel();
        taskModel.setTaskTittle(this.taskTittle);
        taskModel.setTaskDescription(this.taskDescription);
        taskModel.setTaskRewardId(this.taskRewardId);
        taskModel.setTaskImageUrl(this.taskImageUrl);
        taskModel.setStoryModel(storyModel);
        return taskModel;
    }
}
