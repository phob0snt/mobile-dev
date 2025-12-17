package com.x2ketarol.askon.data.remote.dto;

/**
 * DTO (Data Transfer Object) для эксперта из сети
 * Используется для маппинга DTO → Entity → Domain
 */
public class ExpertDto {
    private String id;
    private String fullName;
    private String specialization;
    private String imageUrl;
    private double ratingValue;
    private int totalReviews;
    private double pricePerHour;
    private String description;
    private String[] skillsList;
    
    // Конструкторы
    public ExpertDto() {}
    
    public ExpertDto(String id, String fullName, String specialization, String imageUrl,
                    double ratingValue, int totalReviews, double pricePerHour, 
                    String description, String[] skillsList) {
        this.id = id;
        this.fullName = fullName;
        this.specialization = specialization;
        this.imageUrl = imageUrl;
        this.ratingValue = ratingValue;
        this.totalReviews = totalReviews;
        this.pricePerHour = pricePerHour;
        this.description = description;
        this.skillsList = skillsList;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public double getRatingValue() { return ratingValue; }
    public void setRatingValue(double ratingValue) { this.ratingValue = ratingValue; }
    
    public int getTotalReviews() { return totalReviews; }
    public void setTotalReviews(int totalReviews) { this.totalReviews = totalReviews; }
    
    public double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(double pricePerHour) { this.pricePerHour = pricePerHour; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String[] getSkillsList() { return skillsList; }
    public void setSkillsList(String[] skillsList) { this.skillsList = skillsList; }
}
