package ru.mirea.yudaev.domain.repository;

public interface AuthRepository {
    void signIn(String email, String password, AuthCallback callback);
    void signUp(String email, String password, AuthCallback callback);
    void signOut();
    boolean isUserLoggedIn();
    
    interface AuthCallback {
        void onSuccess();
        void onError(String error);
    }
}
