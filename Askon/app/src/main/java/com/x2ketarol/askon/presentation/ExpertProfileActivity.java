package com.x2ketarol.askon.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import com.x2ketarol.askon.R;
import com.x2ketarol.askon.data.local.ProfilePreferences;
import com.x2ketarol.askon.data.repository.BookingRepositoryImpl;
import com.x2ketarol.askon.data.repository.UsersRepositoryImpl;
import com.x2ketarol.askon.domain.model.Booking;
import com.x2ketarol.askon.domain.model.ExpertProfile;
import com.x2ketarol.askon.domain.repository.BookingRepository;
import com.x2ketarol.askon.domain.repository.UsersRepository;
import com.x2ketarol.askon.domain.usecases.BookExpertTimeUseCase;
import com.x2ketarol.askon.domain.usecases.GetExpertProfileUseCase;

public class ExpertProfileActivity extends AppCompatActivity {

    private ImageView backButton;
    private ImageView expertAvatar;
    private TextView expertName;
    private TextView expertRating;
    private TextView expertPrice;
    private TextView expertDescription;
    private ChipGroup skillsChipGroup;
    private TextView reviewsTitle;
    private TextView noReviewsText;
    private Button bookNowButton;

    private UsersRepository usersRepository;
    private BookingRepository bookingRepository;
    private ProfilePreferences profilePreferences;
    private GetExpertProfileUseCase getExpertProfileUseCase;
    private BookExpertTimeUseCase bookExpertTimeUseCase;

    private String expertId;
    private ExpertProfile expertProfile;
    private boolean isBookingInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_profile);

        // Initialize repositories and use cases
        usersRepository = new UsersRepositoryImpl(this);
        bookingRepository = new BookingRepositoryImpl(this);
        profilePreferences = new ProfilePreferences(this);
        getExpertProfileUseCase = new GetExpertProfileUseCase(usersRepository);
        bookExpertTimeUseCase = new BookExpertTimeUseCase(bookingRepository);

        // Get expert ID from intent
        expertId = getIntent().getStringExtra("expert_id");

        initViews();
        setupListeners();
        loadExpertProfile();
    }

    private void initViews() {
        backButton = findViewById(R.id.backButton);
        expertAvatar = findViewById(R.id.expertAvatar);
        expertName = findViewById(R.id.expertName);
        expertRating = findViewById(R.id.expertRating);
        expertPrice = findViewById(R.id.expertPrice);
        expertDescription = findViewById(R.id.expertDescription);
        skillsChipGroup = findViewById(R.id.skillsChipGroup);
        reviewsTitle = findViewById(R.id.reviewsTitle);
        noReviewsText = findViewById(R.id.noReviewsText);
        bookNowButton = findViewById(R.id.bookNowButton);
    }

    private void setupListeners() {
        backButton.setOnClickListener(v -> finish());

        bookNowButton.setOnClickListener(v -> handleBooking());
    }

    private void loadExpertProfile() {
        // Load expert profile using use case
        expertProfile = getExpertProfileUseCase.execute(expertId);

        if (expertProfile != null) {
            displayProfile();
        } else {
            Toast.makeText(this, "Expert not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void displayProfile() {
        expertName.setText(expertProfile.getName());
        expertRating.setText(String.format("⭐ %.1f (%d)", 
            expertProfile.getRating(), expertProfile.getReviewCount()));
        expertPrice.setText(String.format("$%d/hr", expertProfile.getHourlyRate()));
        expertDescription.setText(expertProfile.getBio());

        // Add skills as chips
        skillsChipGroup.removeAllViews();
        for (String skill : expertProfile.getSkills()) {
            Chip chip = new Chip(this);
            chip.setText(skill);
            chip.setClickable(false);
            skillsChipGroup.addView(chip);
        }

        // Reviews
        reviewsTitle.setText(String.format("Reviews (%d)", expertProfile.getReviewCount()));
        if (expertProfile.getReviewCount() == 0) {
            noReviewsText.setVisibility(TextView.VISIBLE);
        } else {
            noReviewsText.setVisibility(TextView.GONE);
        }
    }

    private void handleBooking() {
        // Защита от множественных кликов
        if (isBookingInProgress) {
            return;
        }
        
        isBookingInProgress = true;
        bookNowButton.setEnabled(false);
        
        try {
            // Create booking
            Booking booking = bookExpertTimeUseCase.execute(
                expertId,
                "2025-12-20", // TODO: Add date picker
                "14:00" // TODO: Add time picker
            );

            if (booking != null) {
                Toast.makeText(this, "Booking successful!", Toast.LENGTH_SHORT).show();
                
                // Просто закрываем текущую Activity
                // BookingsActivity уже в стеке, при возврате onResume() обновит список
                finish();
            } else {
                Toast.makeText(this, "Booking failed", Toast.LENGTH_SHORT).show();
                isBookingInProgress = false;
                bookNowButton.setEnabled(true);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            isBookingInProgress = false;
            bookNowButton.setEnabled(true);
        }
    }
}
