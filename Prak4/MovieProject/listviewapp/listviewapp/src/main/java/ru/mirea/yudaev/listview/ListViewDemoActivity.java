package ru.mirea.yudaev.listview;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * Activity для демонстрации ListView с фрагментами
 */
public class ListViewDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_demo);

        if (savedInstanceState == null) {
            // Загружаем ListFragment при старте
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new MovieListFragment())
                    .commit();
        }
    }
}
