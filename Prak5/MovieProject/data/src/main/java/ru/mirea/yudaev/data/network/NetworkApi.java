package ru.mirea.yudaev.data.network;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import ru.mirea.yudaev.data.mapper.MovieMapper;
import ru.mirea.yudaev.data.network.api.MovieApiService;
import ru.mirea.yudaev.data.network.dto.MoviesResponse;
import ru.mirea.yudaev.domain.models.Movie;

/**
 * Класс для работы с сетевым API фильмов
 */
public class NetworkApi {
    
    private static final String TAG = "NetworkApi";
    private final MovieApiService apiService;
    private final FakeMovieApi fakeMovieApi;
    
    public NetworkApi() {
        this.apiService = RetrofitClient.getMovieApiService();
        this.fakeMovieApi = new FakeMovieApi();
    }
    
    /**
     * Получить популярные фильмы с TMDB API
     * В случае ошибки возвращает данные из FakeMovieApi
     */
    public List<Movie> getMoviesFromNetwork() {
        try {
            Call<MoviesResponse> call = apiService.getPopularMovies(
                    RetrofitClient.API_KEY,
                    "ru-RU",
                    1
            );
            Response<MoviesResponse> response = call.execute();
            
            if (response.isSuccessful() && response.body() != null) {
                List<Movie> movies = MovieMapper.mapToDomainList(response.body().getResults());
                Log.d(TAG, "Загружено фильмов из API: " + movies.size());
                return movies;
            } else {
                Log.w(TAG, "API вернул ошибку: " + response.code() + ", используем FakeMovieApi");
                return fakeMovieApi.fetchMovies();
            }
        } catch (IOException e) {
            Log.e(TAG, "Ошибка сети при загрузке фильмов, используем FakeMovieApi", e);
            return fakeMovieApi.fetchMovies();
        } catch (Exception e) {
            Log.e(TAG, "Неожиданная ошибка при загрузке фильмов, используем FakeMovieApi", e);
            return fakeMovieApi.fetchMovies();
        }
    }
    
    /**
     * Получить топ-рейтинговые фильмы с TMDB API
     */
    public List<Movie> getTopRatedMoviesFromNetwork() {
        try {
            Call<MoviesResponse> call = apiService.getTopRatedMovies(
                    RetrofitClient.API_KEY,
                    "ru-RU",
                    1
            );
            Response<MoviesResponse> response = call.execute();
            
            if (response.isSuccessful() && response.body() != null) {
                List<Movie> movies = MovieMapper.mapToDomainList(response.body().getResults());
                Log.d(TAG, "Загружено топ фильмов из API: " + movies.size());
                return movies;
            } else {
                Log.w(TAG, "API вернул ошибку: " + response.code());
                return new ArrayList<>();
            }
        } catch (IOException e) {
            Log.e(TAG, "Ошибка сети при загрузке топ фильмов", e);
            return new ArrayList<>();
        } catch (Exception e) {
            Log.e(TAG, "Неожиданная ошибка при загрузке топ фильмов", e);
            return new ArrayList<>();
        }
    }

    /**
     * Поиск фильма по ID
     * Сначала пытается найти в локальных данных FakeMovieApi
     */
    public Movie getMovieById(int id) {
        List<Movie> movies = fakeMovieApi.fetchMovies();
        for (Movie movie : movies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return null;
    }
}
