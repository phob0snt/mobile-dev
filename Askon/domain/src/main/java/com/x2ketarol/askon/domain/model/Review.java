package com.x2ketarol.askon.domain.model;

public class Review {
    private String id;
    private String expertId;
    private String userId;
    private String userName;
    private double rating;
    private String comment;

    public Review(String id, String expertId, String userId, String userName, double rating, String comment) {
        this.id = id;
        this.expertId = expertId;
        this.userId = userId;
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public String getExpertId() {
        return expertId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public double getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
