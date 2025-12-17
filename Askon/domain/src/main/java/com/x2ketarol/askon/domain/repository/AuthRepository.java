package com.x2ketarol.askon.domain.repository;

import com.x2ketarol.askon.domain.model.User;

/**
 * Репозиторий для аутентификации
 * Domain layer - изолирован от Firebase и Android
 */
public interface AuthRepository {
    /**
     * Вход пользователя
     * @param email Email
     * @param password Пароль
     * @return User или null
     */
    User login(String email, String password);
    
    /**
     * Регистрация пользователя
     * @param name Имя
     * @param email Email
     * @param password Пароль
     * @return User или null
     */
    User register(String name, String email, String password);
    
    /**
     * Выход пользователя
     */
    void logout();
    
    /**
     * Получить текущего пользователя
     * @return User или null
     */
    User getCurrentUser();
}
