package ru.mirea.yudaev.fragmentbundleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Фрагмент деталей для отображения переданных данных
 */
public class DetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView titleTextView = view.findViewById(R.id.movieTitleTextView);
        TextView positionTextView = view.findViewById(R.id.moviePositionTextView);

        // Получаем данные из Bundle
        Bundle args = getArguments();
        if (args != null) {
            String title = args.getString("movie_title", "");
            int position = args.getInt("movie_position", 0);

            titleTextView.setText(title);
            positionTextView.setText("Позиция в списке: " + position);
        }
    }
}
