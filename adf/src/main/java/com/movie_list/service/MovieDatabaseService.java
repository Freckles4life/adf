package com.movie_list.service;

import com.movie_list.exception.BadRequestException;
import com.movie_list.exception.NotFoundException;
import com.movie_list.model.MovieSearch;
import com.movie_list.repository.MovieRepository;
import com.movie_list.model.Movie;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.matchingAll;

@Transactional
@Service
@AllArgsConstructor
public class MovieDatabaseService implements MovieRecordService {

    private final MovieRepository movieRepository;

    @Override
    public Movie register(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> search(MovieSearch movieSearch) {
        Movie movie = new Movie();
        movie.setTitle(movieSearch.getTitle());
        movie.setDirector(movieSearch.getDirector());
        movie.setYear(movieSearch.getYear());
        movie.setGenre(movieSearch.getGenre());
        Example<Movie> playerExample = Example.of(movie, matchingAll().withIgnoreNullValues());
        return movieRepository.findAll(playerExample);
    }

    @Override
    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new NotFoundException("Movie does not exist"));
    }

    @Override
    public Movie updateMovie(Long id, Movie updatedMovie) {
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Invalid movie id " + id));

        existingMovie.setTitle(updatedMovie.getTitle());
        existingMovie.setDirector(updatedMovie.getDirector());
        existingMovie.setYear(updatedMovie.getYear());
        existingMovie.setGenre(updatedMovie.getGenre());

        return movieRepository.save(existingMovie);

    }

    @Override
    public void delete(Long id) {

        movieRepository.deleteById(id);
    }

    @Override
    public List<Movie> findAllByTitle(String title) {

        return movieRepository.findAllByTitle(title);
    }


    @Override
    public List<Movie> findByTitleAndDirector(String title, String director) {
        List<Movie> result = movieRepository.findByTitleAndArtist(title, director;
        if (result.isEmpty()) {
            throw new BadRequestException("There is no such movie");
        }
        return result;
    }

}