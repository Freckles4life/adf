package com.movie_list.repository;

        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.query.Param;
        import org.springframework.stereotype.Repository;

        import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository <Movie, Long> {

    List<Movie> findAllByTitle(String title);
    @Query(value = "select s from Movie  s where s.title=:title AND s.director=:director")
    List<Movie> findByTitleAndDirector(@Param("title") String title, @Param("director") String firstName);

}
