package com.x2ketarol.askon.data.remote.dto;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Response DTO для API floatrates
 * Пример структуры:
 * {
 *   "rub": {
 *     "code": "RUB",
 *     "alphaCode": "RUB",
 *     "numericCode": "643",
 *     "name": "Russian Ruble",
 *     "rate": 95.123456,
 *     "date": "2025-12-20",
 *     "inverseRate": 0.0105123
 *   },
 *   "eur": { ... }
 * }
 */
public class CurrencyRatesResponse extends java.util.HashMap<String, CurrencyRate> {
    
    public static class CurrencyRate {
        @SerializedName("code")
        public String code;
        
        @SerializedName("alphaCode")
        public String alphaCode;
        
        @SerializedName("numericCode")
        public String numericCode;
        
        @SerializedName("name")
        public String name;
        
        @SerializedName("rate")
        public double rate;
        
        @SerializedName("date")
        public String date;
        
        @SerializedName("inverseRate")
        public double inverseRate;
    }
}
