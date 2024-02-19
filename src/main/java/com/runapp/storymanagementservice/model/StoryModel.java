package com.runapp.storymanagementservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Story")
public class StoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tittle")
    private String tittle;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "difficult_level")
    private int difficultLevel;

    @Column(name = "story_image_url")
    private String storyImageUrl;

    @ManyToMany
    private List<TaskModel> taskModels;
}
