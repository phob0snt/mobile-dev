package ru.mirea.yudaev.retrofitapp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Клиент для работы с TMDB API через Retrofit
 * При ошибке подключения использует локальные данные
 */
public class MovieApiClient {
    
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "your_api_key_here"; // Замените на реальный ключ
    
    private final MovieApiService apiService;
    private final boolean useFallback; // Флаг для использования локальных данных

    public MovieApiClient() {
        this(false); // По умолчанию пытаемся использовать реальный API
    }

    public MovieApiClient(boolean useFallback) {
        this.useFallback = useFallback;
        
        // Логирование HTTP запросов
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(5, TimeUnit.SECONDS) // Уменьшаем timeout
                .readTimeout(5, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(MovieApiService.class);
    }

    public void getPopularMovies(MovieCallback callback) {
        // Если флаг установлен, сразу используем локальные данные
        if (useFallback) {
            callback.onSuccess(getFallbackMovies());
            return;
        }

        // Пытаемся загрузить из API
        apiService.getPopularMovies(API_KEY, "ru-RU", 1)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            callback.onSuccess(response.body().getResults());
                        } else {
                            // При ошибке используем локальные данные
                            callback.onSuccess(getFallbackMovies());
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        // При ошибке сети используем локальные данные
                        callback.onSuccess(getFallbackMovies());
                    }
                });
    }

    /**
     * Локальные данные для демонстрации (fallback)
     */
    private List<MovieDto> getFallbackMovies() {
        List<MovieDto> movies = new ArrayList<>();
        
        movies.add(createMovie(1, "Побег из Шоушенка", 
                "Бухгалтер Энди Дюфрейн обвинён в убийстве собственной жены и её любовника. " +
                "Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, " +
                "царящими по обе стороны решётки.", 
                "/path1.jpg", "1994-09-23", 9.3));
        
        movies.add(createMovie(2, "Крёстный отец",
                "Криминальная сага, повествующая о нью-йоркской сицилийской мафиозной семье Корлеоне. " +
                "Фильм охватывает период 1945-1955 годов.",
                "/path2.jpg", "1972-03-14", 9.2));
        
        movies.add(createMovie(3, "Тёмный рыцарь",
                "Бэтмен поднимает ставки в войне с криминалом. С помощью лейтенанта Джима Гордона и " +
                "прокурора Харви Дента он намерен очистить улицы от преступности.",
                "/path3.jpg", "2008-07-18", 9.0));
        
        movies.add(createMovie(4, "Начало",
                "Кобб — талантливый вор, лучший из лучших в опасном искусстве извлечения: " +
                "он крадёт ценные секреты из глубин подсознания во время сна.",
                "/path4.jpg", "2010-07-16", 8.8));
        
        movies.add(createMovie(5, "Бойцовский клуб",
                "Терзаемый хронической бессонницей и отчаянно пытающийся вырваться из мучительно скучной " +
                "жизни, клерк встречает некоего Тайлера Дёрдена.",
                "/path5.jpg", "1999-10-15", 8.8));
        
        movies.add(createMovie(6, "Форрест Гамп",
                "От лица главного героя Форреста Гампа, слабоумного безобидного человека с благородным " +
                "и открытым сердцем, рассказывается история его необыкновенной жизни.",
                "/path6.jpg", "1994-07-06", 8.8));
        
        movies.add(createMovie(7, "Матрица",
                "Жизнь Томаса Андерсона разделена на две части: днём он — самый обычный офисный работник, " +
                "получающий нагоняи от начальства, а ночью превращается в хакера по имени Нео.",
                "/path7.jpg", "1999-03-31", 8.7));
        
        movies.add(createMovie(8, "Интерстеллар",
                "Когда засуха приводит человечество к продовольственному кризису, коллектив " +
                "исследователей и учёных отправляется в космос, чтобы найти новую планету.",
                "/path8.jpg", "2014-11-07", 8.6));
        
        return movies;
    }

    private MovieDto createMovie(int id, String title, String overview, 
                                  String posterPath, String releaseDate, double rating) {
        MovieDto movie = new MovieDto();
        // Используем рефлексию или создаём конструктор/сеттеры
        // Для простоты возвращаем объект напрямую
        return new MovieDto(id, title, overview, posterPath, releaseDate, rating);
    }

    public interface MovieCallback {
        void onSuccess(List<MovieDto> movies);
        void onError(String error);
    }
}
