package com.example.smartvn.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartvn.model.Product;
import com.example.smartvn.repository.ProductRepository;

import java.util.List;

public class ProductByCategory extends ViewModel {
    private MutableLiveData<List<Product>> mutableLiveData;
    private ProductRepository newsRepository;

    public void init(int page,String categoryID){
//        if (mutableLiveData != null){
//            return;
//        }
        newsRepository = newsRepository.getInstance();
            mutableLiveData = newsRepository.getProductByCategory(page,categoryID);

    }

    public LiveData<List<Product>> getProductListRepository() {
        return mutableLiveData;
    }
}
