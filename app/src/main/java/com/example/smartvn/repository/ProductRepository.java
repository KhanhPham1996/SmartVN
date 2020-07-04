package com.example.smartvn.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.smartvn.model.Product;
import com.example.smartvn.ultil.ApiUltil;
import com.example.smartvn.ultil.RetrofitInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private static ProductRepository productRepository;


    public static ProductRepository getInstance() {
        if (productRepository == null) {
            productRepository = new ProductRepository();
        }
        return productRepository;
    }

    private RetrofitInterface retrofitInterface;

    public ProductRepository() {
        retrofitInterface = ApiUltil.getRetrofitInterface();
    }

    public MutableLiveData<List<Product>> getLatestProduct() {
        final MutableLiveData<List<Product>> newsData = new MutableLiveData<>();
        retrofitInterface.getLastesProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    newsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
        return newsData;
    }

    public MutableLiveData<List<Product>> getProductByCategory(int page, String productID) {
        final MutableLiveData<List<Product>> newsData = new MutableLiveData<>();
        retrofitInterface.getListProducts(page, productID).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {

                    newsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
        return newsData;
    }

    public MutableLiveData<String> insertCart(String name, String email, String phone) {
        final MutableLiveData<String> result = new MutableLiveData<>();
        retrofitInterface.insertCart(name, email, phone).enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                result.setValue("");

            }
        });
        return result;

    }

    public MutableLiveData<String> insertCartDetail(String json) {
        final MutableLiveData<String> result = new MutableLiveData<>();
        retrofitInterface.insertCartDetail(json).enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                result.setValue(t.toString());
            }
        });
        return result;

    }
}
