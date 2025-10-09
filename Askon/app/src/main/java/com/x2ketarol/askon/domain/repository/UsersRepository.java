package com.x2ketarol.askon.domain.repository;

import com.x2ketarol.askon.domain.model.Expert;
import com.x2ketarol.askon.domain.model.ExpertProfile;
import com.x2ketarol.askon.domain.model.Review;
import com.x2ketarol.askon.domain.model.User;
import java.util.List;

public interface UsersRepository {
    // User operations
    User loginUser(String email, String password);
    User registerUser(String name, String email, String password);
    User getCurrentUser();
    
    // Expert operations
    List<Expert> getExpertsList(String category);
    ExpertProfile getExpertProfile(String expertId);
    
    // Review operations
    List<Review> getExpertReviews(String expertId);
    Review submitReview(String expertId, String userId, double rating, String comment);
}
