package ru.mirea.yudaev.fragmentviewmodelapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity для демонстрации обмена данными через SharedViewModel
 */
public class ViewModelDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmodel_demo);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, MovieListFragment.class, null)
                    .commit();
        }
    }
}
