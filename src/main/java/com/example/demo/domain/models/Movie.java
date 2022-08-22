package com.example.demo.domain.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "movie")
public class Movie {

    @Column
    private @Id @GeneratedValue Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column(name="release_year")
    private String releaseYear;

    @Column
    private int duration;

    @Column
    private double rating;

    @Column
    private int likes;

    @Column
    private int dislikes;
}
