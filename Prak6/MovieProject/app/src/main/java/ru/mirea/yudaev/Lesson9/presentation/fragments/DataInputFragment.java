package ru.mirea.yudaev.Lesson9.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.mirea.yudaev.Lesson9.R;

/**
 * Фрагмент для демонстрации Fragment Result API
 * Отправляет данные в BottomSheet
 */
public class DataInputFragment extends Fragment {
    
    private EditText inputEditText;
    private Button sendButton;
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data_input, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        inputEditText = view.findViewById(R.id.inputEditText);
        sendButton = view.findViewById(R.id.sendButton);
        
        sendButton.setOnClickListener(v -> {
            String text = inputEditText.getText().toString();
            
            if (!text.isEmpty()) {
                // Отправляем данные через Fragment Result API
                Bundle result = new Bundle();
                result.putString("input_text", text);
                getParentFragmentManager().setFragmentResult("dataRequestKey", result);
                
                // Показываем BottomSheet
                MovieBottomSheetFragment bottomSheet = new MovieBottomSheetFragment();
                bottomSheet.show(getParentFragmentManager(), "MovieBottomSheet");
                
                // Очищаем поле ввода
                inputEditText.setText("");
            }
        });
    }
}
