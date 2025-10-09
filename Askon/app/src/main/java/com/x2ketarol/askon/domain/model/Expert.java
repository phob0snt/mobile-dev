package com.x2ketarol.askon.domain.model;

public class Expert {
    private String id;
    private String name;
    private String specialty;
    private String photoUrl;
    private double rating;

    public Expert(String id, String name, String specialty, String photoUrl, double rating) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.photoUrl = photoUrl;
        this.rating = rating;
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
}
