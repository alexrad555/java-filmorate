package ru.yandex.practicum.filmorate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.memory.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.memory.InMemoryUserStorage;

@Configuration
public class ServiceConfig {

    @Bean
    public FilmStorage filmStorage() {
        return new InMemoryFilmStorage();
    }

    @Bean
    public UserStorage userStorage() {
        return new InMemoryUserStorage();
    }
}
