package com.x2ketarol.askon.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.x2ketarol.askon.R;
import com.x2ketarol.askon.data.repository.UsersRepositoryImpl;
import com.x2ketarol.askon.domain.model.Expert;
import com.x2ketarol.askon.domain.repository.UsersRepository;
import com.x2ketarol.askon.domain.usecases.GetExpertsListUseCase;
import com.x2ketarol.askon.presentation.adapters.ExpertAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExpertListActivity extends AppCompatActivity {

    private EditText searchInput;
    private RecyclerView expertsRecyclerView;
    private BottomNavigationView bottomNavigation;

    private ExpertAdapter expertAdapter;
    private List<Expert> allExperts;
    private List<Expert> filteredExperts;

    private UsersRepository usersRepository;
    private GetExpertsListUseCase getExpertsListUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_list);

        // Initialize repository and use case
        usersRepository = new UsersRepositoryImpl(this);
        getExpertsListUseCase = new GetExpertsListUseCase(usersRepository);

        initViews();
        setupRecyclerView();
        setupSearch();
        setupBottomNavigation();
        loadExperts();
    }

    private void initViews() {
        searchInput = findViewById(R.id.searchInput);
        expertsRecyclerView = findViewById(R.id.expertsRecyclerView);
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }

    private void setupRecyclerView() {
        expertsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        filteredExperts = new ArrayList<>();
        expertAdapter = new ExpertAdapter(filteredExperts, this::onExpertClick);
        expertsRecyclerView.setAdapter(expertAdapter);
    }

    private void setupSearch() {
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterExperts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupBottomNavigation() {
        bottomNavigation.setSelectedItemId(R.id.nav_explore);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_explore) {
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
                startActivity(new Intent(this, ProfileActivity.class));
                finish();
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }

    private void loadExperts() {
        // Load experts using use case (empty string to get all experts)
        allExperts = getExpertsListUseCase.execute("");
        filteredExperts.clear();
        filteredExperts.addAll(allExperts);
        expertAdapter.notifyDataSetChanged();
    }

    private void filterExperts(String query) {
        if (query.isEmpty()) {
            filteredExperts.clear();
            filteredExperts.addAll(allExperts);
        } else {
            String lowerQuery = query.toLowerCase();
            filteredExperts.clear();
            filteredExperts.addAll(allExperts.stream()
                    .filter(expert -> expert.getName().toLowerCase().contains(lowerQuery) ||
                                     expert.getSpecialization().toLowerCase().contains(lowerQuery))
                    .collect(Collectors.toList()));
        }
        expertAdapter.notifyDataSetChanged();
    }

    private void onExpertClick(Expert expert) {
        Intent intent = new Intent(this, ExpertProfileActivity.class);
        intent.putExtra("expert_id", expert.getId());
        startActivity(intent);
    }
}
