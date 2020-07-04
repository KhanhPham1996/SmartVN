package com.example.smartvn.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartvn.model.Product;
import com.example.smartvn.repository.ProductRepository;

import java.util.List;

public class InsertCartViewModel extends ViewModel {
    private  MutableLiveData<String>  result;
    private ProductRepository newsRepository;

    public void init(String fullname,String email,String phone){
        if (result != null){
            return;
        }
        newsRepository = newsRepository.getInstance();
        result = newsRepository.insertCart(fullname,email,phone);

    }

    public LiveData<String> getResult() {
        return result;
    }
}
