package com.x2ketarol.askon.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.x2ketarol.askon.R;
import com.x2ketarol.askon.data.local.ProfilePreferences;
import com.x2ketarol.askon.data.repository.BookingRepositoryImpl;
import com.x2ketarol.askon.domain.model.Booking;
import com.x2ketarol.askon.domain.repository.BookingRepository;

import java.util.List;

public class BookingsActivity extends AppCompatActivity {

    private ImageView emptyStateIcon;
    private TextView emptyStateText;
    private TextView findExpertsLink;
    private RecyclerView bookingsRecyclerView;
    private BottomNavigationView bottomNavigation;

    private BookingRepository bookingRepository;
    private ProfilePreferences profilePreferences;
    private List<Booking> bookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        // Initialize repository and preferences
        bookingRepository = new BookingRepositoryImpl(this);
        profilePreferences = new ProfilePreferences(this);

        initViews();
        setupBottomNavigation();
        loadBookings();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Перезагружаем бронирования при возврате на экран
        loadBookings();
    }

    private void initViews() {
        emptyStateIcon = findViewById(R.id.emptyStateIcon);
        emptyStateText = findViewById(R.id.emptyStateText);
        findExpertsLink = findViewById(R.id.findExpertsLink);
        bookingsRecyclerView = findViewById(R.id.bookingsRecyclerView);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        bookingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupBottomNavigation() {
        bottomNavigation.setSelectedItemId(R.id.nav_bookings);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_explore) {
                startActivity(new Intent(this, ExpertListActivity.class));
                finish();
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_bookings) {
                return true;
            } else if (itemId == R.id.nav_messages) {
                startActivity(new Intent(this, ChatActivity.class));
                finish();
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                finish();
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });

        findExpertsLink.setOnClickListener(v -> {
            startActivity(new Intent(this, ExpertListActivity.class));
        });
    }

    private void loadBookings() {
        // Получаем userId из SharedPreferences
        String userId = profilePreferences.getUserId();
        if (userId == null) {
            userId = "current_user_id"; // fallback
        }
        
        bookings = bookingRepository.getUserBookings(userId);

        if (bookings == null || bookings.isEmpty()) {
            showEmptyState();
        } else {
            showBookingsList();
        }
    }

    private void showEmptyState() {
        emptyStateIcon.setVisibility(View.VISIBLE);
        emptyStateText.setVisibility(View.VISIBLE);
        findExpertsLink.setVisibility(View.VISIBLE);
        bookingsRecyclerView.setVisibility(View.GONE);
    }

    private void showBookingsList() {
        emptyStateIcon.setVisibility(View.GONE);
        emptyStateText.setVisibility(View.GONE);
        findExpertsLink.setVisibility(View.GONE);
        bookingsRecyclerView.setVisibility(View.VISIBLE);
        
        // TODO: Set adapter with bookings
    }
}
