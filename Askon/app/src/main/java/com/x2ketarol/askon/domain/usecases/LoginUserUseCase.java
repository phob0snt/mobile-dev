package com.x2ketarol.askon.domain.usecases;

import com.x2ketarol.askon.domain.model.User;
import com.x2ketarol.askon.domain.repository.UsersRepository;

public class LoginUserUseCase {
    private final UsersRepository repository;

    public LoginUserUseCase(UsersRepository repository) {
        this.repository = repository;
    }

    public User execute(String email, String password) {
        return repository.loginUser(email, password);
    }
}
