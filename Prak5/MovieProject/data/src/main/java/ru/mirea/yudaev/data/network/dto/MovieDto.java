package ru.mirea.yudaev.data.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * DTO для получения данных о фильме из API
 */
public class MovieDto {
    @SerializedName("id")
    private int id;
    
    @SerializedName("title")
    private String title;
    
    @SerializedName("overview")
    private String overview;
    
    @SerializedName("genre_ids")
    private List<Integer> genreIds;
    
    @SerializedName("release_date")
    private String releaseDate;
    
    @SerializedName("vote_average")
    private double voteAverage;
    
    @SerializedName("poster_path")
    private String posterPath;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title != null ? title : "";
    }

    public String getOverview() {
        return overview != null ? overview : "";
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public String getReleaseDate() {
        return releaseDate != null ? releaseDate : "";
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getPosterPath() {
        return posterPath != null ? posterPath : "";
    }
    
    public String getFullPosterUrl() {
        if (posterPath == null || posterPath.isEmpty()) {
            return "https://via.placeholder.com/500x750?text=No+Image";
        }
        return "https://image.tmdb.org/t/p/w500" + posterPath;
    }
}
