package com.x2ketarol.askon.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.x2ketarol.askon.R;

public class ExpertBottomSheetFragment extends BottomSheetDialogFragment {

    private TextView receivedDataTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
        receivedDataTextView = view.findViewById(R.id.receivedDataTextView);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Listen for Fragment Result
        getParentFragmentManager().setFragmentResultListener("dataRequestKey", this, (requestKey, result) -> {
            String text = result.getString("text");
            if (receivedDataTextView != null) {
                receivedDataTextView.setText("Получено: " + text);
            }
        });
    }
}
