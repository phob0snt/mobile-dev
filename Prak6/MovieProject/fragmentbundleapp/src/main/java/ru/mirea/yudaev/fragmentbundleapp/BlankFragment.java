package ru.mirea.yudaev.fragmentbundleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Фрагмент для демонстрации получения данных через Bundle
 */
public class BlankFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView studentNumberTextView = view.findViewById(R.id.studentNumberTextView);
        TextView studentNameTextView = view.findViewById(R.id.studentNameTextView);
        TextView groupTextView = view.findViewById(R.id.groupTextView);
        Button nextButton = view.findViewById(R.id.nextButton);

        // Получаем данные из Bundle
        Bundle args = getArguments();
        if (args != null) {
            int studentNumber = args.getInt("student_number", 0);
            String studentName = args.getString("student_name", "");
            String group = args.getString("group", "");

            studentNumberTextView.setText("Номер студента: " + studentNumber);
            studentNameTextView.setText("ФИО: " + studentName);
            groupTextView.setText("Группа: " + group);
        }

        // Переход ко второму фрагменту (навигация)
        nextButton.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ListFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });
    }
}
