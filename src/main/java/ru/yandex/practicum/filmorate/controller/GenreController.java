package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreservice;

    @GetMapping
    public List<Genre> getAll() {
        final List<Genre> genres = genreservice.getAll();
        log.info("Get all genres {}", genres);
        return genres;
    }

    @GetMapping("/{id}")
    public Genre getById(@PathVariable Integer id) {
        log.info("Get genre  by id {}", id);
        return genreservice.getById(id);
    }
}
