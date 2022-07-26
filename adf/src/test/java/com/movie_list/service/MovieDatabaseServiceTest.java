package com.movie_list.service;

import com.movie_list.model.Movie;
import com.movie_list.model.MovieSearch;
import com.movie_list.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.ExampleMatcher.matchingAll;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class MovieDatabaseServiceTest {
    @InjectMocks
    private MovieDatabaseService movieDatabaseServiceTest;

    @Mock
    private MovieSearch movieSearch;

    @Mock
    private MovieRepository repository;


    @Test
    void register() {
        Movie sampleMovie = new Movie(2L, "Movie1", "Director1", 1999, "sci-fi");
        when(repository.save(sampleMovie)).thenReturn(sampleMovie);

        Movie movie = movieDatabaseServiceTest.register(sampleMovie);
        assertEquals(movie.getDirector(), sampleMovie.getDirector());
        assertEquals(movie.getId(), sampleMovie.getId());
        assertEquals(movie, sampleMovie);

        verify(repository).save(sampleMovie);

    }

    @Test
    void search() {

        when(movieSearch.getTitle()).thenReturn("Movie1");
        when(movieSearch.getDirector()).thenReturn("Director1");
        when(movieSearch.getYear()).thenReturn(null);
        when(movieSearch.getGenre()).thenReturn(null);

        Movie movie = new Movie();
        movie.setTitle(movieSearch.getTitle());
        movie.setDirector(movieSearch.getDirector());
        movie.setYear(movieSearch.getYear());
        movie.setGenre(movieSearch.getGenre());

        Example<Movie> movieExample = Example.of(movie, matchingAll().withIgnoreNullValues());

        when(repository.findAll(movieExample)).thenReturn(
                Arrays.asList(new Movie(2L, "Movie1", "Director1", 1999, "sci-fi"),
                        new Movie(3L, "Movie1", "Director1", 1998, "sci-fi")));

        List<Movie> movies = movieDatabaseServiceTest.search(movieSearch);

        assertThat(movies).hasSize(2)
                .contains(new Movie(2L, "Movie1", "Director1", 1999, "sci-fi"));
        assertEquals("Movie1", movies.get(0).getTitle());
        assertEquals("Movie1", movies.get(1).getTitle());
        assertNotEquals(movies.get(1).getYear(), movies.get(0).getYear());

        verify(repository).findAll(movieExample);
        verify(movieSearch, times(2)).getTitle();
        verify(movieSearch, times(2)).getDirector();
        verify(movieSearch, times(2)).getYear();
        verify(movieSearch, times(2)).getGenre();
    }

    @Test
    void getAll() {
        when(repository.findAll()).thenReturn(
                Arrays.asList(new Movie(2L, "Movie1", "Director1", 1999, "sci-fi"),
                        new Movie(3L, "Movie2", "Director2", 1998, "sci-fi")));


        List<Movie> movies = movieDatabaseServiceTest.getAll();
        assertEquals(2, movies.size());
        assertEquals("Movie1", movies.get(0).getTitle());
        assertTrue(movies.contains(new Movie(2L, "Movie1", "Director1", 1999, "sci-fi")));

        verify(repository).findAll();
    }

    @Test
    void getById() {
        Movie movie = new Movie(2L, "Movie1", "Director1", 1999, "sci-fi");
        when(repository.findById(2L))
                .thenReturn(Optional.of(movie));

        Movie findMovie = movieDatabaseServiceTest.getById(2L);

        assertEquals("Movie1", findMovie.getTitle());

        verify(repository).findById(2L);
    }

    @Test
    void getById_WhenMovieNotFound() {
        when(repository.findById(24L))
                .thenReturn(Optional.empty());

        try {
            movieDatabaseServiceTest.getById(24L);
            fail();
        } catch (Exception e) {
            assertEquals("Movie record does not exist", e.getMessage());
        }

        verify(repository).findById(24L);
    }

    @Test
    void updateMovie() {
        long id = 24L;
        Movie old = new Movie(id, "adasds", "asdasdsad", 1999, "sci-fi");
        Movie newUpdate = new Movie(id, "Movie1", "Director1", 1999, "sci-fi");

        when(repository.findById(id)).thenReturn(Optional.of(old));
        old.setTitle(newUpdate.getTitle());
        old.setDirector(newUpdate.getDirector());
        old.setYear(newUpdate.getYear());
        old.setGenre(newUpdate.getGenre());

        when(repository.save(old)).thenReturn(old);

        Movie updatedMovie = movieDatabaseServiceTest.updateMovie(id, newUpdate);

        assertEquals(updatedMovie, old);
        assertNotNull(updatedMovie);

        verify(repository).save(old);
        verify(repository).findById(id);


    }

    @Test
    void update_WhenMovieNotFound() {
        long id = 24L;
        Movie newUpdate = new Movie(id, "Movie1", "Director1", 1999, "sci-fi");

        when(repository.findById(24L))
                .thenReturn(Optional.empty());

        try {
            movieDatabaseServiceTest.updateMovie(24L, newUpdate);
            fail();
        } catch (Exception e) {
            assertEquals("Invalid movie id " + id, e.getMessage());
        }

        verify(repository).findById(24L);
    }


    @Test
    void delete() {
        Movie delete = new Movie(24L, "Movie1", "Director1", 1999, "sci-fi");
        doNothing().when(repository).deleteById(24L);
        movieDatabaseServiceTest.delete(24L);

        verify(repository).deleteById(24L);
    }

    @Test
    void findAllByTitle() {
        when(repository.findAllByTitle("Movie1")).thenReturn(
                Arrays.asList(new Movie(2L, "Movie1", "Director1", 1999, "sci-fi"),
                        new Movie(3L, "Movie1", "Director2", 1998, "horror")));

        List <Movie> movies = movieDatabaseServiceTest.findAllByTitle("Movie1");

        assertEquals(movies.get(0).getTitle(), "Movie1");
        assertThat(movies).hasSize(2)
                .contains(new Movie(2L, "Movie1", "Director1", 1999, "sci-fi"));

        verify(repository).findAllByTitle("Movie1");

    }

    @Test
    void findByTitleAndDirector() {

        when(repository.findByTitleAndDirector("Movie1","Director1")).thenReturn(
                Arrays.asList(new Movie(2L, "Movie1", "Director1", 1999, "sci-fi"),
                        new Movie(3L, "Movie1", "Director1", 1998, "horror")));

        List <Movie> movies = movieDatabaseServiceTest.findByTitleAndDirector("Movie1","Director1");
        assertEquals(movies.get(0).getTitle(), "Movie1");
        assertEquals(movies.get(1).getDirector(), "Director1");
        assertEquals(movies.get (1).getDirector(), movies.get(0).getDirector());
        assertNotEquals(movies.get(1), movies.get(0));
        assertThat(movies).hasSize(2)
                .contains(new Movie(2L, "Movie1", "Director1", 1999, "sci-fi"))
                .contains (new Movie(3L, "Movie1", "Director1", 1998, "horror"));

        verify(repository).findByTitleAndDirector("Movie1","Director1");
    }


    @Test
    void findByTitleAndDirector_WhenMovieNotFound() {

        when(repository.findByTitleAndDirector("Movie1","Director1"))
                .thenReturn(Collections.emptyList());

        try {
            movieDatabaseServiceTest.findByTitleAndDirector("Movie1","Director1");
            fail();
        } catch (Exception e) {
            assertEquals("There is no such movie", e.getMessage());
        }

        verify(repository).findByTitleAndDirector("Movie1","Director1");
    }
}
