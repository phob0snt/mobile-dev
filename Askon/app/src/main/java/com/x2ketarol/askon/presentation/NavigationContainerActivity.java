package com.x2ketarol.askon.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.x2ketarol.askon.R;
import com.x2ketarol.askon.data.local.ProfilePreferences;

/**
 * Главная Activity с навигацией через фрагменты
 * Использует Navigation Component для управления бэк-стеком
 */
public class NavigationContainerActivity extends AppCompatActivity {
    
    private NavController navController;
    private BottomNavigationView bottomNavigation;
    private ProfilePreferences profilePreferences;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_container);
        
        profilePreferences = new ProfilePreferences(this);
        
        // Проверка авторизации
        if (profilePreferences.getUserId() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        
        setupNavigation();
        setupBottomNavigation();
        setupToolbar();
    }
    
    private void setupNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
    }
    
    private void setupBottomNavigation() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            
            if (itemId == R.id.nav_explore) {
                navController.navigate(R.id.expertListFragment);
                return true;
            } else if (itemId == R.id.nav_bookings) {
                // Переход на старую Activity (пока не переписана на фрагмент)
                startActivity(new Intent(this, BookingsActivity.class));
                return true;
            } else if (itemId == R.id.nav_messages) {
                // Переход на старую Activity
                startActivity(new Intent(this, ChatActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                navController.navigate(R.id.profileFragment);
                return true;
            }
            
            return false;
        });
    }
    
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        // AppBarConfiguration для top-level destinations
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.expertListFragment, R.id.profileFragment)
                .build();
        
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
    
    @Override
    public void onBackPressed() {
        if (!navController.popBackStack()) {
            super.onBackPressed();
        }
    }
}
