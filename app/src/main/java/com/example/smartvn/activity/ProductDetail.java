package com.example.smartvn.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.smartvn.R;
import com.example.smartvn.model.Cart;
import com.example.smartvn.model.Product;
import com.example.smartvn.ultil.Ultil;

public class ProductDetail extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvProductName;
    private TextView tvProductPrice;
    private TextView tvProDescription;
    private ImageView imgAva;
    private TextView btnAdd;
    private TextView btnMinus;
    private int productCout;
    private TextView tvProductCount;
    private Button btnAddToCart;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        initViewAndValue();
        setData();
    }


    private void initViewAndValue() {
        toolbar = findViewById(R.id.toolbardetail);
        tvProductName = findViewById(R.id.tv_product_detail_name);
        tvProductPrice = findViewById(R.id.tv_product_detail_price);
        tvProDescription = findViewById(R.id.tv_product_detail_description);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        imgAva = findViewById(R.id.img_product_detail);
        btnAdd = findViewById(R.id.btn_add);
        btnMinus = findViewById(R.id.btn_minus);
        productCout = 1;
        tvProductCount = findViewById(R.id.tv_product_count);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            toolbar.getNavigationIcon().setColorFilter(new BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP));
        } else {
            toolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productCout<10){
                    productCout++;
                    tvProductCount.setText(String.valueOf(productCout));
                }
            }
        });
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productCout>1){
                    productCout--;
                    tvProductCount.setText(String.valueOf(productCout));
                }
            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.listCart.size()>0){
                    int productCount = Integer.parseInt(tvProductCount.getText().toString());
                    boolean exist = false;
                    for(int i =0 ;i < MainActivity.listCart.size();i++){
                        if(MainActivity.listCart.get(i).getProductID().equals(product.getId())){
                             MainActivity.listCart.get(i).setProductCount(MainActivity.listCart.get(i).getProductCount() + productCount);
                             if(MainActivity.listCart.get(i).getProductCount() >= 10){
                                 MainActivity.listCart.get(i).setProductCount(10);
                             }
                             MainActivity.listCart.get(i).setProductTotalPrice(String.valueOf(Ultil.PriceFomater(product.getPrice()) * MainActivity.listCart.get(i).getProductCount()));
                             exist = true;
                        }
                    }
                    if(exist == false){
                        int productCount1 = Integer.parseInt(tvProductCount.getText().toString());

                        long newPrice = productCount1 * Ultil.PriceFomater(product.getPrice());
                        MainActivity.listCart.add(new Cart(product.getId(),product.getProductname(),String.valueOf(newPrice),product.getImage(),productCount,Ultil.PriceFomater(product.getPrice())));
                    }
                }
                else {
                    int productCount = Integer.parseInt(tvProductCount.getText().toString());
                    long newPrice = productCount * Ultil.PriceFomater(product.getPrice());
                    MainActivity.listCart.add(new Cart(product.getId(),product.getProductname(),String.valueOf(newPrice),product.getImage(),productCount,Ultil.PriceFomater(product.getPrice())));
                }
                Intent intent = new Intent(ProductDetail.this,CartActivity.class);
                startActivity(intent);
            }
        });
    }



    private void setData() {
        Intent intent = getIntent();
        product =  (Product) intent.getSerializableExtra("product");
        tvProDescription.setText(product.getDescription());
        tvProductPrice.setText(product.getPrice());
        tvProductName.setText(product.getProductname());
        Glide.with(getApplicationContext()).load(product.getImage()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Toast.makeText(getApplicationContext(), "Load Fail:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(imgAva);
    }
}
