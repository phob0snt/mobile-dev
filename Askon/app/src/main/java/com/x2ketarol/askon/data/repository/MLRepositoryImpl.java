package com.x2ketarol.askon.data.repository;

import com.x2ketarol.askon.domain.repository.MLRepository;

public class MLRepositoryImpl implements MLRepository {

    @Override
    public boolean recognizePhoto(String photoPath) {
        // Mock ML recognition - returns random result
        return Math.random() < 0.5;
    }

    @Override
    public String getCategoryFromPhoto(String photoPath) {
        // Mock ML category detection
        String[] categories = {"Plumbing", "Electrical", "Carpentry", "Painting", "HVAC"};
        int randomIndex = (int) (Math.random() * categories.length);
        return categories[randomIndex];
    }
}
