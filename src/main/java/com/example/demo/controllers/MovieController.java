package com.example.demo.controllers;

import com.example.demo.domain.models.Movie;
import com.example.demo.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/api/movies")
    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("/api/movies/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable(value = "id") Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if(movie.isPresent()) {
            return ResponseEntity.ok().body(movie.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/movies/titles/{title}")
    public ResponseEntity<List<Movie>> getMoviesByTitle(@PathVariable(value = "title") String title) {
        List<Movie> movies = movieRepository.findMoviesByTitle(title);
        if(!movies.isEmpty()) {
            return ResponseEntity.ok().body(movies);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/api/movies")
    public ResponseEntity<Movie> createMovie(@Validated @RequestBody Movie movie) {
        Movie newMovie = movieRepository.save(movie);
        return ResponseEntity.ok().body(newMovie);
    }

    @PostMapping("/api/movies/{id}")
    public ResponseEntity<Movie> updateMovie(@Validated @RequestBody Movie movie, @PathVariable(value = "id") long id) {
        Optional<Movie> movieInDB = movieRepository.findById(id);
        if(movieInDB.isPresent()) {
            movie.setId(id);
            movieRepository.save(movie);
            return ResponseEntity.ok().body(movie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/movies/{id}/{like}")
    public ResponseEntity<Movie> likeOrDislikeMovie(@PathVariable(value = "id") long id, @PathVariable(value = "like") boolean like) {
        Optional<Movie> movieInDB = movieRepository.findById(id);
        if(movieInDB.isPresent()) {
            Movie movie = movieInDB.get();
            if(like) {
                movie.setLikes(movie.getLikes() + 1);
                movieRepository.save(movie);
            } else {
                movie.setDislikes((movie.getDislikes() + 1));
                movieRepository.save(movie);
            }
            return ResponseEntity.ok().body(movie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/api/movies/{id}")
    public ResponseEntity<Movie> deleteEmployee(@PathVariable Long id) {
        movieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
