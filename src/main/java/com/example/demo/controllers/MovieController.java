package com.example.demo.controllers;

import com.example.demo.domain.models.Movie;
import com.example.demo.dtos.MovieDto;
import com.example.demo.repositories.MovieRepository;
import com.example.demo.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieService movieService;

    /**
     * This endpoint returns all movies
     * @return
     */
    @GetMapping("/api/movies")
    public ResponseEntity<List<MovieDto>> findAllMovies() {
        return ResponseEntity.ok().body(movieService.findAllMovies());
    }

    /**
     * This endpoint returns movie with the id
     * @param id
     * @return
     */
    @GetMapping("/api/movies/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable(value = "id") Long id) {
        MovieDto movieDto = movieService.getMovieById(id);
        if(movieDto != null) {
            return ResponseEntity.ok().body(movieDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * This endpoint returns movie info that has the title
     * @param title
     * @return
     */
    @GetMapping("/api/movies/titles/{title}")
    public ResponseEntity<List<MovieDto>> getMoviesByTitle(@PathVariable(value = "title") String title) {
        return ResponseEntity.ok().body(movieService.getMoviesByTitle(title));
    }

    /**
     * This endpoint creates a new movie and returns the created movie
     * @param movieDto
     * @return
     */
    @PutMapping("/api/movies")
    public ResponseEntity<MovieDto> createMovie(@Validated @RequestBody MovieDto movieDto) {
        return ResponseEntity.ok().body(movieService.createMovie(movieDto));
    }

    /**
     * This endpoint updates a movie with the given id and returns updated movie
     * @param movieDto
     * @param id
     * @return
     */
    @PostMapping("/api/movies/{id}")
    public ResponseEntity<MovieDto> updateMovie(@Validated @RequestBody MovieDto movieDto, @PathVariable(value = "id") long id) {
        MovieDto updatedMovie = movieService.updateMovie(movieDto, id);
        if(updatedMovie != null) {
            return ResponseEntity.ok().body(updatedMovie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * This endpoint adds likes or dislikes to the movie with the given id and returns the movie
     * @param id
     * @param like
     * @return
     */
    @PostMapping("/api/movies/{id}/{like}")
    public ResponseEntity likeOrDislikeMovie(@PathVariable(value = "id") long id, @PathVariable(value = "like") boolean like) {
        try {
            movieService.likeOrDislikeMovie(id, like);
            return new ResponseEntity(like ? "You liked this movie" : "You disliked this movie", HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity("There is not a movie with this id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This endpoint deletes a movie with the given id
     * @param id
     * @return
     */
    @DeleteMapping("/api/movies/{id}")
    public ResponseEntity deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
