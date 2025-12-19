package com.x2ketarol.askon.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.x2ketarol.askon.R;
import com.x2ketarol.askon.domain.model.Expert;
import com.x2ketarol.askon.presentation.viewmodels.SharedExpertViewModel;

public class ExpertDetailsFragment extends Fragment {

    private TextView nameTextView;
    private TextView specialtyTextView;
    private TextView ratingTextView;
    private TextView priceTextView;

    private SharedExpertViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expert_details, container, false);
        
        nameTextView = view.findViewById(R.id.expertNameTextView);
        specialtyTextView = view.findViewById(R.id.expertSpecialtyTextView);
        ratingTextView = view.findViewById(R.id.expertRatingTextView);
        priceTextView = view.findViewById(R.id.expertPriceTextView);
        
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedExpertViewModel.class);

        viewModel.getSelectedExpert().observe(getViewLifecycleOwner(), expert -> {
            if (expert != null) {
                displayExpertDetails(expert);
            }
        });
    }

    private void displayExpertDetails(Expert expert) {
        nameTextView.setText(expert.getName());
        specialtyTextView.setText(expert.getSpecialty());
        ratingTextView.setText("Рейтинг: " + expert.getRating());
        priceTextView.setText("Цена: " + expert.getPrice() + " ₽/час");
    }
}
