package com.x2ketarol.askon.domain.usecases;

import com.x2ketarol.askon.domain.model.User;
import com.x2ketarol.askon.domain.repository.UsersRepository;

public class RegisterUserUseCase {
    private final UsersRepository repository;

    public RegisterUserUseCase(UsersRepository repository) {
        this.repository = repository;
    }

    public User execute(String name, String email, String password) {
        return repository.registerUser(name, email, password);
    }
}
