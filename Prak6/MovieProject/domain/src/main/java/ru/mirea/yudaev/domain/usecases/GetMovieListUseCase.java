package ru.mirea.yudaev.domain.usecases;

import java.util.List;

import ru.mirea.yudaev.domain.models.Movie;
import ru.mirea.yudaev.domain.repository.MovieRepository;

/**
 * UseCase для получения списка всех фильмов
 */
public class GetMovieListUseCase {
    private MovieRepository movieRepository;

    public GetMovieListUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> execute() {
        return movieRepository.getAllMovies();
    }
}
