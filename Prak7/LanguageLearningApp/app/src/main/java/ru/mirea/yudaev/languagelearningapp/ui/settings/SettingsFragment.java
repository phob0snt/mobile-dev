package ru.mirea.yudaev.languagelearningapp.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.mirea.yudaev.languagelearningapp.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Setup listeners for switches
        binding.switchPush.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(getContext(), "Push notifications: " + (isChecked ? "ON" : "OFF"), 
                    Toast.LENGTH_SHORT).show();
        });

        binding.switchReminders.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(getContext(), "Daily reminders: " + (isChecked ? "ON" : "OFF"), 
                    Toast.LENGTH_SHORT).show();
        });

        binding.switchSound.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(getContext(), "Sound effects: " + (isChecked ? "ON" : "OFF"), 
                    Toast.LENGTH_SHORT).show();
        });

        binding.switchOffline.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(getContext(), "Offline mode: " + (isChecked ? "ON" : "OFF"), 
                    Toast.LENGTH_SHORT).show();
        });

        binding.switchAutoPlay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(getContext(), "Auto-play audio: " + (isChecked ? "ON" : "OFF"), 
                    Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
