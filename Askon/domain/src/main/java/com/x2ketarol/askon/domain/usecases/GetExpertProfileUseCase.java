package com.x2ketarol.askon.domain.usecases;

import com.x2ketarol.askon.domain.model.ExpertProfile;
import com.x2ketarol.askon.domain.repository.UsersRepository;

public class GetExpertProfileUseCase {
    private final UsersRepository repository;

    public GetExpertProfileUseCase(UsersRepository repository) {
        this.repository = repository;
    }

    public ExpertProfile execute(String expertId) {
        return repository.getExpertProfile(expertId);
    }
}
