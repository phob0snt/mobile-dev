package ru.mirea.yudaev.domain.usecases;

import ru.mirea.yudaev.domain.repository.AuthRepository;

public class SignUpUseCase {
    private AuthRepository authRepository;
    
    public SignUpUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }
    
    public void execute(String email, String password, AuthRepository.AuthCallback callback) {
        authRepository.signUp(email, password, callback);
    }
}
