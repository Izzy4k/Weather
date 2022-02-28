package com.example.weather.data.model.temp;

import com.example.weather.data.domain.weather.RetrofitWeather;
import com.example.weather.utils.EndPoints;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TempStorage {
    public static void getTempGyId(Double lat, Double lon, Result result) {
        RetrofitWeather.getApiWeather().getForecast(lat, lon, EndPoints.API_KEY).enqueue(
                new Callback<CountryForecast>() {
                    @Override
                    public void onResponse(Call<CountryForecast> call, Response<CountryForecast>
                            response) {
                        if (response.isSuccessful() && response.body() != null) {
                            result.onSuccess(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<CountryForecast> call, Throwable t) {
                        result.onFailure(t);
                    }
                });
    }

    public interface Result {
        void onSuccess(CountryForecast countryForecast);

        void onFailure(Throwable throwable);
    }
}
