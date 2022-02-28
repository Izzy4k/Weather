package com.example.weather.ui.fragment.main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.weather.R;
import com.example.weather.data.model.location.Country;
import com.example.weather.data.model.temp.CountryForecast;
import com.example.weather.databinding.FragmentMain2Binding;
import com.example.weather.ui.fragment.main.adapter.DayAdapter;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MainFragment extends Fragment {
    private FragmentMain2Binding binding;
    private LocationManager locationManager;
    public static Double lon;
    public static Double lat;
    private MainViewModel mainViewModel;
    private final String C = "°C";
    private final String PERCENT = "%";
    private final String M_BAR = "mBar";
    private final String M_IN_SEC = "m/c";
    private final String COMMA = ",";
    private LocationListener locationListener;
    private DayAdapter dayAdapter;
    private final Integer ZERO = 0;
    private final Double TWO_HUNDRED_SEVENTY_THREE = 273.15;
    private final Integer THOUSAND = 1000;
    private final String TIME_PATTERN = "HH:mm";
    private final Integer ONE = 1;
    private LocalDateTime is;


    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dayAdapter = new DayAdapter();
        initLocation();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMain2Binding.inflate(inflater);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLocation();
        initListener();
        initInterListener();
        initBtn();
        initArguments();
    }

    private void initArguments() {
        final String MODEL = "model";
        if (getArguments() != null) {
            Country country = (Country) getArguments().getSerializable(MODEL);
            lat = country.getLat();
            lon = country.getLon();
            mainViewModel.forecast(lat, lon);
        }
    }

    private void initBtn() {
        binding.btnTown.setOnClickListener(view -> {
            navigate(R.id.action_mainFragment_to_searchFragment);
            MediaPlayer player = MediaPlayer.create(requireContext(), R.raw.img);
            player.start();
        });
    }

    private void initInterListener() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                showLocation(location);
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                LocationListener.super.onProviderEnabled(provider);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (requireActivity().checkSelfPermission(Manifest.permission.
                            ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        requireActivity().requestPermissions(
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                ONE);
                    } else {
                        showLocation(locationManager.getLastKnownLocation(provider));
                    }
                }
            }
        };
    }

    private void initListener() {
        if (mainViewModel.getCountryForecastMutableLiveData() != null) {
            mainViewModel.getCountryForecastMutableLiveData().observe(getViewLifecycleOwner(),
                    countryForecast -> {
                        saveTime(countryForecast);
                        String countryName = countryForecast.getCity().getName() + COMMA + countryForecast.getCity().getCountry();
                        String temp = String.valueOf((int) (countryForecast.getList().get(ZERO).getMain().getTemp() - TWO_HUNDRED_SEVENTY_THREE));
                        String tempMax = (int) (countryForecast.getList().get(ZERO).getMain().getTempMax() - TWO_HUNDRED_SEVENTY_THREE) + C;
                        String tempMin = (int) (countryForecast.getList().get(ZERO).getMain().getTempMin() - TWO_HUNDRED_SEVENTY_THREE) + C;
                        String humidity = countryForecast.getList().get(ZERO).getMain().getHumidity() + PERCENT;
                        String pressure = countryForecast.getList().get(ZERO).getMain().getPressure() + M_BAR;
                        String speed = countryForecast.getList().get(ZERO).getWind().getSpeed() + M_IN_SEC;
                        SimpleDateFormat format = new SimpleDateFormat(TIME_PATTERN);
                        String sunRise = format.format(new Date((long) countryForecast.getCity().getSunrise() * THOUSAND));
                        String sunSet = format.format(new Date((long) countryForecast.getCity().getSunset() * THOUSAND));
                        String weather = countryForecast.getList().get(ZERO).getWeather().get(ZERO).getMain();
                        String feelLikes = (int) (countryForecast.getList().get(ZERO).getMain().getFeelsLike() - TWO_HUNDRED_SEVENTY_THREE) + C;
                        binding.txtDegree.setText(temp);
                        binding.btnTown.setText(countryName);
                        binding.txtWeather.setText(weather);
                        binding.txtDegree.setText(temp);
                        binding.txtTempMax.setText(tempMax);
                        binding.txtTempMin.setText(tempMin);
                        binding.txtHumidityInPercent.setText(humidity);
                        binding.txtPressureMBar.setText(pressure);
                        binding.txtWindInKm.setText(speed);
                        binding.txtSunriseTime.setText(sunRise);
                        binding.txtSunsetTime.setText(sunSet);
                        binding.rvMain.setAdapter(dayAdapter);
                        dayAdapter.getCountryForecast(countryForecast.getList());
                        binding.txtFeelLike.setText(feelLikes);
                        switch (countryForecast.getList().get(ZERO).getWeather().get(ZERO).getMain()) {
                            case "Rain":
                            case "Clouds":
                                binding.imageWeather.setImageResource(R.drawable.ic_cloudy);
                                break;
                            case "Clear":
                                binding.imageWeather.setImageResource(R.drawable.ic_sun);
                                break;
                            case "Smoke":
                                binding.imageWeather.setImageResource(R.drawable.ic_wind);
                                break;
                        }
                    });
        }
    }


    //Метод который вызывается каждую секунду!
    private void saveTime(CountryForecast countryForecast) {
        new Handler().postDelayed(() -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                if (countryForecast.getCity().getTimezone() < 0) {
                    is = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneOffset.of("" + countryForecast.getCity().getTimezone() / 3600));
                } else if (countryForecast.getCity().getTimezone() > 0) {
                    is = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneOffset.of("+" + countryForecast.getCity().getTimezone() / 3600));
                } else {
                    is = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneOffset.of("-" + countryForecast.getCity().getTimezone() / 3600));
                }
                initTime(countryForecast);
                saveTime(countryForecast);
            }
        }, 1000);
    }


    private void showLocation(Location location) {
        if (location == null) return;
        if (getArguments() == null) {
            lat = location.getLatitude();
            lon = location.getLongitude();
            mainViewModel.forecast(lat, lon);
        }
    }

    //Охуеть бомба!Запомни для работ с датой
    private void initTime(CountryForecast countryForecast) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String DATE_FORMAT = "yyyy.MM.dd | HH:mm:ss";
            String HOUR_FORMAT = "HH";
            String MINUTE_FORMAT = "mm";
            SimpleDateFormat hourFormat = new SimpleDateFormat(HOUR_FORMAT);
            SimpleDateFormat minuteFormat = new SimpleDateFormat(MINUTE_FORMAT);
            int hourSunset = Integer.parseInt(hourFormat.format(new Date((long) countryForecast.getCity().getSunset() * THOUSAND)));
            int minuteSunset = Integer.parseInt(minuteFormat.format(new Date((long) countryForecast.getCity().getSunset() * THOUSAND)));
            int hourSunrise = Integer.parseInt(hourFormat.format(new Date((long) countryForecast.getCity().getSunrise() * THOUSAND)));
            int minuteSunrise = Integer.parseInt(minuteFormat.format(new Date((long) countryForecast.getCity().getSunrise() * THOUSAND)));
            binding.txtTime.setText(is.format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
            if (is.toLocalTime().isAfter(LocalTime.of(hourSunset, minuteSunset))) {
                binding.imageDayOrNight.setImageResource(R.drawable.ic_night);
            } else if (is.toLocalTime().isAfter(LocalTime.of(hourSunrise, minuteSunrise))) {
                binding.imageDayOrNight.setImageResource(R.drawable.ic_day);
            }
        }
    }


    private void initLocation() {
        locationManager = (LocationManager)
                requireActivity().getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();
        GetLocation();
    }

    private void GetLocation() {
        int TEN = 10;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requireActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        (long) THOUSAND * TEN, TEN, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        (long) THOUSAND * TEN, TEN, locationListener);
            } else {
                requireActivity().requestPermissions(new
                        String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, ONE);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ONE) {
            GetLocation();
        }
    }

    private void navigate(int id) {
        NavController controller = Navigation.
                findNavController(requireActivity(), R.id.fragment_container);
        controller.navigate(id);
    }
}