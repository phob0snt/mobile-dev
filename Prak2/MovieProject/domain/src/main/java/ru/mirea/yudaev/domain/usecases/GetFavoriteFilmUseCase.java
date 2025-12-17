package ru.mirea.yudaev.domain.usecases;

import ru.mirea.yudaev.domain.models.Movie;
import ru.mirea.yudaev.domain.repository.MovieRepository;

public class GetFavoriteFilmUseCase {
    private MovieRepository movieRepository;
    
    public GetFavoriteFilmUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    
    public Movie execute() {
        return movieRepository.getMovie();
    }
}
