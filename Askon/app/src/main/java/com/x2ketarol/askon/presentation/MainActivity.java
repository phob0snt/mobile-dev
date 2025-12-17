package com.x2ketarol.askon.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.x2ketarol.askon.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Check if user is logged in (TODO: implement proper session management)
        boolean isLoggedIn = false;
        
        if (!isLoggedIn) {
            // Redirect to login screen
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        
        setContentView(R.layout.activity_main);
        
        TextView tvResults = findViewById(R.id.tvResults);
        Button btnGoToExperts = findViewById(R.id.btnGoToExperts);
        
        btnGoToExperts.setOnClickListener(v -> {
            Intent intent = new Intent(this, ExpertListActivity.class);
            startActivity(intent);
        });
    }
}
