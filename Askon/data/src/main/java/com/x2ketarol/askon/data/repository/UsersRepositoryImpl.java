package com.x2ketarol.askon.data.repository;

import android.content.Context;

import com.x2ketarol.askon.data.local.ProfilePreferences;
import com.x2ketarol.askon.domain.model.Expert;
import com.x2ketarol.askon.domain.model.ExpertProfile;
import com.x2ketarol.askon.domain.model.Review;
import com.x2ketarol.askon.domain.model.User;
import com.x2ketarol.askon.domain.repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация UsersRepository
 * Использует SharedPreferences для хранения профиля клиента
 * Делегирует работу с экспертами в MLRepository
 */
public class UsersRepositoryImpl implements UsersRepository {
    
    private final ProfilePreferences profilePreferences;
    private final MLRepositoryImpl mlRepository;
    private List<Review> reviews = new ArrayList<>();

    public UsersRepositoryImpl(Context context) {
        this.profilePreferences = new ProfilePreferences(context);
        this.mlRepository = new MLRepositoryImpl(context);
        initMockReviews();
    }
    
    private void initMockReviews() {
        reviews.add(new Review("1", "1", "101", "John Doe", 5.0, "Excellent service!"));
        reviews.add(new Review("2", "1", "102", "Jane Smith", 4.5, "Very professional"));
        reviews.add(new Review("3", "1", "103", "Bob Wilson", 4.8, "Great work, highly recommend"));
    }

    // User operations - используем SharedPreferences
    @Override
    public User loginUser(String email, String password) {
        // Сохраняем в SharedPreferences
        profilePreferences.setEmail(email);
        profilePreferences.setName("John Doe");
        profilePreferences.setUserId("1");
        profilePreferences.setLoggedIn(true);
        
        return getCurrentUser();
    }

    @Override
    public User registerUser(String name, String email, String password) {
        // Сохраняем в SharedPreferences
        profilePreferences.setName(name);
        profilePreferences.setEmail(email);
        profilePreferences.setUserId(String.valueOf(System.currentTimeMillis()));
        profilePreferences.setLoggedIn(true);
        
        return getCurrentUser();
    }

    @Override
    public User getCurrentUser() {
        // Получаем из SharedPreferences
        if (profilePreferences.isLoggedIn()) {
            return new User(
                profilePreferences.getUserId(),
                profilePreferences.getName(),
                profilePreferences.getEmail(),
                "https://via.placeholder.com/150"
            );
        }
        return null;
    }

    // Expert operations - делегируем в MLRepository
    @Override
    public List<Expert> getExpertsList(String category) {
        return mlRepository.getExperts(category);
    }

    @Override
    public ExpertProfile getExpertProfile(String expertId) {
        return mlRepository.getExpertProfile(expertId);
    }

    // Review operations
    @Override
    public List<Review> getExpertReviews(String expertId) {
        List<Review> expertReviews = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getExpertId().equals(expertId)) {
                expertReviews.add(review);
            }
        }
        return expertReviews;
    }

    @Override
    public Review submitReview(String expertId, String userId, double rating, String comment) {
        String id = String.valueOf(reviews.size() + 1);
        Review newReview = new Review(id, expertId, userId, "User " + userId, rating, comment);
        reviews.add(newReview);
        return newReview;
    }
}
