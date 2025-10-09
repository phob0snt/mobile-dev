package com.x2ketarol.askon.domain.model;

public class ExpertProfile {
    private String id;
    private String name;
    private String specialty;
    private String description;
    private String photoUrl;
    private double rating;
    private int reviewsCount;

    public ExpertProfile(String id, String name, String specialty, String description, 
                        String photoUrl, double rating, int reviewsCount) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.description = description;
        this.photoUrl = photoUrl;
        this.rating = rating;
        this.reviewsCount = reviewsCount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getDescription() {
        return description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public double getRating() {
        return rating;
    }

    public int getReviewsCount() {
        return reviewsCount;
    }
}
