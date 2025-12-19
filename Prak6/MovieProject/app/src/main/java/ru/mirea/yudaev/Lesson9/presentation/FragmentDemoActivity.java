package ru.mirea.yudaev.Lesson9.presentation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.mirea.yudaev.Lesson9.R;
import ru.mirea.yudaev.Lesson9.presentation.fragments.BlankFragment;
import ru.mirea.yudaev.Lesson9.presentation.fragments.DataInputFragment;
import ru.mirea.yudaev.Lesson9.presentation.fragments.MovieListFragment;

/**
 * Activity для демонстрации работы с фрагментами
 * Использует BottomNavigationView для навигации между разделами
 */
public class FragmentDemoActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_demo);
        
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        
        // Загружаем начальный фрагмент
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new MovieListFragment())
                    .commit();
        }
        
        // Навигация по фрагментам
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            
            if (id == R.id.nav_movies) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new MovieListFragment())
                        .commit();
                return true;
            } else if (id == R.id.nav_bundle) {
                // Передаём номер студента через Bundle
                Bundle args = new Bundle();
                args.putInt("student_number", 15);
                
                BlankFragment fragment = new BlankFragment();
                fragment.setArguments(args);
                
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit();
                return true;
            } else if (id == R.id.nav_result_api) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new DataInputFragment())
                        .commit();
                return true;
            } else if (id == R.id.nav_profile) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new ProfileFragment())
                        .commit();
                return true;
            }
            
            return false;
        });
    }
}
