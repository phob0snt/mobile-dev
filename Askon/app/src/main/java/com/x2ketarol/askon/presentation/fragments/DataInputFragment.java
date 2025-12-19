package com.x2ketarol.askon.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.x2ketarol.askon.R;

public class DataInputFragment extends Fragment {

    private EditText dataInput;
    private Button sendButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_input, container, false);
        
        dataInput = view.findViewById(R.id.dataInput);
        sendButton = view.findViewById(R.id.sendButton);
        
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sendButton.setOnClickListener(v -> {
            String inputText = dataInput.getText().toString();
            
            // Send data via Fragment Result API
            Bundle result = new Bundle();
            result.putString("text", inputText);
            getParentFragmentManager().setFragmentResult("dataRequestKey", result);
            
            // Show BottomSheet
            ExpertBottomSheetFragment bottomSheet = new ExpertBottomSheetFragment();
            bottomSheet.show(getParentFragmentManager(), "ExpertBottomSheet");
        });
    }
}
