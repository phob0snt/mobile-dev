package com.x2ketarol.askon.domain.usecases;

import com.x2ketarol.askon.domain.model.Review;
import com.x2ketarol.askon.domain.repository.UsersRepository;

public class SubmitReviewUseCase {
    private final UsersRepository repository;

    public SubmitReviewUseCase(UsersRepository repository) {
        this.repository = repository;
    }

    public Review execute(String expertId, String userId, double rating, String comment) {
        return repository.submitReview(expertId, userId, rating, comment);
    }
}
