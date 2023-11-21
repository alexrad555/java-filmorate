package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public List<Film> getAll() {
        log.info("Getting all films");
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable Integer id) {
        log.info("Get film  by id {}", id);
        return filmService.getById(id);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Creating film {}", film);
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Update film {}", film);
        return filmService.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(
            @PathVariable Integer id,
            @PathVariable Integer userId) {
        log.info("Add like film id {}", id);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(
            @PathVariable Integer id,
            @PathVariable Integer userId) {
        log.info("Delete like film id {}", id);
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getAllPopular(@RequestParam(defaultValue = "10") Integer count) {
        log.info("Getting all popular films");
        return filmService.getAllPopular(count);
    }

}
