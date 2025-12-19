package com.x2ketarol.askon.presentation.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.x2ketarol.askon.R;
import com.x2ketarol.askon.data.local.ProfilePreferences;
import com.x2ketarol.askon.presentation.LoginActivity;
import com.x2ketarol.askon.presentation.viewmodels.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private ProfileViewModel viewModel;
    private ProfilePreferences profilePreferences;
    
    // Views для авторизованного пользователя
    private LinearLayout authorizedLayout;
    private TextView userNameTextView;
    private TextView emailTextView;
    private TextView roleTextView;
    private TextView userIdTextView;
    private Button logoutButton;
    
    // Views для гостя
    private LinearLayout guestLayout;
    private TextView guestMessageTextView;
    private Button loginButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        
        profilePreferences = new ProfilePreferences(requireContext());
        
        initViews(view);
        
        return view;
    }
    
    private void initViews(View view) {
        // Авторизованный пользователь
        authorizedLayout = view.findViewById(R.id.authorizedLayout);
        userNameTextView = view.findViewById(R.id.userNameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        roleTextView = view.findViewById(R.id.roleTextView);
        userIdTextView = view.findViewById(R.id.userIdTextView);
        logoutButton = view.findViewById(R.id.logoutButton);
        
        // Гость
        guestLayout = view.findViewById(R.id.guestLayout);
        guestMessageTextView = view.findViewById(R.id.guestMessageTextView);
        loginButton = view.findViewById(R.id.loginButton);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        
        // Проверяем статус авторизации
        checkAuthorizationStatus();
        
        // Observe user data
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                showAuthorizedUser(user.getName(), user.getEmail());
            } else {
                showGuestMode();
            }
        });
        
        // Observe logout event
        viewModel.getLogoutEvent().observe(getViewLifecycleOwner(), shouldNavigateToLogin -> {
            if (shouldNavigateToLogin != null && shouldNavigateToLogin) {
                navigateToLogin();
            }
        });
        
        logoutButton.setOnClickListener(v -> viewModel.logout());
        loginButton.setOnClickListener(v -> navigateToLogin());
    }
    
    /**
     * Проверка статуса авторизации из SharedPreferences
     */
    private void checkAuthorizationStatus() {
        String userId = profilePreferences.getUserId();
        
        if (userId != null && !userId.isEmpty()) {
            // Пользователь авторизован
            String name = profilePreferences.getName();
            String email = profilePreferences.getEmail();
            boolean isClient = profilePreferences.isClient();
            
            showAuthorizedUser(name, email);
            roleTextView.setText("Role: " + (isClient ? "Client" : "Expert"));
            userIdTextView.setText("ID: " + userId);
        } else {
            // Гостевой режим
            showGuestMode();
        }
    }
    
    /**
     * Показать интерфейс для авторизованного пользователя
     */
    private void showAuthorizedUser(String name, String email) {
        authorizedLayout.setVisibility(View.VISIBLE);
        guestLayout.setVisibility(View.GONE);
        
        userNameTextView.setText("Name: " + (name != null ? name : "Unknown"));
        emailTextView.setText("Email: " + (email != null ? email : "Not specified"));
    }
    
    /**
     * Показать гостевой режим
     */
    private void showGuestMode() {
        authorizedLayout.setVisibility(View.GONE);
        guestLayout.setVisibility(View.VISIBLE);
        
        guestMessageTextView.setText("Вы не авторизованы.\n\n" +
            "В гостевом режиме доступ ограничен:\n" +
            "• Можно просматривать экспертов\n" +
            "• Нельзя бронировать консультации\n" +
            "• Нельзя отправлять сообщения\n\n" +
            "Войдите, чтобы получить полный доступ.");
    }
    
    /**
     * Переход на экран логина
     */
    private void navigateToLogin() {
        Intent intent = new Intent(requireContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }
}
