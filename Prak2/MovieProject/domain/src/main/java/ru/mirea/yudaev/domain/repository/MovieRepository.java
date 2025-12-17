package ru.mirea.yudaev.domain.repository;

import ru.mirea.yudaev.domain.models.Movie;

public interface MovieRepository {
    boolean saveMovie(Movie movie);
    Movie getMovie();
}
