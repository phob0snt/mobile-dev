package com.x2ketarol.askon.domain.usecases;

import com.x2ketarol.askon.domain.model.Expert;
import com.x2ketarol.askon.domain.repository.UsersRepository;
import java.util.List;

public class GetExpertsListUseCase {
    private final UsersRepository repository;

    public GetExpertsListUseCase(UsersRepository repository) {
        this.repository = repository;
    }

    public List<Expert> execute(String category) {
        return repository.getExpertsList(category);
    }
}
