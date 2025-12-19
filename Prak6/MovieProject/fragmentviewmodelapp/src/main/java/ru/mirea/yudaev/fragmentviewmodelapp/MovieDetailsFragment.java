package ru.mirea.yudaev.fragmentviewmodelapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

/**
 * Фрагмент деталей фильма с использованием SharedViewModel
 */
public class MovieDetailsFragment extends Fragment {

    private SharedMovieViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Получаем тот же экземпляр SharedViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(SharedMovieViewModel.class);

        TextView titleTextView = view.findViewById(R.id.titleTextView);
        TextView directorTextView = view.findViewById(R.id.directorTextView);
        TextView yearTextView = view.findViewById(R.id.yearTextView);
        TextView genreTextView = view.findViewById(R.id.genreTextView);
        TextView ratingTextView = view.findViewById(R.id.ratingTextView);

        // Наблюдаем за изменениями в LiveData
        viewModel.getSelectedMovie().observe(getViewLifecycleOwner(), movie -> {
            if (movie != null) {
                titleTextView.setText(movie.getTitle());
                directorTextView.setText("Режиссёр: " + movie.getDirector());
                yearTextView.setText("Год: " + movie.getYear());
                genreTextView.setText("Жанр: " + movie.getGenre());
                ratingTextView.setText(String.format("Рейтинг: %.1f/10", movie.getRating()));
            }
        });
    }
}
