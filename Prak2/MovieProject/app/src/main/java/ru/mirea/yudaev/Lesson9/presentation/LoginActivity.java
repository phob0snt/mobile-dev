package ru.mirea.yudaev.Lesson9.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.yudaev.Lesson9.R;
import ru.mirea.yudaev.data.firebase.FirebaseAuthRepository;
import ru.mirea.yudaev.data.storage.sharedprefs.UserRepositoryImpl;
import ru.mirea.yudaev.domain.models.User;
import ru.mirea.yudaev.domain.repository.AuthRepository;
import ru.mirea.yudaev.domain.usecases.SignInUseCase;
import ru.mirea.yudaev.domain.usecases.SignUpUseCase;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;
    private TextView switchModeTextView;
    
    private SignInUseCase signInUseCase;
    private SignUpUseCase signUpUseCase;
    private UserRepositoryImpl userRepository;
    private boolean isLoginMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AuthRepository authRepository = new FirebaseAuthRepository();
        signInUseCase = new SignInUseCase(authRepository);
        signUpUseCase = new SignUpUseCase(authRepository);
        userRepository = new UserRepositoryImpl(this);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        switchModeTextView = findViewById(R.id.switchModeTextView);

        loginButton.setOnClickListener(v -> handleLogin());
        registerButton.setOnClickListener(v -> handleRegister());
        switchModeTextView.setOnClickListener(v -> switchMode());
    }

    private void handleLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        signInUseCase.execute(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess() {
                // Сохраняем информацию о пользователе
                User user = new User(email, email.split("@")[0]);
                userRepository.saveUser(user);
                
                Toast.makeText(LoginActivity.this, "Успешный вход", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(LoginActivity.this, "Ошибка: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleRegister() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Пароль должен содержать минимум 6 символов", Toast.LENGTH_SHORT).show();
            return;
        }

        signUpUseCase.execute(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess() {
                // Сохраняем информацию о пользователе
                User user = new User(email, email.split("@")[0]);
                userRepository.saveUser(user);
                
                Toast.makeText(LoginActivity.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(LoginActivity.this, "Ошибка: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void switchMode() {
        isLoginMode = !isLoginMode;
        if (isLoginMode) {
            loginButton.setVisibility(android.view.View.VISIBLE);
            registerButton.setVisibility(android.view.View.GONE);
            switchModeTextView.setText("Нет аккаунта? Зарегистрироваться");
        } else {
            loginButton.setVisibility(android.view.View.GONE);
            registerButton.setVisibility(android.view.View.VISIBLE);
            switchModeTextView.setText("Есть аккаунт? Войти");
        }
    }
}
