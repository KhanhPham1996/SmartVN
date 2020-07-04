package com.example.smartvn.ultil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CustomInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
    /*
    chain.request() returns original request that you can work with(modify, rewrite)
    */
        Request request = chain.request();

        // Here you can rewrite the request

        /*
    chain.proceed(request) is the call which will initiate the HTTP work. This call invokes the request and returns the response as per the request.
        */
        Response response = chain.proceed(request);

        //Here you can rewrite/modify the response

        return response;
    }
}