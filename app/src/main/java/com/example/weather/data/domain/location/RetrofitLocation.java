package com.example.weather.data.domain.location;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitLocation {
    private RetrofitLocation() {
    }

    private static ApiLocation apiLocation;

    public static ApiLocation getApiLocation() {
        if (apiLocation == null) {
            apiLocation = createApiLocation();
        }
        return apiLocation;
    }

    private static ApiLocation createApiLocation() {
        return new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/geo/1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiLocation.class);
    }
}
