package com.example.weather.ui.fragment.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather.data.model.location.Country;
import com.example.weather.data.model.location.LocationStorage;

import java.util.List;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<List<Country>> listMutableLiveData ;

    public SearchViewModel(){
        listMutableLiveData = new MutableLiveData<>();
    }
    public void  init(String name){
        LocationStorage.getLocationGyId(name, new LocationStorage.Result() {
            @Override
            public void onSuccess(List<Country> list) {
                listMutableLiveData.setValue(list);
            }

            @Override
            public void onFailure(Throwable throwable) {
                listMutableLiveData.setValue(null);
            }
        });
    }

    public MutableLiveData<List<Country>> getListMutableLiveData() {
        return listMutableLiveData;
    }
}
