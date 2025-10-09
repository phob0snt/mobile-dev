package ru.mirea.yudaev.Lesson9.domain.repository;

import ru.mirea.yudaev.Lesson9.domain.models.Movie;
public interface MovieRepository {
    public boolean saveMovie(Movie movie);
    public Movie getMovie();
}