package ru.mirea.yudaev.retrofitapp;

import com.google.gson.annotations.SerializedName;

/**
 * DTO для фильма из TMDB API
 */
public class MovieDto {
    
    @SerializedName("id")
    private int id;
    
    @SerializedName("title")
    private String title;
    
    @SerializedName("overview")
    private String overview;
    
    @SerializedName("poster_path")
    private String posterPath;
    
    @SerializedName("release_date")
    private String releaseDate;
    
    @SerializedName("vote_average")
    private double voteAverage;

    // Конструктор по умолчанию для Gson
    public MovieDto() {
    }

    // Конструктор для создания локальных данных
    public MovieDto(int id, String title, String overview, String posterPath, 
                    String releaseDate, double voteAverage) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getFullPosterUrl() {
        if (posterPath != null && !posterPath.isEmpty()) {
            return "https://image.tmdb.org/t/p/w500" + posterPath;
        }
        return "";
    }
}
