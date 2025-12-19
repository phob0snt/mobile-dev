package ru.mirea.yudaev.recyclerview;

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
 * Фрагмент с деталями фильма
 */
public class MovieDetailsFragment extends Fragment {

    private SharedMovieViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedMovieViewModel.class);

        TextView titleTextView = view.findViewById(R.id.movieTitleTextView);
        TextView yearTextView = view.findViewById(R.id.movieYearTextView);
        TextView genreTextView = view.findViewById(R.id.movieGenreTextView);
        TextView ratingTextView = view.findViewById(R.id.movieRatingTextView);

        // Подписываемся на изменения выбранного фильма
        viewModel.getSelectedMovie().observe(getViewLifecycleOwner(), movie -> {
            if (movie != null) {
                titleTextView.setText(movie.getTitle());
                yearTextView.setText("Год: " + movie.getYear());
                genreTextView.setText("Жанр: " + movie.getGenre());
                ratingTextView.setText(String.format("Рейтинг: %.1f/10", movie.getRating()));
            }
        });
    }
}
