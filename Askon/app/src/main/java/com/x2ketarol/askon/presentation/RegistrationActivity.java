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
import com.x2ketarol.askon.domain.usecases.RegisterUserUseCase;

public class RegistrationActivity extends AppCompatActivity {

    private MaterialCardView clientCard;
    private MaterialCardView expertCard;
    private EditText nameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private Button signUpButton;
    private TextView signInLink;

    private UsersRepository usersRepository;
    private RegisterUserUseCase registerUserUseCase;

    private boolean isClientMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize repository and use case
        usersRepository = new UsersRepositoryImpl(this);
        registerUserUseCase = new RegisterUserUseCase(usersRepository);

        initViews();
        setupListeners();
    }

    private void initViews() {
        clientCard = findViewById(R.id.clientCard);
        expertCard = findViewById(R.id.expertCard);
        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        signUpButton = findViewById(R.id.signUpButton);
        signInLink = findViewById(R.id.signInLink);
        
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

        signUpButton.setOnClickListener(v -> handleSignUp());

        signInLink.setOnClickListener(v -> {
            finish(); // Return to login
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

    private void handleSignUp() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Register user
        User user = registerUserUseCase.execute(name, email, password);

        if (user != null) {
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ExpertListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }
}
