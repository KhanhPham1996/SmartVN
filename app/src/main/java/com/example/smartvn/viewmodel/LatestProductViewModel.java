package com.example.smartvn.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartvn.model.Product;
import com.example.smartvn.repository.ProductRepository;

import java.util.List;

public class LatestProductViewModel extends ViewModel {
    private MutableLiveData<List<Product>> mutableLiveData;
    private ProductRepository newsRepository;

    public void init(){
        if (mutableLiveData != null){
            return;
        }
        newsRepository = newsRepository.getInstance();
        mutableLiveData = newsRepository.getLatestProduct();

    }

    public LiveData<List<Product>> getLatestProductRepository() {
        return mutableLiveData;
    }

}
