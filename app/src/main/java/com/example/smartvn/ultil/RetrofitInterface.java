package com.example.smartvn.ultil;

import com.example.smartvn.model.Cart;
import com.example.smartvn.model.Product;
import com.example.smartvn.model.ProductCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("getcatagory.php")
    Call<List<ProductCategory>> getProductCategory();

    @GET("getlatestproduct.php")
    Call<List<Product>> getLastesProducts();

    @GET("getproductbycategory.php")
    Call<List<Product>> getListProducts(@Query("page") int page,@Query("categoryid") String categoryid);

    @FormUrlEncoded
    @POST("insertcart.php")
    Call<String> insertCart(@Field("customername") String  customername,
                          @Field("email") String  email,
                          @Field("phone") String  phone );

    @FormUrlEncoded
    @POST("insertcartdetail.php")
    Call<String> insertCartDetail(@Field("json") String  jsonPost);
}
