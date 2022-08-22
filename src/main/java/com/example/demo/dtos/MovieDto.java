package com.example.demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class MovieDto {
    @JsonProperty("id")
    private long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("releaseYear")
    private String releaseYear;

    @JsonProperty("duration")
    private int duration;

    @JsonProperty("rating")
    private double rating;

    @JsonProperty("likes")
    private int likes;

    @JsonProperty("dislikes")
    private int dislikes;
}
