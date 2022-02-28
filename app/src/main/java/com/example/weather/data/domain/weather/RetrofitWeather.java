package com.example.weather.data.domain.weather;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitWeather {
    private RetrofitWeather() {
    }

    private static ApiWeather apiWeather;

    public static ApiWeather getApiWeather() {
        if (apiWeather == null) {
            apiWeather = createWeather();
        }
        return apiWeather;
    }

    private static ApiWeather createWeather() {
        return new Retrofit.Builder().
                baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiWeather.class);
    }
}
