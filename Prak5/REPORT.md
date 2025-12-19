# Практическая работа №5 (Практика 13)
## Разработка мобильного приложения с использованием библиотек Retrofit и Glide для работы с сетью

---

## 🎯 Цель работы

Освоить использование библиотек **Retrofit** для выполнения сетевых запросов и **Glide** для загрузки и отображения изображений в Android-приложении. Интегрировать эти библиотеки в архитектуру Clean Architecture с использованием паттерна MVVM.

---

## 📋 Задачи практической работы

1. **Добавить зависимости Retrofit и Glide** в проект MovieProject
2. **Создать API интерфейс** для работы с TMDB (The Movie Database)
3. **Реализовать DTO (Data Transfer Objects)** для сериализации/десериализации JSON
4. **Настроить RetrofitClient** с logging interceptor для отладки
5. **Создать Mapper** для преобразования DTO в доменные модели
6. **Интегрировать Glide** в адаптер для загрузки постеров фильмов
7. **Применить аналогичные изменения** в проекте Askon для загрузки аватаров экспертов

---

## 🏗️ Выполненные работы

### 1. Настройка зависимостей

#### MovieProject: `gradle/libs.versions.toml`
```toml
[versions]
# ... existing versions
retrofit = "2.9.0"
glide = "4.16.0"
okhttp = "4.12.0"

[libraries]
# Retrofit
retrofit = { group = "com.squareup.retrofit2", name = "retrofit", version.ref = "retrofit" }
retrofit-converter-gson = { group = "com.squareup.retrofit2", name = "converter-gson", version.ref = "retrofit" }

# Glide
glide = { group = "com.github.bumptech.glide", name = "glide", version.ref = "glide" }
glide-compiler = { group = "com.github.bumptech.glide", name = "compiler", version.ref = "glide" }

# OkHttp
okhttp = { group = "com.squareup.okhttp3", name = "okhttp", version.ref = "okhttp" }
okhttp-logging-interceptor = { group = "com.squareup.okhttp3", name = "logging-interceptor", version.ref = "okhttp" }
```

#### MovieProject: `app/build.gradle.kts`
```kotlin
dependencies {
    // Glide для загрузки изображений
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)
    // ... остальные зависимости
}
```

#### MovieProject: `data/build.gradle.kts`
```kotlin
dependencies {
    // Retrofit для сетевых запросов
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    
    // OkHttp для логирования
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    // ... остальные зависимости
}
```

---

### 2. Создание DTO (Data Transfer Objects)

#### `MovieDto.java` - DTO для фильма
```java
package ru.mirea.yudaev.data.network.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieDto {
    @SerializedName("id")
    private int id;
    
    @SerializedName("title")
    private String title;
    
    @SerializedName("overview")
    private String overview;
    
    @SerializedName("genre_ids")
    private List<Integer> genreIds;
    
    @SerializedName("release_date")
    private String releaseDate;
    
    @SerializedName("vote_average")
    private double voteAverage;
    
    @SerializedName("poster_path")
    private String posterPath;
    
    public String getFullPosterUrl() {
        if (posterPath != null && !posterPath.isEmpty()) {
            return "https://image.tmdb.org/t/p/w500" + posterPath;
        }
        return "https://via.placeholder.com/500x750?text=No+Image";
    }
    
    // getters and setters...
}
```

**Ключевые особенности:**
- Аннотации `@SerializedName` для маппинга JSON полей
- Метод `getFullPosterUrl()` формирует полный URL для постера
- Использует базовый путь TMDB: `https://image.tmdb.org/t/p/w500`

#### `MoviesResponse.java` - обёртка для списка фильмов
```java
package ru.mirea.yudaev.data.network.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MoviesResponse {
    @SerializedName("page")
    private int page;
    
    @SerializedName("results")
    private List<MovieDto> results;
    
    @SerializedName("total_pages")
    private int totalPages;
    
    // getters...
}
```

---

### 3. Создание API интерфейса

#### `MovieApiService.java`
```java
package ru.mirea.yudaev.data.network.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.mirea.yudaev.data.network.dto.MoviesResponse;

public interface MovieApiService {
    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );
    
    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );
}
```

**Используемые endpoints:**
- `GET /movie/popular` - популярные фильмы
- `GET /movie/top_rated` - топ-рейтинговые фильмы

---

### 4. Настройка Retrofit клиента

#### `RetrofitClient.java`
```java
package ru.mirea.yudaev.data.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;

public class RetrofitClient {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "demo_key_use_your_own";
    
    private static Retrofit retrofit = null;
    private static MovieApiService movieApiService = null;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            // Logging interceptor для отладки
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            
            // Настройка OkHttpClient с таймаутами
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();
            
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    
    public static MovieApiService getInstance() {
        if (movieApiService == null) {
            movieApiService = getRetrofitInstance().create(MovieApiService.class);
        }
        return movieApiService;
    }
}
```

**Ключевые особенности:**
- Singleton pattern для переиспользования экземпляра
- `HttpLoggingInterceptor` с уровнем `BODY` для детального логирования
- Таймауты: 30 секунд для всех операций
- `GsonConverterFactory` для автоматической сериализации/десериализации

---

### 5. Создание Mapper для преобразования DTO → Domain

#### `MovieMapper.java`
```java
package ru.mirea.yudaev.data.mapper;

import ru.mirea.yudaev.data.network.dto.MovieDto;
import ru.mirea.yudaev.domain.models.Movie;
import java.util.ArrayList;
import java.util.List;

public class MovieMapper {
    
    public static Movie mapToDomain(MovieDto dto) {
        if (dto == null) return null;
        
        return new Movie(
                dto.getId(),
                dto.getTitle(),
                dto.getOverview() != null ? dto.getOverview() : "Описание отсутствует",
                mapGenreIdsToString(dto.getGenreIds()),
                extractYearFromDate(dto.getReleaseDate()),
                dto.getVoteAverage(),
                dto.getFullPosterUrl()
        );
    }
    
    public static List<Movie> mapToDomainList(List<MovieDto> dtos) {
        if (dtos == null) return new ArrayList<>();
        
        List<Movie> movies = new ArrayList<>();
        for (MovieDto dto : dtos) {
            Movie movie = mapToDomain(dto);
            if (movie != null) movies.add(movie);
        }
        return movies;
    }
    
    private static String mapGenreIdsToString(List<Integer> genreIds) {
        if (genreIds == null || genreIds.isEmpty()) return "Разное";
        
        // Маппинг основных жанров TMDB
        Integer firstGenreId = genreIds.get(0);
        switch (firstGenreId) {
            case 28: return "Боевик";
            case 12: return "Приключения";
            case 16: return "Мультфильм";
            case 35: return "Комедия";
            case 80: return "Криминал";
            case 18: return "Драма";
            case 14: return "Фэнтези";
            case 878: return "Фантастика";
            case 53: return "Триллер";
            // ... остальные жанры
            default: return "Разное";
        }
    }
    
    private static int extractYearFromDate(String releaseDate) {
        if (releaseDate == null || releaseDate.length() < 4) return 0;
        try {
            return Integer.parseInt(releaseDate.substring(0, 4));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
```

**Функциональность:**
- Преобразование `MovieDto` → `Movie` (domain model)
- Маппинг ID жанров TMDB в русские названия
- Извлечение года из даты формата "YYYY-MM-DD"
- Обработка null-значений с fallback

---

### 6. Обновление NetworkApi с Retrofit

#### `NetworkApi.java`
```java
package ru.mirea.yudaev.data.network;

import android.util.Log;
import retrofit2.Call;
import retrofit2.Response;
import java.io.IOException;
import java.util.List;

public class NetworkApi {
    private static final String TAG = "NetworkApi";
    private final MovieApiService apiService;
    private final FakeMovieApi fakeMovieApi;
    
    public NetworkApi() {
        this.apiService = RetrofitClient.getInstance().getMovieApiService();
        this.fakeMovieApi = new FakeMovieApi();
    }
    
    public List<Movie> getMoviesFromNetwork() {
        try {
            Call<MoviesResponse> call = apiService.getPopularMovies();
            Response<MoviesResponse> response = call.execute();
            
            if (response.isSuccessful() && response.body() != null) {
                List<Movie> movies = MovieMapper.mapToDomainList(response.body().getResults());
                Log.d(TAG, "Загружено фильмов из API: " + movies.size());
                return movies;
            } else {
                Log.w(TAG, "API вернул ошибку: " + response.code());
                return fakeMovieApi.fetchMovies();
            }
        } catch (IOException e) {
            Log.e(TAG, "Ошибка сети, используем FakeMovieApi", e);
            return fakeMovieApi.fetchMovies();
        }
    }
}
```

**Стратегия обработки ошибок:**
1. Попытка загрузки из TMDB API
2. При ошибке сети → fallback на `FakeMovieApi`
3. Детальное логирование всех операций

---

### 7. Обновление FakeMovieApi с imageUrl

Добавлены URL постеров для всех 12 фильмов:
```java
movieStub.add(new Movie(1, "Побег из Шоушенка", "...", "Драма", 1994, 9.3,
    "https://m.media-amazon.com/images/M/MV5BNDE3ODcxYzMt...jpg"));
```

**Источники изображений:**
- IMDB официальные постеры
- Высокое разрешение (500x750)

---

### 8. Интеграция Glide в MovieAdapter

#### Обновлённый макет `item_movie.xml`
```xml
<LinearLayout android:orientation="horizontal">
    <!-- Постер фильма -->
    <ImageView
        android:id="@+id/moviePosterImageView"
        android:layout_width="100dp"
        android:layout_height="150dp"
        android:scaleType="centerCrop"
        android:contentDescription="@string/movie_poster"/>
    
    <!-- Информация о фильме -->
    <LinearLayout android:orientation="vertical">
        <!-- название, жанр, описание, рейтинг -->
    </LinearLayout>
</LinearLayout>
```

#### Обновлённый `MovieAdapter.java`
```java
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

class MovieViewHolder extends RecyclerView.ViewHolder {
    private final ImageView posterImageView;
    // ... остальные view
    
    public void bind(Movie movie) {
        // ... установка текстовых данных
        
        // Загрузка постера с Glide
        if (movie.getImageUrl() != null && !movie.getImageUrl().isEmpty()) {
            Glide.with(itemView.getContext())
                    .load(movie.getImageUrl())
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(posterImageView);
        } else {
            posterImageView.setImageResource(R.drawable.ic_launcher_background);
        }
    }
}
```

**Возможности Glide:**
- `placeholder()` - изображение во время загрузки
- `error()` - изображение при ошибке
- `centerCrop()` - масштабирование с обрезкой
- `diskCacheStrategy(ALL)` - кеширование оригинала и трансформированного
- Автоматическое управление жизненным циклом

---

### 9. Обновление AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<application
    android:usesCleartextTraffic="true"
    ...>
```

**Необходимые разрешения:**
- `INTERNET` - для сетевых запросов
- `ACCESS_NETWORK_STATE` - проверка доступности сети
- `usesCleartextTraffic` - для HTTP-запросов (актуально для изображений)

---

### 10. Применение в проекте Askon

#### Обновление зависимостей Askon
Аналогично MovieProject добавлены Retrofit и Glide в `libs.versions.toml` и `build.gradle.kts`.

#### Обновление ExpertAdapter с Glide
```java
package com.x2ketarol.askon.presentation.adapters;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ExpertAdapter extends RecyclerView.Adapter<ExpertAdapter.ExpertViewHolder> {
    
    static class ExpertViewHolder extends RecyclerView.ViewHolder {
        private final ImageView avatarImage;
        // ... остальные view
        
        public void bind(Expert expert, OnExpertClickListener listener) {
            // ... установка данных
            
            // Загрузка аватара с Glide
            if (expert.getPhotoUrl() != null && !expert.getPhotoUrl().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(expert.getPhotoUrl())
                        .circleCrop() // круглое изображение для аватара
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(avatarImage);
            } else {
                avatarImage.setImageResource(R.drawable.ic_launcher_background);
            }
        }
    }
}
```

**Отличия для аватаров:**
- `.circleCrop()` вместо `.centerCrop()` для круглых аватаров
- Аналогичное кеширование и обработка ошибок

---

## 🔍 Технические детали

### Retrofit - REST клиент для Android

**Основные компоненты:**
1. **Retrofit.Builder** - конфигурация клиента
   - `baseUrl()` - базовый URL API
   - `client()` - OkHttpClient с настройками
   - `addConverterFactory()` - JSON конвертер

2. **API Interface** - декларативное описание endpoints
   - `@GET`, `@POST`, `@PUT`, `@DELETE` - HTTP методы
   - `@Query` - query параметры
   - `@Path` - path параметры
   - `@Body` - тело запроса

3. **Call<T>** - асинхронный запрос
   - `.execute()` - синхронное выполнение
   - `.enqueue()` - асинхронное с callback

4. **OkHttp Interceptors**
   - Логирование запросов/ответов
   - Добавление заголовков
   - Таймауты и retry

### Glide - библиотека загрузки изображений

**Архитектура:**
```
Glide.with(context)           // Создание request manager
     .load(url)                // Источник изображения
     .apply(options)           // Опции трансформации
     .into(imageView)          // Целевой view
```

**Возможности:**
- **Многоуровневое кеширование:**
  - Memory cache (LRU)
  - Disk cache (оригинал + трансформации)
- **Автоматическая загрузка:**
  - Определение размера view
  - Downsampling для экономии памяти
- **Трансформации:**
  - `centerCrop()`, `fitCenter()`, `circleCrop()`
  - Кастомные трансформации
- **Lifecycle-aware:**
  - Автоматическая пауза/возобновление
  - Очистка ресурсов

### Clean Architecture интеграция

**Слои:**
```
Presentation (app)
    ├── MovieAdapter -> Glide
    └── ViewModel
          ↓
Domain (pure Java)
    ├── Movie (domain model)
    └── MovieRepository (interface)
          ↓
Data (data)
    ├── MovieRepositoryNetworkImpl
    ├── NetworkApi -> Retrofit
    ├── MovieMapper (DTO→Domain)
    └── DTOs (MovieDto, MoviesResponse)
```

**Принципы:**
1. **Зависимость вниз:** App → Data → Domain
2. **Изоляция:** Domain не зависит от Android/Retrofit
3. **Маппинг на границах:** DTO → Entity → Domain

---

## 📊 Результаты работы

### MovieProject
✅ Добавлены зависимости Retrofit, Glide, OkHttp  
✅ Создан API интерфейс для TMDB  
✅ Реализованы DTOs с аннотациями Gson  
✅ Настроен RetrofitClient с логированием  
✅ Создан MovieMapper для DTO→Domain преобразований  
✅ Обновлён FakeMovieApi с URL постеров  
✅ Интегрирован Glide в MovieAdapter  
✅ Обновлён layout с ImageView для постера  

### Askon
✅ Добавлены зависимости Glide  
✅ Обновлён ExpertAdapter с Glide для аватаров  
✅ Применён `.circleCrop()` для круглых изображений  

---

## 🎓 Выводы

1. **Retrofit** предоставляет удобный декларативный способ работы с REST API:
   - Type-safe интерфейсы
   - Автоматическая сериализация/десериализация
   - Гибкая настройка через interceptors

2. **Glide** обеспечивает эффективную загрузку изображений:
   - Автоматическое управление памятью и кешированием
   - Lifecycle-aware управление ресурсами
   - Богатый набор трансформаций

3. **Clean Architecture** позволяет интегрировать библиотеки без нарушения принципов:
   - Retrofit и Glide изолированы в data/presentation слоях
   - Domain layer остаётся независимым от фреймворков
   - Маппинг обеспечивает изоляцию между слоями

4. **Паттерн Fallback** повышает надёжность приложения:
   - При ошибке сети используется FakeMovieApi
   - Пользователь видит контент даже без интернета

---

## 📚 Использованные технологии

| Библиотека | Версия | Назначение |
|-----------|--------|-----------|
| Retrofit | 2.9.0 | REST API клиент |
| Glide | 4.16.0 | Загрузка изображений |
| OkHttp | 4.12.0 | HTTP клиент |
| Gson | 2.10.1 | JSON сериализация |
| Logging Interceptor | 4.12.0 | Логирование HTTP |

---

## 🔗 Полезные ссылки

- [Retrofit Documentation](https://square.github.io/retrofit/)
- [Glide Documentation](https://bumptech.github.io/glide/)
- [TMDB API Documentation](https://developers.themoviedb.org/3)
- [OkHttp Interceptors](https://square.github.io/okhttp/interceptors/)
- [Clean Architecture by Uncle Bob](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

---

**Дата выполнения:** 2024  
**Выполнил:** Студент группы [ГРУППА]  
**Проверил:** [ПРЕПОДАВАТЕЛЬ]
