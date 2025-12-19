package ru.mirea.yudaev.recyclerview;

/**
 * Модель фильма
 */
public class Movie {
    private String title;
    private int year;
    private String genre;
    private double rating;

    public Movie(String title, int year, String genre, double rating) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public double getRating() {
        return rating;
    }
}
