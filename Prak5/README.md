# Практическая работа №5 (Практика 13)
## Retrofit и Glide - Работа с сетью и изображениями

---

## 📝 Краткое описание

Интеграция библиотек **Retrofit** (для REST API запросов) и **Glide** (для загрузки изображений) в Android-приложения MovieProject и Askon с архитектурой Clean Architecture.

---

## ✅ Выполненные задачи

### MovieProject
- ✅ Добавлены зависимости: Retrofit 2.9.0, Glide 4.16.0, OkHttp 4.12.0
- ✅ Создан API интерфейс `MovieApiService` для TMDB
- ✅ Реализованы DTO: `MovieDto`, `MoviesResponse`
- ✅ Настроен `RetrofitClient` с logging interceptor
- ✅ Создан `MovieMapper` для преобразования DTO → Domain
- ✅ Обновлён `NetworkApi` с Retrofit запросами
- ✅ Обновлён `FakeMovieApi` с URL постеров (12 фильмов)
- ✅ Интегрирован Glide в `MovieAdapter` для загрузки постеров
- ✅ Обновлён layout `item_movie.xml` с ImageView
- ✅ Добавлены разрешения в AndroidManifest

### Askon
- ✅ Добавлены зависимости Glide 4.16.0
- ✅ Обновлён `ExpertAdapter` с Glide для аватаров экспертов
- ✅ Применена трансформация `.circleCrop()` для круглых изображений

---

## 🏗️ Архитектура решения

```
Presentation Layer (app)
    ├── MovieAdapter + Glide
    └── MovieListViewModel
          ↓
Domain Layer (domain)
    ├── Movie (entity)
    └── MovieRepository (interface)
          ↓
Data Layer (data)
    ├── MovieRepositoryNetworkImpl
    ├── NetworkApi + Retrofit
    ├── MovieMapper (DTO→Domain)
    └── DTOs (MovieDto, MoviesResponse)
```

---

## 🔑 Ключевые компоненты

### 1. Retrofit Configuration
```java
// RetrofitClient.java
public class RetrofitClient {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    
    // Singleton с OkHttp interceptor
    public static Retrofit getRetrofitInstance() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
                
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
```

### 2. API Interface
```java
// MovieApiService.java
public interface MovieApiService {
    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("page") int page
    );
}
```

### 3. Glide Integration
```java
// MovieAdapter.java
Glide.with(context)
     .load(movie.getImageUrl())
     .centerCrop()
     .placeholder(R.drawable.placeholder)
     .error(R.drawable.error)
     .diskCacheStrategy(DiskCacheStrategy.ALL)
     .into(imageView);
```

---

## 📊 Используемые API

### TMDB (The Movie Database)
- **Base URL:** `https://api.themoviedb.org/3/`
- **Endpoints:**
  - `GET /movie/popular` - популярные фильмы
  - `GET /movie/top_rated` - топ-рейтинговые фильмы
- **Image Base:** `https://image.tmdb.org/t/p/w500/`

---

## 🔧 Технологии

| Библиотека | Версия | Назначение |
|-----------|--------|-----------|
| Retrofit | 2.9.0 | REST API клиент |
| Glide | 4.16.0 | Загрузка изображений |
| OkHttp | 4.12.0 | HTTP клиент с interceptors |
| Gson | - | JSON конвертер |

---

## 📁 Структура файлов

```
Prak5/
├── MovieProject/
│   ├── app/
│   │   ├── presentation/adapter/
│   │   │   └── MovieAdapter.java (+ Glide)
│   │   └── res/layout/
│   │       └── item_movie.xml (+ ImageView)
│   ├── data/
│   │   ├── network/
│   │   │   ├── api/
│   │   │   │   └── MovieApiService.java
│   │   │   ├── dto/
│   │   │   │   ├── MovieDto.java
│   │   │   │   └── MoviesResponse.java
│   │   │   ├── FakeMovieApi.java (+ imageUrl)
│   │   │   ├── NetworkApi.java (+ Retrofit)
│   │   │   └── RetrofitClient.java
│   │   └── mapper/
│   │       └── MovieMapper.java
│   └── domain/
│       └── models/
│           └── Movie.java (+ imageUrl field)
└── REPORT.md (Подробный отчёт)
```

---

## 🚀 Как запустить

1. **Получить TMDB API ключ:**
   - Зарегистрироваться на [themoviedb.org](https://www.themoviedb.org/)
   - Получить API key в настройках аккаунта
   - Заменить `demo_key_use_your_own` в `RetrofitClient.java`

2. **Синхронизировать Gradle:**
   ```bash
   cd Prak5/MovieProject
   ./gradlew build
   ```

3. **Запустить приложение:**
   - Открыть MovieProject в Android Studio
   - Запустить на эмуляторе или устройстве
   - Перейти в MovieListActivity для просмотра списка фильмов

---

## 🎯 Результаты

### До внедрения
- Статичные данные из `FakeMovieApi`
- Отсутствие изображений
- Нет сетевого взаимодействия

### После внедрения
- ✅ Загрузка данных из TMDB API
- ✅ Отображение постеров фильмов
- ✅ Кеширование изображений
- ✅ Fallback на локальные данные при ошибке сети
- ✅ Детальное логирование HTTP запросов

---

## 📚 Полезные ссылки

- 📖 [Полный отчёт (REPORT.md)](./REPORT.md)
- 🔗 [Retrofit Documentation](https://square.github.io/retrofit/)
- 🔗 [Glide Documentation](https://bumptech.github.io/glide/)
- 🔗 [TMDB API Docs](https://developers.themoviedb.org/3)

---

**Статус:** ✅ Завершено  
**Дата:** 2024  
**Проекты:** MovieProject ✅ | Askon ✅
