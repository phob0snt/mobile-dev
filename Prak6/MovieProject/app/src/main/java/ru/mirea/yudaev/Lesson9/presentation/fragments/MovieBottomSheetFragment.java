package ru.mirea.yudaev.Lesson9.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ru.mirea.yudaev.Lesson9.R;

/**
 * Bottom Sheet для демонстрации Fragment Result API
 * Получает данные от родительского фрагмента
 */
public class MovieBottomSheetFragment extends BottomSheetDialogFragment {
    
    private TextView receivedDataTextView;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Подписываемся на получение данных через Fragment Result API
        getParentFragmentManager().setFragmentResultListener("dataRequestKey", this,
                (requestKey, bundle) -> {
                    String text = bundle.getString("input_text");
                    if (receivedDataTextView != null && text != null) {
                        receivedDataTextView.setText("Получено: " + text);
                    }
                });
    }
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        receivedDataTextView = view.findViewById(R.id.receivedDataTextView);
    }
}
