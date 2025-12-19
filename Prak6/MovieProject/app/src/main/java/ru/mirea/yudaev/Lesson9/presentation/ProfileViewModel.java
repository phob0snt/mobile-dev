package ru.mirea.yudaev.Lesson9.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.yudaev.domain.models.User;

public class ProfileViewModel extends ViewModel {
    
    private final MutableLiveData<User> user = new MutableLiveData<>();
    private final MutableLiveData<Boolean> logoutEvent = new MutableLiveData<>();
    
    public ProfileViewModel() {
        // Load current user (simulated)
        User currentUser = new User("user@example.com", "User Name");
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
