package ru.mirea.yudaev.data.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mirea.yudaev.data.network.api.MovieApiService;

import java.util.concurrent.TimeUnit;

/**
 * Класс для создания и настройки Retrofit клиента
 */
public class RetrofitClient {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    // Для целей обучения - используем демо ключ
    // В реальном приложении хранить в BuildConfig или файле конфигурации
    public static final String API_KEY = "demo_key_use_your_own";
    
    private static Retrofit retrofit = null;
    private static MovieApiService movieApiService = null;

    /**
     * Получить настроенный экземпляр Retrofit
     */
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            // Logging interceptor для отладки
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // OkHttpClient с настройками таймаутов
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

    /**
     * Получить экземпляр MovieApiService
     */
    public static MovieApiService getMovieApiService() {
        if (movieApiService == null) {
            movieApiService = getRetrofitInstance().create(MovieApiService.class);
        }
        return movieApiService;
    }
}
