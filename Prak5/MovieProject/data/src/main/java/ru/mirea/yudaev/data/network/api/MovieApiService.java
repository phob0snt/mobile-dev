package ru.mirea.yudaev.data.network.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.mirea.yudaev.data.network.dto.MoviesResponse;

/**
 * Retrofit API интерфейс для работы с TMDB API
 * Документация: https://developer.themoviedb.org/docs/getting-started
 */
public interface MovieApiService {
    
    /**
     * Получить популярные фильмы
     * @param apiKey API ключ TMDB
     * @param language Язык ответа
     * @param page Номер страницы
     */
    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );
    
    /**
     * Получить топ рейтинговые фильмы
     * @param apiKey API ключ TMDB
     * @param language Язык ответа
     * @param page Номер страницы
     */
    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );
}
