package com.x2ketarol.askon.data.remote.api;

import com.x2ketarol.askon.data.remote.dto.CurrencyRatesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Retrofit API для получения курса валют
 * API: http://www.floatrates.com/daily/usd.json
 */
public interface FloatRatesApi {
    
    /**
     * Получить курсы валют относительно USD
     * @return Курсы валют (включая RUB)
     */
    @GET("daily/usd.json")
    Call<CurrencyRatesResponse> getUsdRates();
}
