package com.x2ketarol.askon.data.repository;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.x2ketarol.askon.data.local.ProfilePreferences;
import com.x2ketarol.askon.domain.model.User;
import com.x2ketarol.askon.domain.repository.AuthRepository;

/**
 * Реализация AuthRepository с Firebase Authentication
 * Использует SharedPreferences для хранения профиля клиента
 * Соответствует Clean Architecture - domain изолирован от Firebase
 */
public class AuthRepositoryImpl implements AuthRepository {
    
    private final FirebaseAuth firebaseAuth;
    private final ProfilePreferences profilePreferences;
    
    public AuthRepositoryImpl(Context context) {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.profilePreferences = new ProfilePreferences(context);
    }
    
    @Override
    public User login(String email, String password) {
        try {
            // Firebase Authentication - синхронный вызов для упрощения
            // В production использовать Task<AuthResult> и callback
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser firebaseUser = authResult.getUser();
                    if (firebaseUser != null) {
                        // Сохраняем профиль в SharedPreferences
                        profilePreferences.setUserId(firebaseUser.getUid());
                        profilePreferences.setEmail(firebaseUser.getEmail());
                        profilePreferences.setName(firebaseUser.getDisplayName() != null ? 
                            firebaseUser.getDisplayName() : "User");
                        profilePreferences.setLoggedIn(true);
                    }
                });
            
            // Для синхронности возвращаем текущего пользователя
            return getCurrentUser();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public User register(String name, String email, String password) {
        try {
            // Firebase Authentication - регистрация
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser firebaseUser = authResult.getUser();
                    if (firebaseUser != null) {
                        // Обновляем профиль с именем
                        // userProfileChangeRequest можно использовать для установки имени
                        
                        // Сохраняем в SharedPreferences
                        profilePreferences.setUserId(firebaseUser.getUid());
                        profilePreferences.setEmail(email);
                        profilePreferences.setName(name);
                        profilePreferences.setLoggedIn(true);
                    }
                });
            
            return getCurrentUser();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public void logout() {
        firebaseAuth.signOut();
        profilePreferences.clearProfile();
    }
    
    @Override
    public User getCurrentUser() {
        // Проверяем Firebase Auth
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        
        // Если пользователь авторизован, возвращаем из SharedPreferences
        if (firebaseUser != null && profilePreferences.isLoggedIn()) {
            return new User(
                profilePreferences.getUserId(),
                profilePreferences.getName(),
                profilePreferences.getEmail(),
                "https://via.placeholder.com/150"
            );
        }
        
        return null;
    }
}
