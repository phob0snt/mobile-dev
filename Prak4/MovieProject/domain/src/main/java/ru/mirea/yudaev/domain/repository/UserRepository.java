package ru.mirea.yudaev.domain.repository;

import ru.mirea.yudaev.domain.models.User;

public interface UserRepository {
    boolean saveUser(User user);
    User getUser();
}
