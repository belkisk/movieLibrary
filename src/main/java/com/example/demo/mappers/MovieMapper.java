package com.example.demo.mappers;

import com.example.demo.domain.models.Movie;
import com.example.demo.dtos.MovieDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDto toMovieDto(Movie movie);

    Movie toMovie(MovieDto movieDto);
}
