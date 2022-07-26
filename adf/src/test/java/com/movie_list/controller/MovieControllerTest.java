package com.movie_list.controller;

        import com.movie_list.model.MovieSearch;
        import com.movie_list.service.MovieRecordService;
        import com.movie_list.model.Movie;
        import org.junit.jupiter.api.Test;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
        import org.springframework.boot.test.mock.mockito.MockBean;
        import org.springframework.http.MediaType;
        import org.springframework.test.context.ActiveProfiles;
        import org.springframework.test.web.servlet.MockMvc;
        import org.springframework.test.web.servlet.MvcResult;
        import org.springframework.test.web.servlet.RequestBuilder;
        import org.springframework.test.web.servlet.ResultActions;
        import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

        import java.util.Arrays;

        import static org.mockito.Mockito.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
@ActiveProfiles("test")
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieRecordService movieRecordService;

    @Test
    void index() throws Exception {

        when(movieRecordService.getAll()).thenReturn(
                Arrays.asList(new Movie(1L, "Movie1", "Director1", 1995, "genre1"),
                        new Movie(2L, "Movie2", "Director2", 1999, "genre2"),
                        new Movie(3L, "Movie3", "Director3", 2000, "genre3")));

        //call "/movies"
        RequestBuilder request = MockMvcRequestBuilders
                .get("/movies")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andReturn();

        verify(movieRecordService, times(1)).getAll();

    }


    @Test
    void searchTest() throws Exception {
        //call "/search"
        RequestBuilder request = MockMvcRequestBuilders
                .get("/movies/search")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("search1"))
                .andReturn();

    }


    @Test
    void getSearchedMovieTest() throws Exception {

        when(movieRecordService.search(new MovieSearch("Movie1", "Director1", null, null)))
                .thenReturn(
                        Arrays.asList(new Movie(1L, "Movie1", "Director1", 1995, "genre1"),
                                new Movie(2L, "Movie1", "Director1", 1995, "genre2")));


        //call "/search"
        RequestBuilder request = MockMvcRequestBuilders
                .post("/movies/search")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andReturn();

        verify(movieRecordService, times(1)).search(new MovieSearch(null, null, null, null));
    }

    @Test
    void addMovieTest() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .get("/movies/movie-add")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("movie-add"))
                .andReturn();
    }

    @Test
    void deleteByIdTest() throws Exception {
        doNothing().when(movieRecordService).delete(11L);
        RequestBuilder request = MockMvcRequestBuilders
                .get("/movies/delete/{id}", 11)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(request)
                .andExpect(redirectedUrl("/movies"));
    }

    @Test
    void editById() throws Exception {
        when(movieRecordService.getById(11L)).thenReturn(new Movie(11L, "Movie1", "Director1", 1991, "genre1"));
        RequestBuilder request = MockMvcRequestBuilders
                .get("/movies/edit/{id}", 11)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("movie-edit"))
                .andReturn();

        verify(movieRecordService).getById(11L);
    }

    @Test
    void register() throws Exception {

        when(movieRecordService.register(new Movie(1L, "Movie1", "Director1", 1998, "horror")))
                .thenReturn(new Movie(1L, "Movie1", "Director1", 1998, "horror"));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/movies")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("movie-add"))
                .andReturn();
    }

    @Test
    void updateMovie() throws Exception {
        Movie updated = new Movie(1L, "Movie1", "Director1", 1998, "romance");
        when(movieRecordService.updateMovie(1L, updated))
                .thenReturn(new Movie(1L, "Movie1", "Director1", 1998, "romance"));


        RequestBuilder request = MockMvcRequestBuilders
                .post("/movies/update/{id}", 1)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("movie-edit"))
                .andReturn();

    }
}{
}
