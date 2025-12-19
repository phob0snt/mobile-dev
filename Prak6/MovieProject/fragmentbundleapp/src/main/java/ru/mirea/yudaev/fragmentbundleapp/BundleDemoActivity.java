package ru.mirea.yudaev.fragmentbundleapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity для демонстрации передачи данных через Bundle
 */
public class BundleDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bundle_demo);

        if (savedInstanceState == null) {
            // Создаём Bundle с данными
            Bundle bundle = new Bundle();
            bundle.putInt("student_number", 28);
            bundle.putString("student_name", "Юдаев И.А.");
            bundle.putString("group", "БСБО-09-22");

            // Добавляем фрагмент с передачей данных
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, BlankFragment.class, bundle)
                    .commit();
        }
    }
}
