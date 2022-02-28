package com.example.weather.ui.fragment.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;
import com.example.weather.data.model.temp.Week;
import com.example.weather.databinding.ItemCardBinding;

import java.util.List;


public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {
    private List<Week> list;


    public void getCountryForecast(List<Week> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCardBinding binding = ItemCardBinding.inflate(LayoutInflater.from(parent.getContext())
                , parent, false);
        return new DayViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        holder.onBind(list, position);
    }

    @Override
    public int getItemCount() {
        return list.size() / DayViewHolder.EIGHT;
    }

    public static class DayViewHolder extends RecyclerView.ViewHolder {
        ItemCardBinding binding;
        final String C = "°C";
        final int ONE = 1;
        public static final int EIGHT = 8;
        final int THIRTY_NINE = 39;
        final Double TWO_HUNDRED_SEVENTY_THREE = 273.15;
        final int ZERO = 0;

        public DayViewHolder(@NonNull ItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(List<Week> list, int position) {
            int pos = (ONE + position) * EIGHT;
            if (pos >= list.size()) {
                pos = THIRTY_NINE;
            }
            String[] date = list.get(pos).getDtTxt().split(" ");
            String tempMax = (int) (list.get(pos).getMain().getTempMax() - TWO_HUNDRED_SEVENTY_THREE) + C;
            String tempMin = (int) (list.get(pos).getMain().getTempMin() - TWO_HUNDRED_SEVENTY_THREE) + C;
            binding.itemTxtDay.setText(date[ZERO]);
            binding.itemTxtDegreeDown.setText(tempMin);
            binding.itemTxtDegreeUp.setText(tempMax);
            switch (list.get(position).getWeather().get(ZERO).getMain()) {
                case "Rain":
                case "Clouds":
                    binding.itemImageWeather.setImageResource(R.drawable.ic_cloudy);
                    break;
                case "Clear":
                    binding.itemImageWeather.setImageResource(R.drawable.ic_sun);
                    break;
                case "Smoke":
                    binding.itemImageWeather.setImageResource(R.drawable.ic_wind);
                    break;
            }
        }
    }
}
