package com.runapp.storymanagementservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Task")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "task_tittle")
    private String taskTittle;

    @Column(name = "task_description")
    private String taskDescription;

    @Column(name = "task_reward_id")
    private int taskRewardId;

    @Column(name = "task_image_url")
    private String taskImageUrl;

    @ManyToOne
    @JoinColumn(name = "story_id")
    private StoryModel storyModel;
}
