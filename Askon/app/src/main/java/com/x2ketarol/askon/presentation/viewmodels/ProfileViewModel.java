package com.x2ketarol.askon.presentation.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.x2ketarol.askon.domain.model.User;

public class ProfileViewModel extends ViewModel {
    
    private final MutableLiveData<User> user = new MutableLiveData<>();
    private final MutableLiveData<Boolean> logoutEvent = new MutableLiveData<>();
    
    public ProfileViewModel() {
        // Load current user (simulated)
        User currentUser = new User("user@example.com", "password123");
        user.setValue(currentUser);
    }
    
    public LiveData<User> getUser() {
        return user;
    }
    
    public LiveData<Boolean> getLogoutEvent() {
        return logoutEvent;
    }
    
    public void logout() {
        user.setValue(null);
        logoutEvent.setValue(true);
    }
}
