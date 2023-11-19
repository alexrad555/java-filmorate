package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(of = {"id"})
public class Film  {

    private Integer id;
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;

    @NotNull
    private LocalDate releaseDate;

    @Min(1)
    private int duration;

    private Mpa mpa;

    private Set<Integer> likes;

    private Set<Genre> genres;

    public Film(Integer id, String name, String description, LocalDate releaseDate, Integer duration,
                Set<Integer> likes, Set<Genre> genres, Mpa mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = likes == null ? new HashSet<>() : likes;
        this.genres = genres == null ? new HashSet<>() : genres;
        this.mpa = mpa;
    }

}
