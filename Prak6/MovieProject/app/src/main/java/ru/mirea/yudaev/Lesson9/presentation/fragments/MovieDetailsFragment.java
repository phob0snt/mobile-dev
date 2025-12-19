package ru.mirea.yudaev.Lesson9.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.yudaev.Lesson9.R;
import ru.mirea.yudaev.Lesson9.presentation.viewmodel.SharedMovieViewModel;
import ru.mirea.yudaev.domain.models.Movie;

/**
 * Фрагмент с детальной информацией о фильме
 */
public class MovieDetailsFragment extends Fragment {
    
    private SharedMovieViewModel viewModel;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView genreTextView;
    private TextView yearTextView;
    private TextView ratingTextView;
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        titleTextView = view.findViewById(R.id.movieTitleTextView);
        descriptionTextView = view.findViewById(R.id.movieDescriptionTextView);
        genreTextView = view.findViewById(R.id.movieGenreTextView);
        yearTextView = view.findViewById(R.id.movieYearTextView);
        ratingTextView = view.findViewById(R.id.movieRatingTextView);
        
        // Получаем shared ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(SharedMovieViewModel.class);
        
        // Подписываемся на изменения выбранного фильма
        viewModel.getSelectedMovie().observe(getViewLifecycleOwner(), movie -> {
            if (movie != null) {
                displayMovieDetails(movie);
            }
        });
    }
    
    private void displayMovieDetails(Movie movie) {
        titleTextView.setText(movie.getName());
        descriptionTextView.setText(movie.getDescription());
        genreTextView.setText("Жанр: " + movie.getGenre());
        yearTextView.setText("Год: " + movie.getYear());
        ratingTextView.setText(String.format("Рейтинг: %.1f/10", movie.getRating()));
    }
}
