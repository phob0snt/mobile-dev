package ru.mirea.yudaev.data.network.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Response DTO для списка фильмов
 */
public class MoviesResponse {
    @SerializedName("results")
    private List<MovieDto> results;
    
    @SerializedName("page")
    private int page;
    
    @SerializedName("total_pages")
    private int totalPages;

    public List<MovieDto> getResults() {
        return results;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
