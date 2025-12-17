package com.x2ketarol.askon.domain.model;

import java.util.ArrayList;
import java.util.List;

public class ExpertProfile {
    private String id;
    private String name;
    private String specialty;
    private String description;
    private String photoUrl;
    private double rating;
    private int reviewsCount;
    private int hourlyRate;
    private String bio;
    private List<String> skills;

    public ExpertProfile(String id, String name, String specialty, String description, 
                        String photoUrl, double rating, int reviewsCount) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.description = description;
        this.photoUrl = photoUrl;
        this.rating = rating;
        this.reviewsCount = reviewsCount;
        this.hourlyRate = 75;
        this.bio = description;
        this.skills = new ArrayList<>();
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

    public int getReviewCount() {
        return reviewsCount;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
