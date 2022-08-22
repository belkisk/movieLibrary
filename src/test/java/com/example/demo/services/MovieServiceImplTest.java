package com.example.demo.services;

import com.example.demo.domain.models.Movie;
import com.example.demo.dtos.MovieDto;
import com.example.demo.mappers.MovieMapper;
import com.example.demo.mappers.MovieMapperImplTest;
import com.example.demo.repositories.MovieRepository;
import com.sun.istack.NotNull;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceImplTest {

    @InjectMocks
    MovieServiceImpl movieService;

    @Mock
    MovieRepository movieRepository;

    @Mock
    MovieMapper movieMapper;

    private List<Movie> movieList;
    private List<MovieDto> movieDtoList;

    @Before
    public void setup() {
        movieList = new ArrayList<>();
        Movie movie1 = new Movie();
        movie1.setId(1L);
        movie1.setTitle("Movie Title 1");
        movie1.setDescription("Movie Description 1");
        movie1.setReleaseYear("1991");
        movie1.setDuration(120);
        movie1.setRating(8.05);
        movie1.setLikes(2);
        movie1.setDislikes(3);

        Movie movie2 = new Movie();
        movie2.setId(2L);
        movie2.setTitle("Movie Title 2");
        movie2.setDescription("Movie Description 2");
        movie2.setReleaseYear("1992");
        movie2.setDuration(120);
        movie2.setRating(8.05);
        movie2.setLikes(2);
        movie2.setDislikes(3);

        movieList.add(movie1);
        movieList.add(movie2);

        movieDtoList = new ArrayList<>();
        MovieDto movieDto1 = new MovieDto();
        movieDto1.setId(1L);
        movieDto1.setTitle("Movie Title 1");
        movieDto1.setDescription("Movie Description 1");
        movieDto1.setReleaseYear("1991");
        movieDto1.setDuration(120);
        movieDto1.setRating(8.05);
        movieDto1.setLikes(2);
        movieDto1.setDislikes(3);

        MovieDto movieDto2 = new MovieDto();
        movieDto2.setId(2L);
        movieDto2.setTitle("Movie Title 2");
        movieDto2.setDescription("Movie Description 2");
        movieDto2.setReleaseYear("1992");
        movieDto2.setDuration(120);
        movieDto2.setRating(8.05);
        movieDto2.setLikes(2);
        movieDto2.setDislikes(3);

        movieDtoList.add(movieDto1);
        movieDtoList.add(movieDto2);
    }

    @Test
    public void findAllMovies_whenQuery_returnList() {
        //given
        when(movieRepository.findAll()).thenReturn(movieList);
        when(movieMapper.toMovieDto(movieList.get(0))).thenReturn(movieDtoList.get(0));
        when(movieMapper.toMovieDto(movieList.get(1))).thenReturn(movieDtoList.get(1));

        //when
        movieService.findAllMovies();

        //then
        verify(movieRepository, times(1)).findAll();
        verify(movieMapper, times(2)).toMovieDto(any());

        assertEquals(ArrayList.class, movieService.findAllMovies().getClass());
        assertEquals(MovieDto.class, movieService.findAllMovies().get(0).getClass());
        assertEquals(2, movieService.findAllMovies().size());
        assertEquals(movieDtoList.get(0), movieService.findAllMovies().get(0));
        assertEquals(movieDtoList.get(1), movieService.findAllMovies().get(1));
    }

    @Test
    public void getMovieById_whenFound_returnMovie() {
        //given
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movieList.get(0)));
        when(movieMapper.toMovieDto(movieList.get(0))).thenReturn(movieDtoList.get(0));

        //when
        movieService.getMovieById(1L);

        //then
        verify(movieRepository, times(1)).findById(1L);
        verify(movieMapper, times(1)).toMovieDto(movieList.get(0));

        assertEquals(movieDtoList.get(0), movieService.getMovieById(1L));
        assertEquals(MovieDto.class, movieService.getMovieById(1L).getClass());
    }

    @Test
    public void getMovieById_whenNotFound_returnNull() {
        //given
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        //when
        movieService.getMovieById(1L);

        //then
        verify(movieRepository,times(1)).findById(1L);
        verify(movieMapper, times(0)).toMovieDto(any());

        assertNull(movieService.getMovieById(1L));
    }

    @Test
    public void getMoviesByTitle_whenFound_returnMovies() {
        //given
        when(movieRepository.findMoviesByTitle("Movie Title 1")).thenReturn(Arrays.asList(movieList.get(0)));
        when(movieMapper.toMovieDto(movieList.get(0))).thenReturn(movieDtoList.get(0));

        //when
        movieService.getMoviesByTitle("Movie Title 1");

        //then
        verify(movieRepository, times(1)).findMoviesByTitle("Movie Title 1");
        verify(movieMapper, times(1)).toMovieDto(movieList.get(0));

        assertEquals(ArrayList.class, movieService.getMoviesByTitle("Movie Title 1").getClass());
        assertEquals(MovieDto.class, movieService.getMoviesByTitle("Movie Title 1").get(0).getClass());
        assertEquals(1, movieService.getMoviesByTitle("Movie Title 1").size());
        assertEquals(movieDtoList.get(0), movieService.getMoviesByTitle("Movie Title 1").get(0));
    }

    @Test
    public void getMoviesByTitle_whenNotFound_returnEmptyList() {
        //given
        when(movieRepository.findMoviesByTitle("Movie2")).thenReturn(Collections.emptyList());

        //when
        movieService.getMoviesByTitle("Movie2");

        //then
        verify(movieRepository, times(1)).findMoviesByTitle("Movie2");
        verify(movieMapper, times(0)).toMovieDto(any());

        assertEquals(ArrayList.class, movieService.getMoviesByTitle("Movie2").getClass());
        assertEquals(0, movieService.getMoviesByTitle("Movie2").size());
    }

    @Test
    public void createMovie_whenSave_returnCreatedMovie() {
        //given
        when(movieMapper.toMovie(movieDtoList.get(0))).thenReturn(movieList.get(0));
        when(movieRepository.save(movieList.get(0))).thenReturn(movieList.get(0));
        when(movieMapper.toMovieDto(movieList.get(0))).thenReturn(movieDtoList.get(0));

        //when
        movieService.createMovie(movieDtoList.get(0));

        //then
        verify(movieRepository, times(1)).save(movieList.get(0));
        verify(movieMapper, times(1)).toMovieDto(movieList.get(0));
        verify(movieMapper, times(1)).toMovie(movieDtoList.get(0));

        assertEquals(MovieDto.class, movieService.createMovie(movieDtoList.get(0)).getClass());
        assertNotNull(movieService.createMovie(movieDtoList.get(0)).getId());
        assertEquals(movieDtoList.get(0), movieService.createMovie(movieDtoList.get(0)));
    }

    @Test
    public void updateMovie_whenMovieFound_returnUpdatedMovie() {
        //given
        MovieDto updatedMovie = new MovieDto();
        updatedMovie.setTitle(movieList.get(0).getTitle());
        updatedMovie.setDuration(180);
        updatedMovie.setDescription(movieList.get(0).getDescription());
        updatedMovie.setReleaseYear(movieList.get(0).getReleaseYear());
        updatedMovie.setRating(7.95);
        updatedMovie.setLikes(movieList.get(0).getLikes());
        updatedMovie.setDislikes(movieList.get(0).getDislikes());

        Movie alteredMovie = new Movie();
        alteredMovie.setId(updatedMovie.getId());
        alteredMovie.setTitle(updatedMovie.getTitle());
        alteredMovie.setDescription(updatedMovie.getDescription());
        alteredMovie.setDuration(updatedMovie.getDuration());
        alteredMovie.setRating(updatedMovie.getRating());
        alteredMovie.setLikes(updatedMovie.getLikes());
        alteredMovie.setDislikes(updatedMovie.getDislikes());

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movieList.get(0)));
        when(movieMapper.toMovieDto(alteredMovie)).thenReturn(updatedMovie);
        when(movieMapper.toMovie(updatedMovie)).thenReturn(alteredMovie);
        when(movieRepository.save(alteredMovie)).thenReturn(alteredMovie);

        //when
        movieService.updateMovie(updatedMovie, 1L);

        //then
        verify(movieRepository, times(1)).findById(1L);
        verify(movieRepository, times(1)).save(alteredMovie);
        verify(movieMapper, times(1)).toMovieDto(alteredMovie);
        verify(movieMapper, times(1)).toMovie(updatedMovie);

        assertEquals(MovieDto.class, movieService.updateMovie(updatedMovie, 1L).getClass());
        assertEquals(updatedMovie, movieService.updateMovie(updatedMovie, 1L));
        assertEquals(updatedMovie.getTitle(), movieService.updateMovie(updatedMovie, 1L).getTitle());
        assertEquals(updatedMovie.getDescription(), movieService.updateMovie(updatedMovie, 1L).getDescription());
        assertEquals(updatedMovie.getDuration(), movieService.updateMovie(updatedMovie, 1L).getDuration());
        assertEquals(updatedMovie.getRating(), movieService.updateMovie(updatedMovie, 1L).getRating());
        assertEquals(updatedMovie.getReleaseYear(), movieService.updateMovie(updatedMovie, 1L).getReleaseYear());
        assertEquals(updatedMovie.getLikes(), movieService.updateMovie(updatedMovie, 1L).getLikes());
        assertEquals(updatedMovie.getDislikes(), movieService.updateMovie(updatedMovie, 1L).getDislikes());
    }

    @Test
    public void updateMovie_whenMovieNotFound_returnNull() {
        //given
        when(movieRepository.findById(3L)).thenReturn(Optional.empty());

        //when
        movieService.updateMovie(movieDtoList.get(0), 3L);

        //then
        verify(movieRepository, times(1)).findById(3L);
        verify(movieRepository, times(0)).save(any());
        verify(movieMapper, times(0)).toMovieDto(any());
        verify(movieMapper, times(0)).toMovie(any());

        assertNull(movieService.updateMovie(movieDtoList.get(0), 3L));
    }

    @Test
    public void likeOrDislikeMovie_whenMovieNotFound_throwException() {
        //given
        when(movieRepository.findById(3L)).thenReturn(Optional.empty());

        //then
        assertThrows(NoSuchElementException.class, () -> movieService.likeOrDislikeMovie(3L, true));

        verify(movieRepository, times(1)).findById(3L);
        verify(movieRepository, times(0)).save(any());
    }

    @Test
    public void likeOrDislikeMovie_WhenLike_increaseLikes() {
        //given
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movieList.get(0)));
        movieList.get(0).setLikes(movieList.get(0).getLikes() + 1);
        when(movieRepository.save(movieList.get(0))).thenReturn(movieList.get(0));

        //when
        movieService.likeOrDislikeMovie(1L, true);

        //then
        verify(movieRepository, times(1)).findById(1L);
        verify(movieRepository, times(1)).save(movieList.get(0));
    }

    @Test
    public void likeOrDislikeMovie_WhenDisLike_increaseDislikes() {
        //given
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movieList.get(0)));
        movieList.get(0).setDislikes(movieList.get(0).getDislikes() + 1);
        when(movieRepository.save(movieList.get(0))).thenReturn(movieList.get(0));

        //when
        movieService.likeOrDislikeMovie(1L, false);

        //then
        verify(movieRepository, times(1)).findById(1L);
        verify(movieRepository, times(1)).save(movieList.get(0));
    }

    @Test
    public void deleteMovie_whenCalled_Success() {
        //when
        movieService.deleteMovie(2L);

        //then
        verify(movieRepository, times(1)).deleteById(2L);
    }
 }
