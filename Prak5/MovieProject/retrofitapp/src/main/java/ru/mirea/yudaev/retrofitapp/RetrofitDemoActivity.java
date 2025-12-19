package ru.mirea.yudaev.retrofitapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity для демонстрации работы с Retrofit
 */
public class RetrofitDemoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private MovieApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_demo);

        recyclerView = findViewById(R.id.moviesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MovieAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Используем локальные данные (fallback) вместо реального API
        apiClient = new MovieApiClient(true);

        loadMovies();
    }

    private void loadMovies() {
        apiClient.getPopularMovies(new MovieApiClient.MovieCallback() {
            @Override
            public void onSuccess(List<MovieDto> movies) {
                runOnUiThread(() -> {
                    adapter.updateMovies(movies);
                    Toast.makeText(RetrofitDemoActivity.this, 
                        "Загружено " + movies.size() + " фильмов", 
                        Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(RetrofitDemoActivity.this, 
                        "Ошибка: " + error, 
                        Toast.LENGTH_LONG).show();
                });
            }
        });
    }
}
