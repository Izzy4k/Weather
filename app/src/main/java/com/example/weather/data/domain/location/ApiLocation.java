package com.example.weather.data.domain.location;


import com.example.weather.data.model.location.Country;
import com.example.weather.utils.EndPoints;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiLocation {
    @GET(EndPoints.DIRECT)
    Call<List<Country>> getLocation(@Query(EndPoints.CITY_NAME) String city, @Query(EndPoints.APP_ID) String id);
}
