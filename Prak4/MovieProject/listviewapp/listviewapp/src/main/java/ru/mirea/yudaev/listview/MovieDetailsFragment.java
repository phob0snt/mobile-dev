package ru.mirea.yudaev.listview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Фрагмент с деталями фильма
 */
public class MovieDetailsFragment extends Fragment {

    private static final String ARG_MOVIE_NAME = "movie_name";

    public static MovieDetailsFragment newInstance(String movieName) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MOVIE_NAME, movieName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView movieNameTextView = view.findViewById(R.id.movieNameTextView);

        if (getArguments() != null) {
            String movieName = getArguments().getString(ARG_MOVIE_NAME);
            movieNameTextView.setText(movieName);
        }
    }
}
