package com.x2ketarol.askon.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.x2ketarol.askon.R;

public class BlankFragment extends Fragment {

    private static final String ARG_STUDENT_NUMBER = "student_number";

    private TextView studentNumberTextView;

    public static BlankFragment newInstance(int studentNumber) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_STUDENT_NUMBER, studentNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        studentNumberTextView = view.findViewById(R.id.studentNumberTextView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            int studentNumber = getArguments().getInt(ARG_STUDENT_NUMBER);
            studentNumberTextView.setText("Номер студента: " + studentNumber);
        }
    }
}
