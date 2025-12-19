package ru.mirea.yudaev.data.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.yudaev.domain.repository.AuthRepository;

public class FirebaseAuthRepository implements AuthRepository {
    private final FirebaseAuth firebaseAuth;

    public FirebaseAuthRepository() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signIn(String email, String password, AuthCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        String error = task.getException() != null ? 
                                task.getException().getMessage() : "Authentication failed";
                        callback.onError(error);
                    }
                });
    }

    @Override
    public void signUp(String email, String password, AuthCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        String error = task.getException() != null ? 
                                task.getException().getMessage() : "Registration failed";
                        callback.onError(error);
                    }
                });
    }

    @Override
    public void signOut() {
        firebaseAuth.signOut();
    }

    @Override
    public boolean isUserLoggedIn() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        return currentUser != null;
    }
}
