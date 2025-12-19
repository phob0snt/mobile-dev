package ru.mirea.yudaev.languagelearningapp.ui.lessons;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.mirea.yudaev.languagelearningapp.databinding.FragmentLessonsBinding;

public class LessonsFragment extends Fragment {

    private FragmentLessonsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLessonsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // RecyclerView setup would go here in a real app
        // For demo purposes, we'll just show the layout
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
