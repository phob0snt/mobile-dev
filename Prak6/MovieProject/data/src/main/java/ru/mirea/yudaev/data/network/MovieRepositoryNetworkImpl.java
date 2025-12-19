package ru.mirea.yudaev.data.network;

import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.mirea.yudaev.domain.models.Movie;
import ru.mirea.yudaev.domain.repository.MovieRepository;

/**
 * Реализация репозитория фильмов с использованием сетевого API
 * Использует Retrofit для загрузки данных из TMDB API
 */
public class MovieRepositoryNetworkImpl implements MovieRepository {
    private static final String TAG = "MovieRepositoryNetwork";
    
    private final NetworkApi networkApi;
    private final FakeMovieApi fakeMovieApi;
    private final ExecutorService executorService;

    public MovieRepositoryNetworkImpl() {
        this.networkApi = new NetworkApi();
        this.fakeMovieApi = new FakeMovieApi();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public boolean saveMovie(Movie movie) {
        // В реальном приложении здесь был бы сетевой запрос для сохранения
        Log.d(TAG, "Сохранение фильма: " + movie.getName());
        return true;
    }

    @Override
    public Movie getMovie() {
        // Возвращает фильм по умолчанию из локальных данных
        return fakeMovieApi.fetchMovies().get(0);
    }

    @Override
    public List<Movie> getAllMovies() {
        // Загружает фильмы из сетевого API
        // В случае ошибки NetworkApi автоматически вернет данные из FakeMovieApi
        Log.d(TAG, "Загрузка всех фильмов из API");
        return networkApi.getMoviesFromNetwork();
    }
}
