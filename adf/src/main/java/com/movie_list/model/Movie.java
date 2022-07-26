package com.movie_list.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Entity
@Table
@SequenceGenerator(name = "movie_id_seq", initialValue = 10, allocationSize = 100)
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="movie_id_seq")
    private Long id;
    @NotBlank(message = "You may not add a movie without a title")
    private String title;
    @NotBlank(message = "You may not add a movie without an director")
    private String director;
    @Positive(message = "First movie was recorded in 1888, therefore unfortunately we cannot play movies from BC :)")
    @Min(value = 1888, message = "Invalid year, cannot add movies before 1888")
    @Max(value = 2022, message = "Invalid year, cannot add movies from the future")
    private Integer year;
    @NotBlank(message = "You may not add a movie without a genre")
    private String genre;
}