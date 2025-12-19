package ru.mirea.yudaev.Lesson9.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.yudaev.Lesson9.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel viewModel;
    private TextView emailTextView;
    private Button logoutButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        
        emailTextView = view.findViewById(R.id.emailTextView);
        logoutButton = view.findViewById(R.id.logoutButton);
        
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        
        // Observe user data
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                emailTextView.setText("Email: " + user.getEmail());
            } else {
                emailTextView.setText("Пользователь не авторизован");
            }
        });
        
        // Observe logout event
        viewModel.getLogoutEvent().observe(getViewLifecycleOwner(), shouldNavigateToLogin -> {
            if (shouldNavigateToLogin != null && shouldNavigateToLogin) {
                // Navigate to LoginActivity
                // For demo purposes, just show a message
                emailTextView.setText("Вы вышли из системы");
            }
        });
        
        logoutButton.setOnClickListener(v -> viewModel.logout());
    }
}
