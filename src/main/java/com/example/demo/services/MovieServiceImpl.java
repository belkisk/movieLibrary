package com.example.demo.services;

import com.example.demo.domain.models.Movie;
import com.example.demo.dtos.MovieDto;
import com.example.demo.mappers.MovieMapper;
import com.example.demo.repositories.MovieRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieMapper movieMapper;

    @Override
    public List<MovieDto> findAllMovies() {
        List<Movie> movieList = movieRepository.findAll();
        return movieRepository.findAll().stream().map(movieMapper::toMovieDto).collect(Collectors.toList());
    }

    @Override
    public MovieDto getMovieById(long id) {
        Optional<Movie> movieInDB =  movieRepository.findById(id);
        if(movieInDB.isPresent()) {
            return movieMapper.toMovieDto(movieInDB.get());
        } else {
            return null;
        }
    }

    @Override
    public List<MovieDto> getMoviesByTitle(String title) {
        return movieRepository.findMoviesByTitle(title).stream().map(movieMapper::toMovieDto).collect(Collectors.toList());
    }

    @Override
    public MovieDto createMovie(MovieDto movieDto) {
        return movieMapper.toMovieDto(movieRepository.save(movieMapper.toMovie(movieDto)));
    }

    @Override
    public MovieDto updateMovie(MovieDto movieDto, long id) {
        Optional<Movie> movieInDB = movieRepository.findById(id);
        if(movieInDB.isPresent()) {
            movieDto.setId(id);
            return movieMapper.toMovieDto(movieRepository.save(movieMapper.toMovie(movieDto)));
        } else {
            return null;
        }
    }

    @Override
    public void likeOrDislikeMovie(long id, boolean like) throws NoSuchElementException {
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
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void deleteMovie(long id) {
        movieRepository.deleteById(id);
    }
}
