package com.x2ketarol.askon.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.x2ketarol.askon.R;
import com.x2ketarol.askon.domain.model.Expert;
import com.x2ketarol.askon.presentation.fragments.adapters.ExpertFragmentAdapter;
import com.x2ketarol.askon.presentation.viewmodels.SharedExpertViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExpertListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExpertFragmentAdapter adapter;
    private SharedExpertViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expert_list, container, false);
        recyclerView = view.findViewById(R.id.expertsRecyclerView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedExpertViewModel.class);

        setupRecyclerView();
        loadExperts();
    }

    private void setupRecyclerView() {
        adapter = new ExpertFragmentAdapter(new ArrayList<>(), expert -> {
            // Set selected expert in ViewModel
            viewModel.selectExpert(expert);
            
            // Navigate to details fragment
            getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, new ExpertDetailsFragment())
                .addToBackStack(null)
                .commit();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void loadExperts() {
        // Simulated expert list
        List<Expert> experts = new ArrayList<>();
        experts.add(new Expert("1", "Анна Иванова", "Специалист по налогам", "https://randomuser.me/api/portraits/women/44.jpg", 4.8f, 3000));
        experts.add(new Expert("2", "Сергей Петров", "Юрист по недвижимости", "https://randomuser.me/api/portraits/men/32.jpg", 4.5f, 2500));
        experts.add(new Expert("3", "Мария Сидорова", "Бухгалтер", "https://randomuser.me/api/portraits/women/65.jpg", 4.9f, 2000));

        adapter.updateExperts(experts);
    }
}
