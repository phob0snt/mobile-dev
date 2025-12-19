package ru.mirea.yudaev.fragmentresultapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity для демонстрации Fragment Result API
 */
public class ResultApiDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_api_demo);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, DataInputFragment.class, null)
                    .commit();
        }
    }
}
