package ru.mirea.yudaev.scrollviewapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity для демонстрации работы со ScrollView
 */
public class ScrollViewDemoActivity extends AppCompatActivity {

    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview_demo);

        scrollView = findViewById(R.id.scrollView);
        Button scrollToTopButton = findViewById(R.id.scrollToTopButton);
        Button scrollToBottomButton = findViewById(R.id.scrollToBottomButton);
        Button scrollToMiddleButton = findViewById(R.id.scrollToMiddleButton);

        // Прокрутка наверх
        scrollToTopButton.setOnClickListener(v -> {
            scrollView.smoothScrollTo(0, 0);
            showToast("Прокрутка наверх");
        });

        // Прокрутка вниз
        scrollToBottomButton.setOnClickListener(v -> {
            scrollView.fullScroll(View.FOCUS_DOWN);
            showToast("Прокрутка вниз");
        });

        // Прокрутка к середине
        scrollToMiddleButton.setOnClickListener(v -> {
            View contentView = scrollView.getChildAt(0);
            int scrollViewHeight = scrollView.getHeight();
            int contentHeight = contentView.getHeight();
            int middlePosition = (contentHeight - scrollViewHeight) / 2;
            scrollView.smoothScrollTo(0, middlePosition);
            showToast("Прокрутка к середине");
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
