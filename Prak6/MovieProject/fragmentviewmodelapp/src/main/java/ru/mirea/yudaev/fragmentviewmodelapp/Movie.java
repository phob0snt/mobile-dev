package ru.mirea.yudaev.fragmentviewmodelapp;

/**
 * Модель фильма
 */
public class Movie {
    private final String title;
    private final String director;
    private final int year;
    private final String genre;
    private final double rating;

    public Movie(String title, String director, int year, String genre, double rating) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
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
