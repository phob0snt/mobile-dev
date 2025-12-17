package ru.mirea.yudaev.Lesson9.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.yudaev.Lesson9.R;
import ru.mirea.yudaev.Lesson9.data.repository.MovieRepositoryImpl;
import ru.mirea.yudaev.Lesson9.domain.GetFavoriteFilmUseCase;
import ru.mirea.yudaev.Lesson9.domain.SaveMovieToFavoriteUseCase;
import ru.mirea.yudaev.Lesson9.domain.models.Movie;
import ru.mirea.yudaev.Lesson9.domain.repository.MovieRepository;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        EditText text = findViewById(R.id.movieNameInputField);
        TextView textView = findViewById(R.id.textView);
        MovieRepository movieRepository = new MovieRepositoryImpl(this);

        findViewById(R.id.saveMovieButton).setOnClickListener(view -> {
            Boolean result = new
                    SaveMovieToFavoriteUseCase(movieRepository).execute(new Movie(2,
                    text.getText().toString()));
            textView.setText(String.format("Save result %s", result));
        });
        findViewById(R.id.showMovieButton).setOnClickListener(view -> {
            Movie movie = new GetFavoriteFilmUseCase(movieRepository).execute();
            textView.setText(String.format("Save result %s", movie.getName()));
        });
    }
}