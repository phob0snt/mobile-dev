package ru.mirea.yudaev.domain.models;

public class Movie {
    private int id;
    private String name;
    private String description;
    private String genre;
    private int year;
    private double rating;
    private String imageUrl;

    public Movie(int id, String name) {
        this.id = id;
        this.name = name;
        this.description = "";
        this.genre = "";
        this.year = 0;
        this.rating = 0.0;
        this.imageUrl = "";
    }

    public Movie(int id, String name, String description, String genre, int year, double rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
        this.imageUrl = "";
    }

    public Movie(int id, String name, String description, String genre, int year, double rating, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }
    
    public int getId() {
        return id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public int getYear() {
        return year;
    }
    
    public double getRating() {
        return rating;
    }
    
    public String getImageUrl() {
        return imageUrl != null ? imageUrl : "";
    }
}
