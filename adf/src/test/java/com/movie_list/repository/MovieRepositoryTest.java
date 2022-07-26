package com.movie_list.repository;

import com.movie_list.model.Movie;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ConfigurationProperties (prefix = "test.properties")
@DataJpaTest
@ActiveProfiles ("test")

public class MovieRepositoryTest {
    @Autowired
    private MovieRepository repository;

    Movie movie1 = new Movie (11L,"movie1", "director1", 1999, "horror");
    Movie movie2 = new Movie (12L,"movie1", "director1", 1995, "romance");
    Movie movie3 = new Movie (13L,"movie1", "director2", 2000, "thriller");
    Movie movie4 = new Movie (17L,"movie2", "director17", 2011, "sci-fi");
    Movie movie5 = new Movie (21L,"movie18", "director18", 2020, "documentary");

    @BeforeEach
    void  setUp() {
        repository.save(movie1);
        repository.save(movie2);
        repository.save(movie3);
        repository.save(movie4);
        repository.save(movie5);
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
    }


    @Test
    void findAllByTitle() {
        List<Movie> movies = repository.findAllByTitle("movie1");
        Assertions.assertThat(movies).hasSize(3)
                .noneMatch(x-> x.getTitle() == "movie2");
        assertEquals("movie1", movies.get(0).getTitle());
        assertEquals("movie1", movies.get(1).getTitle());
    }

    @Test
    void findByTitleAndDirector() {

        List<Movie> findByTitleAndDirector = repository.findByTitleAndDirector("movie1", "director1");
        Assertions.assertThat(findByTitleAndDirector).hasSize(2)
                .noneMatch(x-> x.getTitle() == "movie2");
    }
}