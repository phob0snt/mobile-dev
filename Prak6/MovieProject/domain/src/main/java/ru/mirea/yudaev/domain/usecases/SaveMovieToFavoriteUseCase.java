package ru.mirea.yudaev.domain.usecases;

import ru.mirea.yudaev.domain.models.Movie;
import ru.mirea.yudaev.domain.repository.MovieRepository;

public class SaveMovieToFavoriteUseCase {
    private MovieRepository movieRepository;
    
    public SaveMovieToFavoriteUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    
    public boolean execute(Movie movie) {
        return movieRepository.saveMovie(movie);
    }
}
