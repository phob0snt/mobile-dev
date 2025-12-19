package ru.mirea.yudaev.fragmentresultapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Фрагмент для ввода данных и отправки через Fragment Result API
 */
public class DataInputFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data_input, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText editText = view.findViewById(R.id.dataEditText);
        Button showButton = view.findViewById(R.id.showBottomSheetButton);

        showButton.setOnClickListener(v -> {
            String text = editText.getText().toString();

            // Создаём Bundle с данными
            Bundle result = new Bundle();
            result.putString("input_text", text);

            // Отправляем результат через Fragment Result API
            getParentFragmentManager().setFragmentResult("data_request", result);

            // Открываем BottomSheet
            MovieBottomSheetFragment bottomSheet = new MovieBottomSheetFragment();
            bottomSheet.show(getParentFragmentManager(), "MovieBottomSheet");
        });
    }
}
