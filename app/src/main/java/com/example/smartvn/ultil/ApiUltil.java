package com.example.smartvn.ultil;

import java.text.DecimalFormat;

public class ApiUltil {

    public static final String BASE_URL = "https://khanhsmart.000webhostapp.com/server/";

    public static RetrofitInterface getRetrofitInterface() {
        return RetrofitClient.getClient(BASE_URL).create(RetrofitInterface.class);
    }
}
