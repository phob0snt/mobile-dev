package com.x2ketarol.askon.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "experts")
public class ExpertEntity {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String specialty;
    private String photoUrl;
    private double rating;
    private int reviewCount;
    private double hourlyRate;
    private String bio;
    private String skills;  // JSON string для списка навыков

    public ExpertEntity(String id, String name, String specialty, String photoUrl, 
                        double rating, int reviewCount, double hourlyRate, String bio, String skills) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.photoUrl = photoUrl;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.hourlyRate = hourlyRate;
        this.bio = bio;
        this.skills = skills;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    
    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    
    public int getReviewCount() { return reviewCount; }
    public void setReviewCount(int reviewCount) { this.reviewCount = reviewCount; }
    
    public double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }
    
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }
}
