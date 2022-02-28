package com.example.weather.ui.fragment.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather.data.model.temp.CountryForecast;
import com.example.weather.data.model.temp.TempStorage;


public class MainViewModel extends ViewModel {
    private MutableLiveData<CountryForecast> countryForecastMutableLiveData;

    public MainViewModel() {
        countryForecastMutableLiveData = new MutableLiveData<>();
    }



    public void forecast(Double lat, Double lon) {
        TempStorage.getTempGyId(lat, lon, new TempStorage.Result() {
            @Override
            public void onSuccess(CountryForecast countryForecast) {
                countryForecastMutableLiveData.setValue(countryForecast);
            }

            @Override
            public void onFailure(Throwable throwable) {
                countryForecastMutableLiveData.setValue(null);
            }
        });
    }


    public MutableLiveData<CountryForecast> getCountryForecastMutableLiveData() {
        return countryForecastMutableLiveData;
    }
}
