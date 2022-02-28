package com.example.weather.ui.fragment.search;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.weather.R;
import com.example.weather.data.model.location.Country;
import com.example.weather.databinding.FragmentSearchBinding;
import com.example.weather.ui.fragment.search.adapter.SearchAdapter;


public class SearchFragment extends Fragment implements SearchAdapter.Transfer {
    FragmentSearchBinding binding;
    private SearchViewModel searchViewModel;
    private SearchAdapter adapter;
    private final String EMPTY = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new SearchAdapter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
        initBtn();
        initEditListener();
    }

    private void initEditListener() {
        binding.bottomEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String tempCityName = binding.bottomEdit.getText().toString();
                searchViewModel.init(tempCityName);
                initListener();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initBtn() {
        binding.imageSearch.setOnClickListener(view -> {
            String cityName = binding.bottomEdit.getText().toString();
            if (!cityName.equals(EMPTY)) {
                searchViewModel.init(cityName);
                clear();
            }
        });
    }

    private void clear() {
        binding.bottomEdit.setText(EMPTY);
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
    }

    private void initListener() {
        binding.bottomRv.setAdapter(adapter);
        if (searchViewModel.getListMutableLiveData() != null) {
            searchViewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), list ->
                    adapter.setList(list));
        } else {
            Toast.makeText(requireContext(), "Ничего не найдено!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(Country country) {
        final String MODEL = "model";
        NavController controller = Navigation.findNavController(requireActivity(), R.id.fragment_container);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MODEL, country);
        controller.navigate(R.id.action_searchFragment_to_mainFragment2, bundle);
    }


}