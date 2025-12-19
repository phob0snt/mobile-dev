package com.x2ketarol.askon.data.remote;

import android.util.Log;

import com.x2ketarol.askon.data.remote.api.FloatRatesApi;
import com.x2ketarol.askon.data.remote.dto.CurrencyRatesResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * Retrofit клиент для API FloatRates
 * Получает курс валют USD -> RUB
 */
public class CurrencyApiClient {
    
    private static final String TAG = "CurrencyApiClient";
    private static final String BASE_URL = "http://www.floatrates.com/";
    
    private static CurrencyApiClient instance;
    private final FloatRatesApi api;
    
    // Кэшированное значение курса (чтобы не делать запрос каждый раз)
    private Double cachedUsdToRubRate = null;
    private long lastUpdateTime = 0;
    private static final long CACHE_DURATION = 1000 * 60 * 30; // 30 минут
    
    private CurrencyApiClient() {
        // Логирование для отладки
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        
        api = retrofit.create(FloatRatesApi.class);
    }
    
    public static synchronized CurrencyApiClient getInstance() {
        if (instance == null) {
            instance = new CurrencyApiClient();
        }
        return instance;
    }
    
    /**
     * Получить курс USD -> RUB
     * @param callback Колбэк с результатом
     */
    public void getUsdToRubRate(final CurrencyCallback callback) {
        // Проверяем кэш
        if (cachedUsdToRubRate != null && 
            (System.currentTimeMillis() - lastUpdateTime) < CACHE_DURATION) {
            Log.d(TAG, "Используем кэшированный курс: " + cachedUsdToRubRate);
            callback.onSuccess(cachedUsdToRubRate);
            return;
        }
        
        // Делаем запрос к API
        api.getUsdRates().enqueue(new Callback<CurrencyRatesResponse>() {
            @Override
            public void onResponse(Call<CurrencyRatesResponse> call, 
                                 Response<CurrencyRatesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CurrencyRatesResponse rates = response.body();
                    
                    // Ищем курс RUB
                    if (rates.containsKey("rub")) {
                        CurrencyRatesResponse.CurrencyRate rubRate = rates.get("rub");
                        double rate = rubRate.rate;
                        
                        // Кэшируем
                        cachedUsdToRubRate = rate;
                        lastUpdateTime = System.currentTimeMillis();
                        
                        Log.d(TAG, "Получен курс USD->RUB: " + rate);
                        callback.onSuccess(rate);
                    } else {
                        Log.e(TAG, "RUB не найден в ответе API");
                        callback.onError("RUB rate not found");
                    }
                } else {
                    Log.e(TAG, "Ошибка ответа API: " + response.code());
                    callback.onError("API error: " + response.code());
                }
            }
            
            @Override
            public void onFailure(Call<CurrencyRatesResponse> call, Throwable t) {
                Log.e(TAG, "Ошибка сети: " + t.getMessage());
                
                // Если есть кэш - используем его даже устаревший
                if (cachedUsdToRubRate != null) {
                    Log.d(TAG, "Используем устаревший кэш из-за ошибки сети");
                    callback.onSuccess(cachedUsdToRubRate);
                } else {
                    // Фоллбэк значение (примерный курс)
                    callback.onSuccess(95.0);
                }
            }
        });
    }
    
    /**
     * Конвертировать USD в RUB
     * @param usd Сумма в долларах
     * @param callback Колбэк с суммой в рублях
     */
    public void convertUsdToRub(final double usd, final ConversionCallback callback) {
        getUsdToRubRate(new CurrencyCallback() {
            @Override
            public void onSuccess(double rate) {
                double rub = usd * rate;
                callback.onSuccess(usd, rub, rate);
            }
            
            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }
    
    /**
     * Callback для получения курса
     */
    public interface CurrencyCallback {
        void onSuccess(double rate);
        void onError(String error);
    }
    
    /**
     * Callback для конвертации
     */
    public interface ConversionCallback {
        void onSuccess(double usd, double rub, double rate);
        void onError(String error);
    }
}
