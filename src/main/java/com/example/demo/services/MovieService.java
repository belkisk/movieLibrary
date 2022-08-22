package com.example.demo.services;

import com.example.demo.dtos.MovieDto;

import java.util.List;
import java.util.NoSuchElementException;

public interface MovieService {

    List<MovieDto> findAllMovies();

    MovieDto getMovieById(long id);

    List<MovieDto> getMoviesByTitle(String title);

    MovieDto createMovie(MovieDto movieDto);

    MovieDto updateMovie(MovieDto movieDto, long id);

    void likeOrDislikeMovie(long id, boolean like) throws NoSuchElementException;

    void deleteMovie(long id);
}
