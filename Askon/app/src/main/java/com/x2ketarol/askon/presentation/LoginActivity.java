package com.x2ketarol.askon.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

import com.x2ketarol.askon.R;
import com.x2ketarol.askon.data.repository.UsersRepositoryImpl;
import com.x2ketarol.askon.domain.model.User;
import com.x2ketarol.askon.domain.repository.UsersRepository;
import com.x2ketarol.askon.domain.usecases.LoginUserUseCase;

public class LoginActivity extends AppCompatActivity {

    private MaterialCardView clientCard;
    private MaterialCardView expertCard;
    private EditText emailInput;
    private EditText passwordInput;
    private Button signInButton;
    private TextView signUpLink;
    private TextView demoModeInfo;

    private UsersRepository usersRepository;
    private LoginUserUseCase loginUserUseCase;

    private boolean isClientMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize repository and use case
        usersRepository = new UsersRepositoryImpl(this);
        loginUserUseCase = new LoginUserUseCase(usersRepository);

        initViews();
        setupListeners();
    }

    private void initViews() {
        clientCard = findViewById(R.id.clientCard);
        expertCard = findViewById(R.id.expertCard);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        signInButton = findViewById(R.id.signInButton);
        signUpLink = findViewById(R.id.signUpLink);
        demoModeInfo = findViewById(R.id.demoModeInfo);
        
        // Set initial selection
        updateCardSelection();
    }

    private void setupListeners() {
        // Client card selection
        clientCard.setOnClickListener(v -> {
            isClientMode = true;
            updateCardSelection();
        });

        // Expert card selection
        expertCard.setOnClickListener(v -> {
            isClientMode = false;
            updateCardSelection();
        });

        // Sign In button
        signInButton.setOnClickListener(v -> handleSignIn());

        // Sign Up link
        signUpLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
    }

    private void updateCardSelection() {
        if (isClientMode) {
            clientCard.setStrokeWidth(6);
            clientCard.setStrokeColor(getResources().getColor(R.color.primary, null));
            expertCard.setStrokeWidth(2);
            expertCard.setStrokeColor(getResources().getColor(android.R.color.darker_gray, null));
        } else {
            expertCard.setStrokeWidth(6);
            expertCard.setStrokeColor(getResources().getColor(R.color.primary, null));
            clientCard.setStrokeWidth(2);
            clientCard.setStrokeColor(getResources().getColor(android.R.color.darker_gray, null));
        }
    }

    private void handleSignIn() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Use case for login
        User user = loginUserUseCase.execute(email, password);

        if (user != null) {
            Toast.makeText(this, "Welcome, " + user.getName() + "!", Toast.LENGTH_SHORT).show();
            
            // Navigate to main screen
            Intent intent = new Intent(LoginActivity.this, ExpertListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }
}
