package com.movie_list.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieSearch {
    private String title;
    private String director;
    private Integer year;
    private String genre;

    public String getArtist() {
        return Strings.isNotBlank(director) ? director : null;
    }

    public String getTitle() {
        return Strings.isNotBlank(title) ? title : null;
    }

    public String getGenre(){
        return Strings.isNotBlank(genre) ? genre : null;
    }
}