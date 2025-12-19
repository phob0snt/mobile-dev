package ru.mirea.yudaev.retrofitapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Обертка для ответа от TMDB API
 */
public class MoviesResponse {
    
    @SerializedName("results")
    private List<MovieDto> results;

    public List<MovieDto> getResults() {
        return results;
    }
}
