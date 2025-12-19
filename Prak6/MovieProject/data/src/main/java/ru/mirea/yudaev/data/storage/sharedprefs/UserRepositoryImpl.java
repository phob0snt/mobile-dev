package ru.mirea.yudaev.data.storage.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

import ru.mirea.yudaev.domain.models.User;
import ru.mirea.yudaev.domain.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {
    private final SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_NAME = "user_name";

    public UserRepositoryImpl(Context context) {
        this.sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean saveUser(User user) {
        return sharedPreferences.edit()
                .putString(KEY_USER_EMAIL, user.getEmail())
                .putString(KEY_USER_NAME, user.getName())
                .commit();
    }

    @Override
    public User getUser() {
        String email = sharedPreferences.getString(KEY_USER_EMAIL, "");
        String name = sharedPreferences.getString(KEY_USER_NAME, "Guest");
        return new User(email, name);
    }
}
