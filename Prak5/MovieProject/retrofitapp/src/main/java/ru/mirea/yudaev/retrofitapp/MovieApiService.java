package ru.mirea.yudaev.retrofitapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * API интерфейс для TMDB
 */
public interface MovieApiService {
    
    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("page") int page
    );
}
