package ru.mirea.yudaev.Lesson9.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.yudaev.Lesson9.R;
import ru.mirea.yudaev.Lesson9.presentation.viewmodel.MainViewModel;
import ru.mirea.yudaev.Lesson9.presentation.viewmodel.ViewModelFactory;
import ru.mirea.yudaev.data.firebase.FirebaseAuthRepository;
import ru.mirea.yudaev.data.storage.sharedprefs.UserRepositoryImpl;
import ru.mirea.yudaev.domain.models.User;

/**
 * Главная Activity приложения, реализующая паттерн MVVM.
 * View слой - отвечает только за отображение UI и передачу событий в ViewModel.
 */
public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private EditText movieNameInputField;
    private TextView resultTextView;
    private ProgressBar progressBar;
    
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
        
        // Инициализация ViewModel через Factory для инъекции зависимостей
        mainViewModel = new ViewModelProvider(
                this,
                new ViewModelFactory(getApplicationContext())
        ).get(MainViewModel.class);
        
        // Инициализация UI элементов
        initViews();
        
        // Настройка наблюдателей за LiveData
        setupObservers();
        
        // Отображение информации о пользователе
        displayUserInfo();
        
        // Настройка обработчиков событий
        setupListeners();
    }
    
    /**
     * Инициализация UI элементов
     */
    private void initViews() {
        movieNameInputField = findViewById(R.id.movieNameInputField);
        resultTextView = findViewById(R.id.textView);
        progressBar = new ProgressBar(this);
    }
    
    /**
     * Настройка наблюдателей за LiveData из ViewModel
     */
    private void setupObservers() {
        // Наблюдение за результатом операций с фильмами
        mainViewModel.movieTextLiveData.observe(this, text -> {
            resultTextView.setText(text);
        });
        
        // Наблюдение за состоянием загрузки
        mainViewModel.isLoading.observe(this, isLoading -> {
            // Можно добавить ProgressBar в layout и управлять им
            // progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });
    }
    
    /**
     * Отображение информации о текущем пользователе
     */
    private void displayUserInfo() {
        UserRepositoryImpl userRepository = new UserRepositoryImpl(this);
        User user = userRepository.getUser();
        TextView userInfoTextView = findViewById(R.id.userInfoTextView);
        userInfoTextView.setText("Пользователь: " + user.getName() + " (" + user.getEmail() + ")");
    }
    
    /**
     * Настройка обработчиков событий UI
     */
    private void setupListeners() {
        // Сохранение фильма
        findViewById(R.id.saveMovieButton).setOnClickListener(view -> {
            String movieName = movieNameInputField.getText().toString();
            mainViewModel.saveMovie(movieName);
        });
        
        // Получение избранного фильма
        findViewById(R.id.showMovieButton).setOnClickListener(view -> {
            mainViewModel.loadFavoriteMovie();
        });
        
        // Выход из аккаунта
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            FirebaseAuthRepository authRepository = new FirebaseAuthRepository();
            authRepository.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}