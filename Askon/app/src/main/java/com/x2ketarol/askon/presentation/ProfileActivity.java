package com.x2ketarol.askon.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.x2ketarol.askon.R;
import com.x2ketarol.askon.data.local.ProfilePreferences;

public class ProfileActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private TextView userName;
    private TextView userEmail;
    private TextView userPhone;
    private TextView userRole;
    private Button logoutButton;
    
    private ProfilePreferences profilePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        
        profilePreferences = new ProfilePreferences(this);

        initViews();
        loadProfileData();
        setupBottomNavigation();
    }

    private void initViews() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userPhone = findViewById(R.id.userPhone);
        userRole = findViewById(R.id.userRole);
        logoutButton = findViewById(R.id.logoutButton);
        
        logoutButton.setOnClickListener(v -> handleLogout());
    }
    
    private void loadProfileData() {
        // Загружаем данные из SharedPreferences
        userName.setText(profilePreferences.getName());
        userEmail.setText(profilePreferences.getEmail());
        
        String phone = profilePreferences.getPhone();
        if (phone != null && !phone.isEmpty()) {
            userPhone.setText(phone);
        } else {
            userPhone.setText("Not specified");
        }
        
        String role = profilePreferences.isClient() ? "Client" : "Expert";
        userRole.setText(role);
    }
    
    private void handleLogout() {
        // Очищаем профиль
        profilePreferences.clearProfile();
        
        // Переходим на экран логина
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void setupBottomNavigation() {
        bottomNavigation.setSelectedItemId(R.id.nav_profile);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_explore) {
                startActivity(new Intent(this, ExpertListActivity.class));
                finish();
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_bookings) {
                startActivity(new Intent(this, BookingsActivity.class));
                finish();
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_messages) {
                startActivity(new Intent(this, ChatActivity.class));
                finish();
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_profile) {
                return true;
            }
            return false;
        });
    }
}
