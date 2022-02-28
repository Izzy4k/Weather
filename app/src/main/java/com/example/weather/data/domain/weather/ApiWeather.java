package com.example.weather.data.domain.weather;

import com.example.weather.data.model.temp.CountryForecast;
import com.example.weather.utils.EndPoints;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiWeather {
    @GET(EndPoints.FORECAST)
    Call<CountryForecast> getForecast(@Query(EndPoints.LAT) Double lat, @Query(EndPoints.LON)
            Double lon, @Query(EndPoints.APP_ID) String id);
}
