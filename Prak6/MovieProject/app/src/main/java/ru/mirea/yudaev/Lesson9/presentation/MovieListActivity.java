package ru.mirea.yudaev.Lesson9.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.mirea.yudaev.Lesson9.R;
import ru.mirea.yudaev.Lesson9.presentation.adapter.MovieAdapter;
import ru.mirea.yudaev.Lesson9.presentation.viewmodel.MovieListViewModel;
import ru.mirea.yudaev.Lesson9.presentation.viewmodel.ViewModelFactory;
import ru.mirea.yudaev.data.firebase.FirebaseAuthRepository;

/**
 * Activity для отображения списка фильмов с использованием RecyclerView и MVVM
 */
public class MovieListActivity extends AppCompatActivity {
    private MovieListViewModel viewModel;
    private MovieAdapter adapter;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

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
        
        setContentView(R.layout.activity_movie_list);
        
        // Инициализация ViewModel
        viewModel = new ViewModelProvider(
                this,
                new ViewModelFactory(getApplicationContext())
        ).get(MovieListViewModel.class);
        
        // Инициализация UI
        initViews();
        
        // Настройка RecyclerView
        setupRecyclerView();
        
        // Настройка наблюдателей
        setupObservers();
        
        // Загрузка данных
        viewModel.loadMovies();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.moviesRecyclerView);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupRecyclerView() {
        // Создание адаптера с обработчиком кликов
        adapter = new MovieAdapter(movie -> {
            Toast.makeText(this, 
                    "Выбран: " + movie.getName() + " (" + movie.getYear() + ")", 
                    Toast.LENGTH_SHORT).show();
        });
        
        // Настройка RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setupObservers() {
        // Наблюдение за списком фильмов
        viewModel.movies.observe(this, movies -> {
            if (movies != null) {
                adapter.setMovies(movies);
            }
        });
        
        // Наблюдение за состоянием загрузки
        viewModel.isLoading.observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        });
        
        // Наблюдение за ошибками
        viewModel.error.observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });
    }
}
