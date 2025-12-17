package ru.mirea.yudaev.Lesson9.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.mirea.yudaev.Lesson9.R;
import ru.mirea.yudaev.data.firebase.FirebaseAuthRepository;
import ru.mirea.yudaev.data.storage.sharedprefs.UserRepositoryImpl;
import ru.mirea.yudaev.data.storage.room.MovieRepositoryRoomImpl;
import ru.mirea.yudaev.domain.models.User;
import ru.mirea.yudaev.domain.usecases.GetFavoriteFilmUseCase;
import ru.mirea.yudaev.domain.usecases.SaveMovieToFavoriteUseCase;
import ru.mirea.yudaev.domain.models.Movie;
import ru.mirea.yudaev.domain.repository.MovieRepository;

public class MainActivity extends AppCompatActivity {
    private ExecutorService executorService;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Проверка авторизации
        FirebaseAuthRepository authRepository = new FirebaseAuthRepository();
        if (!authRepository.isUserLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        
        setContentView(R.layout.main_activity);
        executorService = Executors.newSingleThreadExecutor();
        
        // Отображаем информацию о пользователе из SharedPreferences
        UserRepositoryImpl userRepository = new UserRepositoryImpl(this);
        User user = userRepository.getUser();
        TextView userInfoTextView = findViewById(R.id.userInfoTextView);
        userInfoTextView.setText("Пользователь: " + user.getName() + " (" + user.getEmail() + ")");
        
        EditText text = findViewById(R.id.movieNameInputField);
        TextView textView = findViewById(R.id.textView);
        MovieRepository movieRepository = new MovieRepositoryRoomImpl(this);

        findViewById(R.id.saveMovieButton).setOnClickListener(view -> {
            String movieName = text.getText().toString();
            executorService.execute(() -> {
                boolean result = new SaveMovieToFavoriteUseCase(movieRepository)
                        .execute(new Movie(2, movieName));
                runOnUiThread(() -> 
                    textView.setText(String.format("Save result: %s", result))
                );
            });
        });
        
        findViewById(R.id.showMovieButton).setOnClickListener(view -> {
            executorService.execute(() -> {
                Movie movie = new GetFavoriteFilmUseCase(movieRepository).execute();
                runOnUiThread(() -> 
                    textView.setText(String.format("Movie: %s", movie.getName()))
                );
            });
        });
        
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            authRepository.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}