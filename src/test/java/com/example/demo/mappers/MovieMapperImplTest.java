package com.example.demo.mappers;

import com.example.demo.domain.models.Movie;
import com.example.demo.dtos.MovieDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieMapperImplTest {
    private MovieMapper movieMapper = Mappers.getMapper(MovieMapper.class);

    @Test
    public void movieToMovieDto_whenMaps_thenCorrect() {
        //given
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Movie Title");
        movie.setDescription("Movie Description");
        movie.setReleaseYear("2022");
        movie.setDuration(120);
        movie.setRating(8.05);
        movie.setLikes(2);
        movie.setDislikes(3);

        //when
        MovieDto movieDto = movieMapper.toMovieDto(movie);

        //then
        assertEquals(movie.getId(), movieDto.getId());
        assertEquals(movie.getTitle(), movieDto.getTitle());
        assertEquals(movie.getDescription(), movieDto.getDescription());
        assertEquals(movie.getReleaseYear(), movieDto.getReleaseYear());
        assertEquals(movie.getDuration(), movieDto.getDuration());
        assertEquals(movie.getRating(), movieDto.getRating());
        assertEquals(movie.getLikes(), movieDto.getLikes());
        assertEquals(movie.getDislikes(), movieDto.getDislikes());
    }

    @Test
    public void movieDtoToMovie_whenMaps_thenCorrect() {
        //given
        MovieDto movieDto = new MovieDto();
        movieDto.setId(1L);
        movieDto.setTitle("Movie Title");
        movieDto.setDescription("Movie Description");
        movieDto.setReleaseYear("2022");
        movieDto.setDuration(120);
        movieDto.setRating(8.05);
        movieDto.setLikes(2);
        movieDto.setDislikes(3);

        //when
        Movie movie = movieMapper.toMovie(movieDto);

        //then
        assertEquals(movieDto.getId(), movie.getId());
        assertEquals(movieDto.getTitle(), movie.getTitle());
        assertEquals(movieDto.getDescription(), movie.getDescription());
        assertEquals(movieDto.getReleaseYear(), movie.getReleaseYear());
        assertEquals(movieDto.getDuration(), movie.getDuration());
        assertEquals(movieDto.getRating(), movie.getRating());
        assertEquals(movieDto.getLikes(), movie.getLikes());
        assertEquals(movieDto.getDislikes(), movie.getDislikes());
    }
}
