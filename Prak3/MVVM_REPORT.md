# Практическая работа №11

**Тема:** Модификация слоя App. Внедрение архитектурного паттерна MVVM с использованием ViewModel и LiveData.

## Цель работы

- Рефакторинг приложения из практической работы №2 с внедрением архитектурного паттерна MVVM
- Перенос бизнес-логики из Activity в ViewModel
- Реализация наблюдения за данными через LiveData
- Решение проблемы сохранения состояния при повороте экрана

---

## Реализованная архитектура MVVM

### 1) View (MainActivity)

```java
public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private EditText movieNameInputField;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        
        // Инициализация ViewModel через Factory для инъекции зависимостей
        mainViewModel = new ViewModelProvider(
                this,
                new ViewModelFactory(getApplicationContext())
        ).get(MainViewModel.class);
        
        // Инициализация UI элементов
        initViews();
        
        // Настройка наблюдателей за LiveData
        setupObservers();
        
        // Настройка обработчиков событий
        setupListeners();
    }
    
    private void setupObservers() {
        // Наблюдение за результатом операций с фильмами
        mainViewModel.movieTextLiveData.observe(this, text -> {
            resultTextView.setText(text);
        });
        
        // Наблюдение за состоянием загрузки
        mainViewModel.isLoading.observe(this, isLoading -> {
            // Управление отображением загрузки
        });
    }
    
    private void setupListeners() {
        findViewById(R.id.saveMovieButton).setOnClickListener(view -> {
            String movieName = movieNameInputField.getText().toString();
            mainViewModel.saveMovie(movieName);
        });
        
        findViewById(R.id.showMovieButton).setOnClickListener(view -> {
            mainViewModel.loadFavoriteMovie();
        });
    }
}
```

**Основные изменения:**
- Activity теперь не содержит бизнес-логику, только UI код
- Инициализация ViewModel через `ViewModelProvider` и `ViewModelFactory`
- Все данные получаются через подписку на LiveData
- Обработчики событий делегируют вызовы в ViewModel

### 2) ViewModel (MainViewModel)

```java
public class MainViewModel extends ViewModel {
    private final MovieRepository movieRepository;
    private final ExecutorService executorService;
    
    // LiveData для текста результата операции
    private final MutableLiveData<String> _movieTextLiveData = new MutableLiveData<>();
    public final LiveData<String> movieTextLiveData = _movieTextLiveData;
    
    // LiveData для состояния загрузки
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoading = _isLoading;

    public MainViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void saveMovie(String movieName) {
        _isLoading.setValue(true);
        executorService.execute(() -> {
            Movie movie = new Movie(2, movieName);
            boolean result = new SaveMovieToFavoriteUseCase(movieRepository).execute(movie);
            
            _movieTextLiveData.postValue(result ? 
                "Фильм сохранен: " + movieName : 
                "Ошибка: пустое имя фильма");
            _isLoading.postValue(false);
        });
    }

    public void loadFavoriteMovie() {
        _isLoading.setValue(true);
        executorService.execute(() -> {
            Movie movie = new GetFavoriteFilmUseCase(movieRepository).execute();
            
            _movieTextLiveData.postValue(movie != null ? 
                "Избранный фильм: " + movie.getName() : 
                "Нет избранного фильма");
            _isLoading.postValue(false);
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}
```

**Ключевые особенности:**
- Вся бизнес-логика инкапсулирована в ViewModel
- Использование MutableLiveData для внутренних изменений
- Публичные LiveData только для чтения (immutable)
- ExecutorService управляется внутри ViewModel
- Правильная очистка ресурсов в `onCleared()`

### 3) ViewModelFactory для инъекции зависимостей

```java
public class ViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public ViewModelFactory(Context context) {
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            MovieRepository movieRepository = new MovieRepositoryRoomImpl(context);
            return (T) new MainViewModel(movieRepository);
        } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            AuthRepository authRepository = new FirebaseAuthRepository();
            UserRepository userRepository = new UserRepositoryImpl(context);
            return (T) new LoginViewModel(authRepository, userRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
```

**Преимущества:**
- Централизованное создание зависимостей
- Упрощенное тестирование (можно подменить реализации)
- Соблюдение принципа инверсии зависимостей
- Переиспользуемость кода

---

## Ключевые изменения и улучшения

### Решение проблемы жизненного цикла

**Было**: При повороте экрана Activity полностью пересоздавалась, терялось состояние

```java
// Старая реализация - логика в Activity
findViewById(R.id.saveMovieButton).setOnClickListener(view -> {
    executorService.execute(() -> {
        boolean result = new SaveMovieToFavoriteUseCase(movieRepository)
                .execute(new Movie(2, movieName));
        runOnUiThread(() -> 
            textView.setText(String.format("Save result: %s", result))
        );
    });
});
// При повороте экрана текст в textView терялся
```

**Стало**: ViewModel сохраняется при изменениях конфигурации

```java
// Новая реализация - логика во ViewModel
mainViewModel.saveMovie(movieName);

// В setupObservers()
mainViewModel.movieTextLiveData.observe(this, text -> {
    resultTextView.setText(text);
});
// LiveData автоматически восстанавливает последнее значение при повороте
```

**Преимущества:**
- Данные сохраняются автоматически при повороте экрана
- Нет необходимости в `onSaveInstanceState()`
- Асинхронные операции продолжают выполняться
- UI автоматически обновляется после пересоздания Activity

---

## Контрольное задание

### 1. Взаимодействие Activity/Fragment с domain слоем через ViewModel

**Реализовано для двух экранов:**

1. **MainActivity с MainViewModel**
   - Управление избранными фильмами
   - Сохранение и загрузка через UseCase
   
2. **LoginActivity с LoginViewModel**
   - Аутентификация пользователя
   - Регистрация и вход через UseCase

**До**: Прямые вызовы UseCases в Activity

```java
// MainActivity - старая версия
MovieRepository movieRepository = new MovieRepositoryRoomImpl(this);
executorService.execute(() -> {
    boolean result = new SaveMovieToFavoriteUseCase(movieRepository)
            .execute(new Movie(2, movieName));
    runOnUiThread(() -> textView.setText("Result: " + result));
});
```

**После**: Через ViewModel

```java
// MainActivity - новая версия
mainViewModel.saveMovie(movieName);

// MainViewModel
public void saveMovie(String movieName) {
    executorService.execute(() -> {
        boolean result = new SaveMovieToFavoriteUseCase(movieRepository)
                .execute(new Movie(2, movieName));
        _movieTextLiveData.postValue(result ? "Сохранено" : "Ошибка");
    });
}
```

### 2. Обновление состояния интерфейса через LiveData

**Реализованы следующие LiveData:**

#### MainViewModel:
- `movieTextLiveData` - результат операций с фильмами
- `isLoading` - состояние загрузки

```java
public class MainViewModel extends ViewModel {
    private final MutableLiveData<String> _movieTextLiveData = new MutableLiveData<>();
    public final LiveData<String> movieTextLiveData = _movieTextLiveData;
    
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoading = _isLoading;
}
```

#### LoginViewModel:
- `authResult` - результат аутентификации
- `validationError` - ошибки валидации
- `isLoading` - состояние загрузки

```java
public class LoginViewModel extends ViewModel {
    private final MutableLiveData<AuthResult> _authResult = new MutableLiveData<>();
    public final LiveData<AuthResult> authResult = _authResult;
    
    private final MutableLiveData<String> _validationError = new MutableLiveData<>();
    public final LiveData<String> validationError = _validationError;
    
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoading = _isLoading;
}
```

**Наблюдение в Activity:**

```java
// MainActivity
private void setupObservers() {
    mainViewModel.movieTextLiveData.observe(this, text -> {
        resultTextView.setText(text);
    });
    
    mainViewModel.isLoading.observe(this, isLoading -> {
        // Управление состоянием загрузки
    });
}

// LoginActivity
private void setupObservers() {
    loginViewModel.authResult.observe(this, result -> {
        Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
        if (result.isSuccess()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    });
    
    loginViewModel.validationError.observe(this, error -> {
        if (error != null && !error.isEmpty()) {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }
    });
    
    loginViewModel.isLoading.observe(this, isLoading -> {
        loginButton.setEnabled(!isLoading);
        registerButton.setEnabled(!isLoading);
    });
}
```

### 3. Преимущества внедрения MVVM

1. **Разделение ответственности**
   - View отвечает только за UI
   - ViewModel содержит бизнес-логику
   - Четкая граница между слоями

2. **Сохранение состояния**
   - Автоматическое восстановление при повороте
   - Продолжение асинхронных операций
   - Нет потери данных

3. **Тестируемость**
   - ViewModel можно тестировать без Android Framework
   - Легко мокировать зависимости через Factory
   - Unit-тесты для бизнес-логики

4. **Реактивность**
   - Автоматическое обновление UI
   - Нет необходимости в ручном управлении состоянием
   - Меньше boilerplate кода

5. **Управление жизненным циклом**
   - ViewModel переживает изменения конфигурации
   - Автоматическая очистка ресурсов в `onCleared()`
   - LiveData учитывает жизненный цикл Activity

---

## Итоги

1. **Успешно внедрен паттерн MVVM** - достигнуто четкое разделение ответственности между компонентами

2. **Решена проблема жизненного цикла** - состояние сохраняется при повороте экрана благодаря ViewModel

3. **Реализована реактивность** - автоматическое обновление UI через LiveData

4. **Улучшена тестируемость** - бизнес-логика изолирована во ViewModel и может тестироваться независимо

5. **Соблюдены принципы чистой архитектуры** - зависимости инкапсулированы через ViewModelFactory

6. **Внедрено для всех экранов** - MainActivity и LoginActivity переведены на MVVM

Архитектура приложения теперь соответствует современным стандартам Android-разработки и демонстрирует правильное использование компонентов Android Jetpack (ViewModel, LiveData).

---

**Выполнил**: Юдаев И. А.
**Группа**: БСБО-09-22  
**Дата**: 18 декабря 2025
