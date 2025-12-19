package ru.mirea.yudaev.domain.usecases;

import ru.mirea.yudaev.domain.repository.AuthRepository;

public class SignInUseCase {
    private AuthRepository authRepository;
    
    public SignInUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }
    
    public void execute(String email, String password, AuthRepository.AuthCallback callback) {
        authRepository.signIn(email, password, callback);
    }
}
