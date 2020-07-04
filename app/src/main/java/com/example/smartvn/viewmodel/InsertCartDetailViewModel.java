package com.example.smartvn.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartvn.repository.ProductRepository;

public class InsertCartDetailViewModel extends ViewModel {
    private MutableLiveData<String> result;
    private ProductRepository newsRepository;

    public void init(String json){
        if (result != null){
            return;
        }
        newsRepository = newsRepository.getInstance();
        result = newsRepository.insertCartDetail(json);

    }

    public LiveData<String> getResult() {
        return result;
    }
}
