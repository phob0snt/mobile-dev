package com.x2ketarol.askon.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Expert {
    private String id;
    private String name;
    private String specialty;
    private String photoUrl;
    private double rating;
    private int reviewCount;
    private int hourlyRate;
    private String specialization;
    private List<String> skills;

    public Expert(String id, String name, String specialty, String photoUrl, double rating) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.photoUrl = photoUrl;
        this.rating = rating;
        this.reviewCount = 0;
        this.hourlyRate = 50;
        this.specialization = specialty;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public double getRating() {
        return rating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}

