# Практическая работа №11

**Тема:** Модификация слоя App. Внедрение архитектурного паттерна MVVM с использованием ViewModel и LiveData.
## Цель работы

- Рефакторинг приложения из практической работы №1 с внедрением архитектурного паттерна MVVM

- Перенос бизнес-логики из Activity в ViewModel

- Реализация наблюдения за данными через LiveData

- Решение проблемы сохранения состояния при повороте экрана
  
---

## Реализованная архитектура MVVM

### 1) View (MainActivity)

```java

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private TextView textViewMovie;
    private EditText editTextMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация ViewModel через Factory
        mainViewModel = new ViewModelProvider(
                this,
                new ViewModelFactory(getApplicationContext())
        ).get(MainViewModel.class);

        // Подписка на LiveData
        mainViewModel.getMovieTextLiveData().observe(this, newText -> {
            textViewMovie.setText(newText);
        });

        // Обработчики событий
        findViewById(R.id.buttonSaveMovie).setOnClickListener(v -> {
            mainViewModel.saveMovie(editTextMovie.getText().toString());
        });
    }
}

```

### 2) ViewModel (Новый компонент)

```java
public class MainViewModel extends ViewModel {
    private final MovieRepository movieRepository;
    private final MutableLiveData<String> movieTextLiveData = new MutableLiveData<>();

    public MainViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void saveMovie(String movieName) {
        Movie movie = new Movie(2, movieName);
        boolean isSaved = new SaveMovieToFavoriteUseCase(movieRepository).execute(movie);
        if (isSaved) {
            movieTextLiveData.setValue("Сохранено");
        } else {
            movieTextLiveData.setValue("Ошибка: пустое имя");
        }
    }

    public MutableLiveData<String> getMovieTextLiveData() {
        return movieTextLiveData;
    }
}
```

### 3) ViewModelFactory для инъекции зависимостей


```java
public class ViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public ViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        MovieStorage storage = new SharedPrefMovieStorage(context);
        MovieRepository repository = new MovieRepositoryImpl(storage);
        return (T) new MainViewModel(repository);
    }
}
```

<img width="974" height="598" alt="image" src="https://github.com/user-attachments/assets/6b3658fa-54c1-45c7-8dad-6852d5bd881d" />

<img width="974" height="631" alt="image" src="https://github.com/user-attachments/assets/ce6c0618-4484-44dc-85f3-71b398d77e7f" />


---

## Ключевые изменения и улучшения

### Решение проблемы жизненного цикла

**Было**: При повороте экрана `Activity` полностью пересоздавалась, терялось состояние

```java
// Старая реализация - логика в Activity
saveButton.setOnClickListener(v -> {
    boolean ok = saveUC.execute(new Movie(2, edit.getText().toString()));
    tv.setText(ok ? "Сохранено" : "Ошибка: пустое имя"); // Терялось при повороте
});
```

**Стало**: `ViewModel` сохраняется при изменениях конфигурации

```java
// Новая реализация - логика во ViewModel
saveButton.setOnClickListener(v -> {
    mainViewModel.saveMovie(editTextMovie.getText().toString());
    // LiveData автоматически обновит UI при повороте
});
```

<img width="974" height="408" alt="image" src="https://github.com/user-attachments/assets/0708edeb-3237-4e0b-bca1-4c5360bf42d1" />

<img width="974" height="625" alt="image" src="https://github.com/user-attachments/assets/ac68592d-2522-4811-91a4-62fddaf6345e" />

---

## Контрольное задание

### Взаимодействие Activity/Fragment с domain слоем через ViewModel

- Созданы `ViewModel` для всех основных экранов:

  - `CatalogViewModel` - для каталога продуктов
  
  - `FavoritesViewModel` - для избранного
  
  - `ProductDetailsViewModel` - для деталей продукта
  
  - `SearchViewModel` - для поиска
  
  - `HomeViewModel` - для главной страницы (уже было)

- Устранено прямое взаимодействие `Fragment/Activity` с `UseCases` и `Repository`

- Вся бизнес-логика перенесена в соответствующие `ViewModel`

**До**: Прямые вызовы `UseCases` в `Fragment`

```java

List<Product> list = new GetProductList(repo).execute();

```

**После**: Через `ViewModel`

```java

viewModel.loadProducts(categoryId);
viewModel.products.observe(...);

```

### Обновление состояния интерфейса через LiveData

- Во всех `ViewModel` добавлены `LiveData` для:

  - Основных данных (`_products`, `_favoriteProducts`, `_product`)
  
  - Состояния загрузки (`_isLoading`)
  
  - Ошибок (`_error`)
  
  - Состояния избранного (`_isFavorite`)

- Во всех `Fragment/Activity` добавлены Observer для автоматического обновления UI

- Реализована реактивность - UI обновляется автоматически при изменении данных

```java

// ViewModel
private final MutableLiveData<List<Product>> _products = new MutableLiveData<>();
public final LiveData<List<Product>> products = _products;

// Fragment
viewModel.products.observe(getViewLifecycleOwner(), products -> {
    adapter.submit(products);
});

```

### Использование MediatorLiveData для комбинирования данных

Реализовано в двух `ViewModel`:

1. `FavoritesViewModel`:

  - `MediatorLiveData<List<Product>> _favoriteProducts`
  
  - Комбинирует данные из:
  
    - `_allProducts` (все продукты из репозитория)
    
    - `_favoriteIds` (ID избранных из `SharedPreferences`)
  
  - Автоматически фильтрует и обновляет список избранных

2. `SearchViewModel`:

  - `MediatorLiveData<List<Product>> _searchResults`
  
  - Комбинирует данные из:
  
    - `_allProducts` (все продукты)
    
    - `_searchQuery` (текст поиска)
  
  - Автоматически фильтрует продукты по поисковому запросу

3. Преимущества `MediatorLiveData`:

  - Автоматическое обновление при изменении любого источника
  
  - Изоляция логики комбинирования данных
  
  - Реактивное обновление UI

<img width="974" height="609" alt="image" src="https://github.com/user-attachments/assets/73eaa828-8d31-430e-b2fe-42eaf1bf8687" />

<img width="974" height="619" alt="image" src="https://github.com/user-attachments/assets/6d7f984b-3f70-45b5-a132-f011854bdb38" />

<img width="974" height="585" alt="image" src="https://github.com/user-attachments/assets/a3df5153-60a8-49e2-899c-56ffec3027f3" />

---

## Итоги

1. Успешно внедрен паттерн MVVM - достигнуто четкое разделение ответственности между компонентами

2. Решена проблема жизненного цикла - состояние сохраняется при повороте экрана благодаря ViewModel

3. Реализована реактивность - автоматическое обновление UI через LiveData

4. Улучшена тестируемость - бизнес-логика изолирована во ViewModel

5. Соблюдены принципы чистой архитектуры - зависимости инкапсулированы через Factory

Архитектура приложения теперь соответствует современным стандартам Android-разработки и демонстрирует правильное использование компонентов Jetpack (ViewModel, LiveData).

---

**Выполнила**: Голышева Е.А.  
**Группа**: БСБО-09-22
