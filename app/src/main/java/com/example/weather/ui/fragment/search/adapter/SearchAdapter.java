package com.example.weather.ui.fragment.search.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.data.model.location.Country;
import com.example.weather.databinding.ItemCityBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.BottomViewHolder> {
    private List<Country> list = new ArrayList<>();
    private Transfer transfer;

    public SearchAdapter(Transfer transfer) {
        this.transfer = transfer;
    }

    public void setList(List<Country> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BottomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCityBinding binding = ItemCityBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new BottomViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BottomViewHolder holder, int position) {
        holder.onBind(list.get(position).getName());
        holder.itemView.setOnClickListener(view -> {
            transfer.onClick(list.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BottomViewHolder extends RecyclerView.ViewHolder {
        ItemCityBinding binding;

        public BottomViewHolder(@NonNull ItemCityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(String name) {
            binding.txtCity.setText(name);
        }
    }

    public interface Transfer {
        void onClick(Country country);
    }
}
