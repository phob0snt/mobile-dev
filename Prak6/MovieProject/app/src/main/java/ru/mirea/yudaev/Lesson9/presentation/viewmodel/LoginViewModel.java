package ru.mirea.yudaev.Lesson9.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.yudaev.domain.models.User;
import ru.mirea.yudaev.domain.repository.AuthRepository;
import ru.mirea.yudaev.domain.repository.UserRepository;
import ru.mirea.yudaev.domain.usecases.SignInUseCase;
import ru.mirea.yudaev.domain.usecases.SignUpUseCase;

/**
 * ViewModel для экрана аутентификации.
 * Управляет логикой входа и регистрации пользователя через LiveData.
 */
public class LoginViewModel extends ViewModel {
    private final SignInUseCase signInUseCase;
    private final SignUpUseCase signUpUseCase;
    private final UserRepository userRepository;
    
    // LiveData для результата аутентификации
    private final MutableLiveData<AuthResult> _authResult = new MutableLiveData<>();
    public final LiveData<AuthResult> authResult = _authResult;
    
    // LiveData для состояния загрузки
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoading = _isLoading;
    
    // LiveData для ошибок валидации
    private final MutableLiveData<String> _validationError = new MutableLiveData<>();
    public final LiveData<String> validationError = _validationError;

    public LoginViewModel(AuthRepository authRepository, UserRepository userRepository) {
        this.signInUseCase = new SignInUseCase(authRepository);
        this.signUpUseCase = new SignUpUseCase(authRepository);
        this.userRepository = userRepository;
    }

    /**
     * Вход пользователя в систему
     * @param email Email пользователя
     * @param password Пароль
     */
    public void signIn(String email, String password) {
        if (!validateInput(email, password)) {
            return;
        }
        
        _isLoading.setValue(true);
        signInUseCase.execute(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess() {
                saveUserData(email);
                _authResult.postValue(new AuthResult(true, "Успешный вход"));
                _isLoading.postValue(false);
            }

            @Override
            public void onError(String error) {
                _authResult.postValue(new AuthResult(false, "Ошибка входа: " + error));
                _isLoading.postValue(false);
            }
        });
    }

    /**
     * Регистрация нового пользователя
     * @param email Email пользователя
     * @param password Пароль
     */
    public void signUp(String email, String password) {
        if (!validateInput(email, password)) {
            return;
        }
        
        if (password.length() < 6) {
            _validationError.setValue("Пароль должен содержать минимум 6 символов");
            return;
        }
        
        _isLoading.setValue(true);
        signUpUseCase.execute(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess() {
                saveUserData(email);
                _authResult.postValue(new AuthResult(true, "Успешная регистрация"));
                _isLoading.postValue(false);
            }

            @Override
            public void onError(String error) {
                _authResult.postValue(new AuthResult(false, "Ошибка регистрации: " + error));
                _isLoading.postValue(false);
            }
        });
    }

    /**
     * Валидация введенных данных
     */
    private boolean validateInput(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            _validationError.setValue("Заполните все поля");
            return false;
        }
        return true;
    }

    /**
     * Сохранение данных пользователя
     */
    private void saveUserData(String email) {
        User user = new User(email, email.split("@")[0]);
        userRepository.saveUser(user);
    }

    /**
     * Класс для результата аутентификации
     */
    public static class AuthResult {
        private final boolean success;
        private final String message;

        public AuthResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }
}
