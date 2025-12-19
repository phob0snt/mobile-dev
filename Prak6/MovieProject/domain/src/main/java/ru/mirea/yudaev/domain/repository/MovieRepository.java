package ru.mirea.yudaev.domain.repository;

import java.util.List;

import ru.mirea.yudaev.domain.models.Movie;

public interface MovieRepository {
    boolean saveMovie(Movie movie);
    Movie getMovie();
    List<Movie> getAllMovies();
}
