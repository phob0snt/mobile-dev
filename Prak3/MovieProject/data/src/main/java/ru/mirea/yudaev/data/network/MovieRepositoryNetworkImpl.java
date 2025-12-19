package ru.mirea.yudaev.data.network;

import ru.mirea.yudaev.domain.models.Movie;
import ru.mirea.yudaev.domain.repository.MovieRepository;

public class MovieRepositoryNetworkImpl implements MovieRepository {
    private final NetworkApi networkApi;

    public MovieRepositoryNetworkImpl() {
        this.networkApi = new NetworkApi();
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
}
