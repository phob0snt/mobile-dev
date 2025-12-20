# Практическая работа №4
## Основные способы отображения списков в Android: ScrollView, ListView, RecyclerView

---

## Цель работы

Изучение и практическое применение основных компонентов Android SDK для отображения списков данных: `ScrollView`, `ListView` и `RecyclerView`. Освоение современного подхода к построению пользовательского интерфейса с использованием архитектурного паттерна MVVM с `ViewModel` и `LiveData`.

---

## Структура проекта

Проект организован в виде multi-module приложения с тремя демонстрационными модулями:

- **scrollviewapp** - демонстрация работы со ScrollView
- **listviewapp** - демонстрация работы с ListView
- **recyclerviewapp** - демонстрация работы с RecyclerView
- **domain** - модуль с бизнес-логикой
- **data** - модуль с данными

---

## 1. Реализация ScrollViewApp

### Описание
Простое приложение для демонстрации программного управления прокруткой с помощью `ScrollView`.

### Ключевые компоненты

**ScrollViewDemoActivity.java:**
```java
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
}
```

### Особенности
- Использование `smoothScrollTo()` для плавной прокрутки
- Использование `fullScroll()` для мгновенной прокрутки
- Вычисление позиции середины контента

---

## 2. Реализация ListViewApp

### Описание
Приложение с использованием `ListView` для отображения списка фильмов с навигацией через фрагменты.

### Ключевые компоненты

**ListViewDemoActivity.java:**
```java
public class ListViewDemoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_demo);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new MovieListFragment())
                    .commit();
        }
    }
}
```

**MovieListFragment.java:**
```java
public class MovieListFragment extends Fragment {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.moviesListView);

        String[] movies = {
                "Побег из Шоушенка (1994)",
                "Крёстный отец (1972)",
                "Тёмный рыцарь (2008)",
                "Криминальное чтиво (1994)",
                "Список Шиндлера (1993)",
                "Форрест Гамп (1994)",
                "Начало (2010)",
                "Бойцовский клуб (1999)",
                "Матрица (1999)",
                "Интерстеллар (2014)"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                movies
        );

        listView.setAdapter(adapter);

        // Обработка клика на элемент
        listView.setOnItemClickListener((parent, itemView, position, id) -> {
            String selectedMovie = movies[position];
            MovieDetailsFragment detailsFragment = 
                MovieDetailsFragment.newInstance(selectedMovie);
            
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, detailsFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }
}
```

### Особенности
- Использование `ArrayAdapter` для простого списка
- Навигация между фрагментами через `FragmentManager`
- Обработка кликов через `OnItemClickListener`
- Передача данных через `Bundle`

---

## 3. Реализация RecyclerViewApp

### Описание
Современное приложение с использованием `RecyclerView`, `ViewModel` и `LiveData` по паттерну MVVM.

### Модель данных

**Movie.java:**
```java
public class Movie {
    private String title;
    private int year;
    private String genre;
    private double rating;

    public Movie(String title, int year, String genre, double rating) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
    }

    // Геттеры
    public String getTitle() { return title; }
    public int getYear() { return year; }
    public String getGenre() { return genre; }
    public double getRating() { return rating; }
}
```

### ViewModel

**SharedMovieViewModel.java:**
```java
public class SharedMovieViewModel extends ViewModel {
    private final MutableLiveData<Movie> selectedMovie = new MutableLiveData<>();

    public void selectMovie(Movie movie) {
        selectedMovie.setValue(movie);
    }

    public LiveData<Movie> getSelectedMovie() {
        return selectedMovie;
    }
}
```

### Адаптер

**MovieAdapter.java:**
```java
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private final List<Movie> movies;
    private final OnMovieClickListener listener;

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    public MovieAdapter(List<Movie> movies, OnMovieClickListener listener) {
        this.movies = movies;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie, listener);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView yearTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.movieTitleTextView);
            yearTextView = itemView.findViewById(R.id.movieYearTextView);
        }

        public void bind(Movie movie, OnMovieClickListener listener) {
            titleTextView.setText(movie.getTitle());
            yearTextView.setText(String.valueOf(movie.getYear()));
            itemView.setOnClickListener(v -> listener.onMovieClick(movie));
        }
    }
}
```

### Фрагмент со списком

**MovieListFragment.java:**
```java
public class MovieListFragment extends Fragment {
    private SharedMovieViewModel viewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity())
            .get(SharedMovieViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.moviesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Movie> movies = getMovieList();
        
        MovieAdapter adapter = new MovieAdapter(movies, movie -> {
            // Передаём выбранный фильм в ViewModel
            viewModel.selectMovie(movie);

            // Навигация к деталям
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new MovieDetailsFragment())
                    .addToBackStack(null)
                    .commit();
        });

        recyclerView.setAdapter(adapter);
    }

    private List<Movie> getMovieList() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Побег из Шоушенка", 1994, "Драма", 9.3));
        movies.add(new Movie("Крёстный отец", 1972, "Криминал", 9.2));
        movies.add(new Movie("Тёмный рыцарь", 2008, "Боевик", 9.0));
        movies.add(new Movie("Криминальное чтиво", 1994, "Криминал", 8.9));
        movies.add(new Movie("Список Шиндлера", 1993, "Драма", 8.9));
        movies.add(new Movie("Форрест Гамп", 1994, "Драма", 8.8));
        movies.add(new Movie("Начало", 2010, "Фантастика", 8.8));
        movies.add(new Movie("Бойцовский клуб", 1999, "Драма", 8.8));
        movies.add(new Movie("Матрица", 1999, "Фантастика", 8.7));
        movies.add(new Movie("Интерстеллар", 2014, "Фантастика", 8.6));
        return movies;
    }
}
```

### Activity

**RecyclerViewDemoActivity.java:**
```java
public class RecyclerViewDemoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_demo);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new MovieListFragment())
                    .commit();
        }
    }
}
```

### Особенности
- **Паттерн MVVM**: разделение логики и представления
- **ViewModel**: хранение данных при изменении конфигурации
- **LiveData**: реактивное обновление UI
- **ViewHolder**: переиспользование View для производительности
- **RecyclerView**: эффективное отображение больших списков
- **Shared ViewModel**: обмен данными между фрагментами

---

## Выводы

В ходе практической работы:

1. **ScrollView** подходит для небольшого статичного контента, но неэффективен для больших списков
2. **ListView** хорош для простых списков, но устарел и имеет ограничения
3. **RecyclerView** - современный и рекомендуемый подход:
   - Эффективное использование памяти
   - Высокая производительность
   - Гибкость и расширяемость
   - Интеграция с MVVM паттерном

4. **MVVM паттерн** с ViewModel и LiveData обеспечивает:
   - Разделение ответственности
   - Сохранение данных при изменении конфигурации
   - Реактивное обновление UI
   - Легкое тестирование

5. **Фрагменты** позволяют создавать модульную навигацию и обмениваться данными через Shared ViewModel
