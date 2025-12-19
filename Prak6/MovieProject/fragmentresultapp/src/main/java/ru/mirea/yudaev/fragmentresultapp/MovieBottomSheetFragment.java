package ru.mirea.yudaev.fragmentresultapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * BottomSheet фрагмент для отображения данных из Fragment Result API
 */
public class MovieBottomSheetFragment extends BottomSheetDialogFragment {

    private TextView receivedDataTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Регистрируем слушатель для получения результата
        getParentFragmentManager().setFragmentResultListener("data_request", this, 
            (requestKey, bundle) -> {
                String text = bundle.getString("input_text", "");
                if (receivedDataTextView != null) {
                    receivedDataTextView.setText(text.isEmpty() ? "Данные не получены" : text);
                }
            }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        receivedDataTextView = view.findViewById(R.id.receivedDataTextView);
    }
}
