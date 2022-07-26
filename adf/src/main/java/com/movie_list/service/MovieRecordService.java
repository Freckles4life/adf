package com.movie_list.service;


    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public interface MovieRecordService {

        Movie register(Movie movie);

        List<Movie> getAll();

        Movie getById(Long id);

        Movie updateMovie(Long id, Movie updatedMovie);

        void delete(Long id);

        List<Movie> search(MovieSearch movieSearch);

        List<Movie> findAllByTitle(String title);

        List<Movie> findByTitleAndDirector(String title, String director);

    }

