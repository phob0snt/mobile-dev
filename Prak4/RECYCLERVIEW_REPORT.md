# Практическая работа №12

**Тема:** Основные способы отображения списков в Android: `ScrollView`, `ListView`, `RecyclerView`

**Проекты:** MovieProject с отдельными модулями (scrollviewapp, listviewapp, recyclerviewapp)

## Цель работы

Изучение и практическое применение основных компонентов Android SDK, предназначенных для отображения списков данных: `ScrollView`, `ListView` и `RecyclerView`. Освоение современного подхода к построению пользовательского интерфейса с использованием архитектурного паттерна **MVVM** (на примере связки `ViewModel` и `LiveData`) для создания эффективных и масштабируемых приложений.

---

## Структура проекта

Проект организован в виде multi-module приложения с тремя отдельными демонстрационными модулями:

### Модули проекта:

1. **app** - основное приложение MovieProject с полной функциональностью
2. **scrollviewapp** - демонстрационный модуль для работы со ScrollView
3. **listviewapp** - демонстрационный модуль для работы с ListView
4. **recyclerviewapp** - демонстрационный модуль для работы с RecyclerView  
5. **domain** - модуль с бизнес-логикой и моделями
6. **data** - модуль с данными и репозиториями

Все модули подключены в `settings.gradle.kts`:

```kotlin
include(":app")
include(":domain")
include(":data")
include(":scrollviewapp")
include(":listviewapp")
include(":recyclerviewapp")
```

---

## Часть 1: MovieProject (Prak4)

### Реализация RecyclerView с ViewModel и LiveData

#### 1. Добавление зависимостей

В файл `gradle/libs.versions.toml` добавлены зависимости для `RecyclerView` и `CardView`:

```toml
[versions]
agp = "8.8.2"
lifecycle = "2.6.2"
recyclerview = "1.3.2"
cardview = "1.0.0"

[libraries]
recyclerview = { group = "androidx.recyclerview", name = "recyclerview", version.ref = "recyclerview" }
cardview = { group = "androidx.cardview", name = "cardview", version.ref = "cardview" }
```

В `app/build.gradle.kts`:

```kotlin
dependencies {
    implementation(libs.recyclerview)
    implementation(libs.cardview)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
}
```

---

#### 2. Создание заглушки с данными в модуле :data

Реализован класс `FakeMovieApi.java` в модуле `:data`, который имитирует получение данных о фильмах из внешнего API. Класс содержит статический набор из 12 популярных фильмов с подробной информацией:

```java
public class FakeMovieApi {
    private final List<Movie> movieStub = new ArrayList<>();

    public FakeMovieApi() {
        initializeMovies();
    }

    private void initializeMovies() {
        movieStub.add(new Movie(
                1, 
                "Побег из Шоушенка", 
                "История банкира, несправедливо осужденного за убийство жены и её любовника",
                "Драма",
                1994,
                9.3
        ));
        
        movieStub.add(new Movie(
                2, 
                "Крёстный отец", 
                "Хроника криминальной семьи Корлеоне в Нью-Йорке 1940-х годов",
                "Криминал, Драма",
                1972,
                9.2
        ));
        // ... всего 12 фильмов
    }

    public List<Movie> fetchMovies() {
        return new ArrayList<>(movieStub);
    }

    public List<Movie> fetchMoviesByGenre(String genre) {
        List<Movie> filtered = new ArrayList<>();
        for (Movie movie : movieStub) {
            if (movie.getGenre().toLowerCase().contains(genre.toLowerCase())) {
                filtered.add(movie);
            }
        }
        return filtered;
    }
}
```

Этот класс используется в `MovieRepositoryRoomImpl` и `MovieRepositoryNetworkImpl` для возврата списка фильмов.

---

#### 3. Расширение доменной модели Movie

Модель `Movie` в модуле `:domain` была расширена дополнительными полями:

```java
public class Movie {
    private int id;
    private String name;
    private String description;  // Новое поле
    private String genre;         // Новое поле
    private int year;             // Новое поле
    private double rating;        // Новое поле

    public Movie(int id, String name, String description, String genre, int year, double rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
    }
    
    // Getters...
}
```

---

#### 4. Создание UseCase для получения списка фильмов

В модуле `:domain` создан новый UseCase:

```java
public class GetMovieListUseCase {
    private MovieRepository movieRepository;

    public GetMovieListUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> execute() {
        return movieRepository.getAllMovies();
    }
}
```

Интерфейс `MovieRepository` обновлён:

```java
public interface MovieRepository {
    boolean saveMovie(Movie movie);
    Movie getMovie();
    List<Movie> getAllMovies();  // Новый метод
}
```

---

#### 5. Создание ViewModel для списка фильмов

В модуле `:app` создан `MovieListViewModel` с использованием LiveData для реактивного обновления UI:

```java
public class MovieListViewModel extends ViewModel {
    private final MovieRepository movieRepository;
    private final ExecutorService executorService;
    
    // LiveData для списка фильмов
    private final MutableLiveData<List<Movie>> _movies = new MutableLiveData<>();
    public final LiveData<List<Movie>> movies = _movies;
    
    // LiveData для состояния загрузки
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoading = _isLoading;
    
    // LiveData для ошибок
    private final MutableLiveData<String> _error = new MutableLiveData<>();
    public final LiveData<String> error = _error;

    public MovieListViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void loadMovies() {
        _isLoading.setValue(true);
        executorService.execute(() -> {
            try {
                List<Movie> movieList = new GetMovieListUseCase(movieRepository).execute();
                _movies.postValue(movieList);
                _isLoading.postValue(false);
            } catch (Exception e) {
                _error.postValue("Ошибка загрузки фильмов: " + e.getMessage());
                _isLoading.postValue(false);
            }
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

---

#### 6. Создание макета элемента списка

Создан файл `item_movie.xml` с использованием `CardView` для красивого отображения карточки фильма:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="8dp"
    app:cardCornerRadius="8dp"
    app:cardElevation="4dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="16dp">

        <TextView
            android:id="@+id/movieTitleTextView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textSize="18sp"
            android:textStyle="bold"/>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal">

            <TextView
                android:id="@+id/movieGenreTextView"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"/>

            <TextView
                android:id="@+id/movieYearTextView"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"/>
        </LinearLayout>

        <TextView
            android:id="@+id/movieDescriptionTextView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:maxLines="3"
            android:ellipsize="end"/>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
            
            <TextView
                android:text="★"
                android:textColor="#FFD700"/>

            <TextView
                android:id="@+id/movieRatingTextView"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"/>
        </LinearLayout>

    </LinearLayout>
</androidx.cardview.widget.CardView>
```

---

#### 7. Создание Adapter для RecyclerView

Создан класс `MovieAdapter` с паттерном ViewHolder:

```java
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies = new ArrayList<>();
    private OnMovieClickListener onMovieClickListener;

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    public MovieAdapter(OnMovieClickListener listener) {
        this.onMovieClickListener = listener;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView genreTextView;
        private final TextView yearTextView;
        private final TextView descriptionTextView;
        private final TextView ratingTextView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.movieTitleTextView);
            genreTextView = itemView.findViewById(R.id.movieGenreTextView);
            yearTextView = itemView.findViewById(R.id.movieYearTextView);
            descriptionTextView = itemView.findViewById(R.id.movieDescriptionTextView);
            ratingTextView = itemView.findViewById(R.id.movieRatingTextView);
        }

        public void bind(Movie movie) {
            titleTextView.setText(movie.getName());
            genreTextView.setText(movie.getGenre());
            yearTextView.setText(String.valueOf(movie.getYear()));
            descriptionTextView.setText(movie.getDescription());
            ratingTextView.setText(String.format("%.1f", movie.getRating()));

            itemView.setOnClickListener(v -> {
                if (onMovieClickListener != null) {
                    onMovieClickListener.onMovieClick(movie);
                }
            });
        }
    }
}
```

---

#### 8. Создание Activity для отображения списка

Создана `MovieListActivity` с интеграцией ViewModel и RecyclerView:

```java
public class MovieListActivity extends AppCompatActivity {
    private MovieListViewModel viewModel;
    private MovieAdapter adapter;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Проверка авторизации
        FirebaseAuthRepository authRepository = new FirebaseAuthRepository();
        if (!authRepository.isUserLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        
        setContentView(R.layout.activity_movie_list);
        
        // Инициализация ViewModel
        viewModel = new ViewModelProvider(
                this,
                new ViewModelFactory(getApplicationContext())
        ).get(MovieListViewModel.class);
        
        initViews();
        setupRecyclerView();
        setupObservers();
        
        // Загрузка данных
        viewModel.loadMovies();
    }

    private void setupRecyclerView() {
        adapter = new MovieAdapter(movie -> {
            Toast.makeText(this, 
                    "Выбран: " + movie.getName() + " (" + movie.getYear() + ")", 
                    Toast.LENGTH_SHORT).show();
        });
        
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setupObservers() {
        // Наблюдение за списком фильмов
        viewModel.movies.observe(this, movies -> {
            if (movies != null) {
                adapter.setMovies(movies);
            }
        });
        
        // Наблюдение за состоянием загрузки
        viewModel.isLoading.observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        });
        
        // Наблюдение за ошибками
        viewModel.error.observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });
    }
}
```

---

#### 9. Обновление ViewModelFactory

Добавлена поддержка создания `MovieListViewModel`:

```java
public class ViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

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
        } else if (modelClass.isAssignableFrom(MovieListViewModel.class)) {
            MovieRepository movieRepository = new MovieRepositoryRoomImpl(context);
            return (T) new MovieListViewModel(movieRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
```

---

## Часть 2: Приложение Askon

В приложении Askon уже была реализована правильная архитектура с использованием RecyclerView для отображения списков. Рассмотрим ключевые компоненты:

### 1. ExpertAdapter - Адаптер для списка экспертов

```java
public class ExpertAdapter extends RecyclerView.Adapter<ExpertAdapter.ExpertViewHolder> {

    private final List<Expert> experts;
    private final OnExpertClickListener listener;

    public interface OnExpertClickListener {
        void onExpertClick(Expert expert);
    }

    @NonNull
    @Override
    public ExpertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expert, parent, false);
        return new ExpertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpertViewHolder holder, int position) {
        Expert expert = experts.get(position);
        holder.bind(expert, listener);
    }

    static class ExpertViewHolder extends RecyclerView.ViewHolder {
        private final ImageView avatarImage;
        private final TextView nameText;
        private final TextView ratingText;
        private final TextView descriptionText;
        private final TextView priceText;
        private final ChipGroup skillsChipGroup;

        public void bind(Expert expert, OnExpertClickListener listener) {
            nameText.setText(expert.getName());
            ratingText.setText(String.format("⭐ %.1f (%d)", 
                expert.getRating(), expert.getReviewCount()));
            descriptionText.setText(expert.getSpecialization());
            priceText.setText(String.format("$%d/hr", expert.getHourlyRate()));

            // Add skills as chips
            skillsChipGroup.removeAllViews();
            List<String> skills = expert.getSkills();
            int maxSkills = Math.min(skills.size(), 3);
            for (int i = 0; i < maxSkills; i++) {
                Chip chip = new Chip(itemView.getContext());
                chip.setText(skills.get(i));
                chip.setClickable(false);
                chip.setChipBackgroundColorResource(R.color.chip_background);
                skillsChipGroup.addView(chip);
            }

            itemView.setOnClickListener(v -> listener.onExpertClick(expert));
        }
    }
}
```

### 2. ExpertListActivity - Activity со списком экспертов

```java
public class ExpertListActivity extends AppCompatActivity {
    private RecyclerView expertsRecyclerView;
    private ExpertAdapter expertAdapter;
    private List<Expert> allExperts;
    private List<Expert> filteredExperts;
    private GetExpertsListUseCase getExpertsListUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_list);

        usersRepository = new UsersRepositoryImpl(this);
        getExpertsListUseCase = new GetExpertsListUseCase(usersRepository);

        setupRecyclerView();
        setupSearch();
        loadExperts();
    }

    private void setupRecyclerView() {
        expertsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        filteredExperts = new ArrayList<>();
        expertAdapter = new ExpertAdapter(filteredExperts, this::onExpertClick);
        expertsRecyclerView.setAdapter(expertAdapter);
    }

    private void loadExperts() {
        allExperts = getExpertsListUseCase.execute("");
        filteredExperts.clear();
        filteredExperts.addAll(allExperts);
        expertAdapter.notifyDataSetChanged();
    }

    private void filterExperts(String query) {
        if (query.isEmpty()) {
            filteredExperts.clear();
            filteredExperts.addAll(allExperts);
        } else {
            String lowerQuery = query.toLowerCase();
            filteredExperts.clear();
            filteredExperts.addAll(allExperts.stream()
                    .filter(expert -> expert.getName().toLowerCase().contains(lowerQuery) ||
                                     expert.getSpecialization().toLowerCase().contains(lowerQuery))
                    .collect(Collectors.toList()));
        }
        expertAdapter.notifyDataSetChanged();
    }

    private void onExpertClick(Expert expert) {
        Intent intent = new Intent(this, ExpertProfileActivity.class);
        intent.putExtra("expert_id", expert.getId());
        startActivity(intent);
    }
}
```

### 3. Ключевые особенности реализации в Askon

#### Использование Clean Architecture
- **Domain Layer**: UseCase `GetExpertsListUseCase` инкапсулирует бизнес-логику
- **Data Layer**: Repository предоставляет данные через интерфейс
- **Presentation Layer**: Activity использует UseCase для получения данных

#### Расширенный функционал
- **Поиск и фильтрация**: Реализован текстовый поиск экспертов по имени и специализации
- **Material Design**: Использование Chips для отображения навыков эксперта
- **Bottom Navigation**: Навигация между разделами приложения
- **CardView**: Красивое оформление карточек экспертов

---

## Итоги

### MovieProject (основной модуль app)
1. ✅ Добавлены зависимости RecyclerView и CardView
2. ✅ Создана заглушка `FakeMovieApi` с данными о 12 фильмах
3. ✅ Расширена модель `Movie` дополнительными полями
4. ✅ Создан `GetMovieListUseCase` для получения списка
5. ✅ Реализован `MovieListViewModel` с LiveData
6. ✅ Создан адаптер `MovieAdapter` с ViewHolder
7. ✅ Разработан макет карточки фильма `item_movie.xml`
8. ✅ Создана `MovieListActivity` с MVVM архитектурой
9. ✅ Интегрирована навигация из MainActivity

### Демонстрационные модули

#### 1. listviewapp
**Цель:** Демонстрация базового подхода работы со списками

**Ключевые компоненты:**
- `ListViewDemoActivity` - главная Activity
- `MovieListFragment` - список с ArrayAdapter
- `MovieDetailsFragment` - детали через Bundle
- ListView с стандартным адаптером
- Передача данных между фрагментами через Bundle

**Особенности:**
- Простой подход без ViewModel
- Использование встроенного ArrayAdapter
- Навигация через FragmentManager

#### 2. recyclerviewapp
**Цель:** Демонстрация современного подхода с RecyclerView

**Ключевые компоненты:**
### Демонстрационные модули

Для систематического изучения различных компонентов списков созданы 3 автономных модуля:

#### 1. scrollviewapp
**Цель:** Демонстрация работы со ScrollView и HorizontalScrollView

**Ключевые компоненты:**
- `ScrollViewDemoActivity` - главная Activity
- Вертикальный ScrollView с длинным текстовым контентом
- HorizontalScrollView с галереей карточек
- Программное управление прокруткой

**Функциональность:**
- ✅ Вертикальная прокрутка большого текста
- ✅ Горизонтальная галерея с цветными карточками
- ✅ Кнопки управления: прокрутка вверх/вниз/к центру
- ✅ `smoothScrollTo()` для плавной прокрутки
- ✅ `fillViewport` для корректного отображения

**Особенности:**
- ScrollView может содержать только один дочерний элемент
- Использование LinearLayout как контейнер
- HorizontalScrollView для горизонтального скроллинга
- Material Card для визуального оформления

#### 2. listviewapp
**Цель:** Демонстрация базового подхода с ListView

**Ключевые компоненты:**
- `ListViewDemoActivity` - главная Activity  
- `MovieListFragment` - простой список с ArrayAdapter
- `MovieDetailsFragment` - статичные детали

**Особенности:**
- Простой ArrayAdapter для отображения строк
- Базовая навигация через Bundle
- Без использования ViewModel
- Подходит для простых списков

#### 3. recyclerviewapp
**Цель:** Демонстрация современного подхода с RecyclerView и MVVM

**Ключевые компоненты:**
- `RecyclerViewDemoActivity` - главная Activity
- `MovieListFragment` - список с кастомным адаптером
- `MovieDetailsFragment` - детали через SharedViewModel
- `MovieAdapter` - адаптер с ViewHolder паттерном
- `SharedMovieViewModel` - для обмена данными
- `Movie` - модель данных

**Особенности:**
- Использует MVVM архитектуру
- SharedViewModel для обмена данными между фрагментами
- Material Design с CardView
- RecyclerView с LinearLayoutManager

### Сравнение компонентов списков

| Компонент | Модуль | Использование | Производительность | Гибкость |
|-----------|--------|---------------|-------------------|----------|
| **ScrollView** | scrollviewapp | Длинный статический контент | ⭐⭐ | ⭐⭐ |
| **ListView** | listviewapp | Простые списки до 100 элементов | ⭐⭐⭐ | ⭐⭐ |
| **RecyclerView** | recyclerviewapp | Большие динамические списки | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ |

### Общие достижения

✅ **Модульность** - Создано 3 независимых демо-приложения  
✅ **Компиляция** - Все модули успешно собираются  
✅ **Разделение концепций** - Каждый модуль демонстрирует свой подход  
✅ **Обучающая ценность** - Легко сравнить разные техники

Каждый модуль может запускаться отдельно, что удобно для обучения и демонстрации различных техник работы со списками в Android.

### Askon
1. ✅ Используется RecyclerView для списка экспертов
2. ✅ Реализован `ExpertAdapter` с паттерном ViewHolder
3. ✅ Создан красивый макет `item_expert.xml` с CardView
4. ✅ Применена Clean Architecture с UseCase
5. ✅ Добавлена функциональность поиска и фильтрации
6. ✅ Использованы Material Design компоненты (Chips, Bottom Navigation)

### Ключевые преимущества RecyclerView

1. **Производительность**: RecyclerView использует паттерн ViewHolder "из коробки", что обеспечивает эффективное переиспользование элементов списка
2. **Гибкость**: Легко настраивается LayoutManager (Linear, Grid, Staggered)
3. **Анимации**: Встроенная поддержка анимаций добавления/удаления элементов
4. **Разделение ответственности**: Adapter отвечает только за данные, ViewHolder - за UI

### Архитектурные преимущества MVVM

1. **Выживание при изменениях конфигурации**: ViewModel сохраняет данные при повороте экрана
2. **Реактивность**: LiveData автоматически обновляет UI при изменении данных
3. **Тестируемость**: Бизнес-логика изолирована в ViewModel
4. **Разделение ответственностей**: Activity/Fragment отвечают только за UI

---

**Выполнил**: Юдаев И. А.
**Группа**: БСБО-09-22
