package ru.mirea.yudaev.data.network;

import java.util.List;

import ru.mirea.yudaev.domain.models.Movie;
import ru.mirea.yudaev.domain.repository.MovieRepository;

public class MovieRepositoryNetworkImpl implements MovieRepository {
    private final NetworkApi networkApi;
    private final FakeMovieApi fakeMovieApi;

    public MovieRepositoryNetworkImpl() {
        this.networkApi = new NetworkApi();
        this.fakeMovieApi = new FakeMovieApi();
    }

    @Override
    public boolean saveMovie(Movie movie) {
        // В реальном приложении здесь был бы сетевой запрос
        return true;
    }

    @Override
    public Movie getMovie() {
        return networkApi.getMovieById(2);
    }

    @Override
    public List<Movie> getAllMovies() {
        return fakeMovieApi.fetchMovies();
    }
}
