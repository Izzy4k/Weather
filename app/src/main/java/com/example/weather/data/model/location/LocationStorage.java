package com.example.weather.data.model.location;

import com.example.weather.data.domain.location.RetrofitLocation;
import com.example.weather.utils.EndPoints;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationStorage {
    public static void getLocationGyId(String city, Result result) {
        RetrofitLocation.getApiLocation().getLocation(city, EndPoints.API_KEY)
                .enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    result.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                result.onFailure(t);
            }
        });

    }

    public interface Result {
        void onSuccess(List<Country> list);

        void onFailure(Throwable throwable);
    }
}
