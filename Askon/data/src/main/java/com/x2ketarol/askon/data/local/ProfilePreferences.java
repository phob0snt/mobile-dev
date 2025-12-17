package com.x2ketarol.askon.data.local;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Класс для хранения профиля клиента в SharedPreferences
 * Соответствует требованиям Clean Architecture - SharedPreferences для профиля
 */
public class ProfilePreferences {
    
    private static final String PREF_NAME = "AskonProfile";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_IS_CLIENT = "isClient";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    
    private final SharedPreferences prefs;
    
    public ProfilePreferences(Context context) {
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    
    // User ID
    public void setUserId(String userId) {
        prefs.edit().putString(KEY_USER_ID, userId).apply();
    }
    
    public String getUserId() {
        return prefs.getString(KEY_USER_ID, null);
    }
    
    // Name
    public void setName(String name) {
        prefs.edit().putString(KEY_NAME, name).apply();
    }
    
    public String getName() {
        return prefs.getString(KEY_NAME, "");
    }
    
    // Email
    public void setEmail(String email) {
        prefs.edit().putString(KEY_EMAIL, email).apply();
    }
    
    public String getEmail() {
        return prefs.getString(KEY_EMAIL, "");
    }
    
    // Phone
    public void setPhone(String phone) {
        prefs.edit().putString(KEY_PHONE, phone).apply();
    }
    
    public String getPhone() {
        return prefs.getString(KEY_PHONE, "");
    }
    
    // Is Client
    public void setClient(boolean isClient) {
        prefs.edit().putBoolean(KEY_IS_CLIENT, isClient).apply();
    }
    
    public boolean isClient() {
        return prefs.getBoolean(KEY_IS_CLIENT, true);
    }
    
    // Is Logged In
    public void setLoggedIn(boolean isLoggedIn) {
        prefs.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply();
    }
    
    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    
    // Clear all
    public void clearProfile() {
        prefs.edit().clear().apply();
    }
}
