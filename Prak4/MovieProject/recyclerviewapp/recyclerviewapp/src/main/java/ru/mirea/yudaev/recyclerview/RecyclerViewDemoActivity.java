package ru.mirea.yudaev.recyclerview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity для демонстрации RecyclerView с фрагментами
 */
public class RecyclerViewDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_demo);

        if (savedInstanceState == null) {
            // Загружаем MovieListFragment при старте
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new MovieListFragment())
                    .commit();
        }
    }
}
