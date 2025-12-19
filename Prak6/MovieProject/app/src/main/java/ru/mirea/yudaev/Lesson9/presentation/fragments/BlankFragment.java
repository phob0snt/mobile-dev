package ru.mirea.yudaev.Lesson9.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.mirea.yudaev.Lesson9.R;

/**
 * Базовый фрагмент для демонстрации передачи данных через Bundle
 */
public class BlankFragment extends Fragment {
    
    private static final String ARG_STUDENT_NUMBER = "student_number";
    
    public static BlankFragment newInstance(int studentNumber) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_STUDENT_NUMBER, studentNumber);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        TextView textView = view.findViewById(R.id.studentNumberTextView);
        
        Bundle args = getArguments();
        if (args != null) {
            int studentNumber = args.getInt(ARG_STUDENT_NUMBER, 0);
            textView.setText("Номер студента: " + studentNumber);
        } else {
            textView.setText("Данные не переданы");
        }
    }
}
