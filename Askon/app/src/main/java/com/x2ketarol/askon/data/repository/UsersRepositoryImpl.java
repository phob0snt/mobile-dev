package com.x2ketarol.askon.data.repository;

import com.x2ketarol.askon.domain.model.Expert;
import com.x2ketarol.askon.domain.model.ExpertProfile;
import com.x2ketarol.askon.domain.model.Review;
import com.x2ketarol.askon.domain.model.User;
import com.x2ketarol.askon.domain.repository.UsersRepository;
import java.util.ArrayList;
import java.util.List;

public class UsersRepositoryImpl implements UsersRepository {
    private User currentUser;
    private List<Expert> experts = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();

    public UsersRepositoryImpl() {
        initMockData();
    }

    private void initMockData() {
        // Mock experts
        experts.add(new Expert("1", "Alex Smith", "Plumbing", "https://via.placeholder.com/150", 4.8));
        experts.add(new Expert("2", "Maria Johnson", "Electrical", "https://via.placeholder.com/150", 4.6));
        experts.add(new Expert("3", "David Brown", "Carpentry", "https://via.placeholder.com/150", 4.9));
        experts.add(new Expert("4", "Sarah Wilson", "Painting", "https://via.placeholder.com/150", 4.7));

        // Mock reviews
        reviews.add(new Review("1", "1", "101", "John Doe", 5.0, "Excellent service!"));
        reviews.add(new Review("2", "1", "102", "Jane Smith", 4.5, "Very professional"));
        reviews.add(new Review("3", "1", "103", "Bob Wilson", 4.8, "Great work, highly recommend"));
    }

    // User operations
    @Override
    public User loginUser(String email, String password) {
        currentUser = new User("1", "John Doe", email, "https://via.placeholder.com/150");
        return currentUser;
    }

    @Override
    public User registerUser(String name, String email, String password) {
        currentUser = new User("1", name, email, "https://via.placeholder.com/150");
        return currentUser;
    }

    @Override
    public User getCurrentUser() {
        if (currentUser == null) {
            currentUser = new User("1", "John Doe", "john@example.com", "https://via.placeholder.com/150");
        }
        return currentUser;
    }

    // Expert operations
    @Override
    public List<Expert> getExpertsList(String category) {
        List<Expert> filteredExperts = new ArrayList<>();
        for (Expert expert : experts) {
            if (category == null || category.isEmpty() || expert.getSpecialty().equalsIgnoreCase(category)) {
                filteredExperts.add(expert);
            }
        }
        return filteredExperts.isEmpty() ? new ArrayList<>(experts) : filteredExperts;
    }

    @Override
    public ExpertProfile getExpertProfile(String expertId) {
        for (Expert expert : experts) {
            if (expert.getId().equals(expertId)) {
                return new ExpertProfile(
                        expert.getId(),
                        expert.getName(),
                        expert.getSpecialty(),
                        "Experienced specialist with 10+ years in " + expert.getSpecialty().toLowerCase(),
                        expert.getPhotoUrl(),
                        expert.getRating(),
                        127
                );
            }
        }
        // Default profile if not found
        return new ExpertProfile(
                expertId,
                "Expert " + expertId,
                "General",
                "Professional expert",
                "https://via.placeholder.com/150",
                4.5,
                50
        );
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
