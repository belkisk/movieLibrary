package com.example.demo.domain.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "movie")
public class Movie {

    private @Id @GeneratedValue Long id;

    private String title;
    private String description;
    private String releaseYear;
    private int duration;
    private double rating;
    private int likes;
    private int dislikes;
}
