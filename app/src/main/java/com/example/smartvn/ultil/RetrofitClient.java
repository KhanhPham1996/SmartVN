package com.example.smartvn.ultil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new CustomInterceptor()) // This is used to add ApplicationInterceptor.
            .addNetworkInterceptor(new CustomInterceptor()) //This is used to add NetworkInterceptor.
            .build();

    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
