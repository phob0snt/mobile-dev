package ru.mirea.yudaev.data.network;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.yudaev.domain.models.Movie;

public class NetworkApi {
    
    public List<Movie> getMoviesFromNetwork() {
        // Замоканные данные
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(1, "Интерстеллар"));
        movies.add(new Movie(2, "Аватар"));
        movies.add(new Movie(3, "Матрица"));
        movies.add(new Movie(4, "Начало"));
        movies.add(new Movie(5, "Темный рыцарь"));
        return movies;
    }

    public Movie getMovieById(int id) {
        List<Movie> movies = getMoviesFromNetwork();
        for (Movie movie : movies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return null;
    }
}
