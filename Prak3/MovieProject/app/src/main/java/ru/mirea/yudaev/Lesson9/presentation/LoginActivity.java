package ru.mirea.yudaev.Lesson9.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.yudaev.Lesson9.R;
import ru.mirea.yudaev.Lesson9.presentation.viewmodel.LoginViewModel;
import ru.mirea.yudaev.Lesson9.presentation.viewmodel.ViewModelFactory;

/**
 * Activity для аутентификации пользователя, реализующая паттерн MVVM.
 * View слой - отвечает только за отображение UI и передачу событий в ViewModel.
 */
public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;
    private TextView switchModeTextView;
    
    private boolean isLoginMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Инициализация ViewModel через Factory
        loginViewModel = new ViewModelProvider(
                this,
                new ViewModelFactory(getApplicationContext())
        ).get(LoginViewModel.class);

        // Инициализация UI элементов
        initViews();
        
        // Настройка наблюдателей за LiveData
        setupObservers();
        
        // Настройка обработчиков событий
        setupListeners();
    }
    
    /**
     * Инициализация UI элементов
     */
    private void initViews() {
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        switchModeTextView = findViewById(R.id.switchModeTextView);
    }
    
    /**
     * Настройка наблюдателей за LiveData из ViewModel
     */
    private void setupObservers() {
        // Наблюдение за результатом аутентификации
        loginViewModel.authResult.observe(this, result -> {
            Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
            
            if (result.isSuccess()) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });
        
        // Наблюдение за ошибками валидации
        loginViewModel.validationError.observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
        
        // Наблюдение за состоянием загрузки
        loginViewModel.isLoading.observe(this, isLoading -> {
            // Отключаем кнопки во время загрузки
            loginButton.setEnabled(!isLoading);
            registerButton.setEnabled(!isLoading);
            emailEditText.setEnabled(!isLoading);
            passwordEditText.setEnabled(!isLoading);
        });
    }
    
    /**
     * Настройка обработчиков событий UI
     */
    private void setupListeners() {
        loginButton.setOnClickListener(v -> handleLogin());
        registerButton.setOnClickListener(v -> handleRegister());
        switchModeTextView.setOnClickListener(v -> switchMode());
    }

    /**
     * Обработка входа пользователя
     */
    private void handleLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        
        // Вся логика валидации и аутентификации в ViewModel
        loginViewModel.signIn(email, password);
    }

    /**
     * Обработка регистрации пользователя
     */
    private void handleRegister() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        
        // Вся логика валидации и регистрации в ViewModel
        loginViewModel.signUp(email, password);
    }

    /**
     * Переключение между режимами входа и регистрации
     */
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
