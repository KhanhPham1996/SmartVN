package com.example.smartvn.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.smartvn.R;
import com.example.smartvn.model.Cart;
import com.example.smartvn.model.CartDetail;
import com.example.smartvn.ultil.ApiUltil;
import com.example.smartvn.ultil.RetrofitInterface;
import com.example.smartvn.ultil.Ultil;
import com.example.smartvn.viewmodel.InsertCartDetailViewModel;
import com.example.smartvn.viewmodel.InsertCartViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerInfoAcivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputLayout textInpuName, textInputEmail, textInputPhone;
    private Button btnConfirm;
    private RetrofitInterface retrofitInterface;
    private LinearLayout linearParent;
    private InsertCartViewModel insertCartViewModel;
    private InsertCartDetailViewModel inserCartDeteailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info_acivity);
        setViewAndValue();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    private void setViewAndValue() {
        toolbar = findViewById(R.id.toolbarcustomerinfo);
        textInpuName = findViewById(R.id.text_input_full_name);
        textInputEmail = findViewById(R.id.text_input_email);
        textInputPhone = findViewById(R.id.text_input_phone);
        linearParent = findViewById(R.id.linear_parent);
        btnConfirm = findViewById(R.id.btn_confirm);
        retrofitInterface = ApiUltil.getRetrofitInterface();
        insertCartViewModel = new ViewModelProvider(this).get(InsertCartViewModel.class);
        inserCartDeteailViewModel = new ViewModelProvider(this).get(InsertCartDetailViewModel.class);
        LifecycleOwner lifecycleOwner = this;
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
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputData()) {
                    String fullName = textInpuName.getEditText().getText().toString().trim();
                    String email = textInputEmail.getEditText().getText().toString().trim();
                    String phone = textInputPhone.getEditText().getText().toString().trim();
                    insertCartViewModel.init(fullName, email, phone);
                    insertCartViewModel.getResult().observe(lifecycleOwner, respone -> {

                        if(!respone.isEmpty()){
                            ArrayList<CartDetail> cartDetails = new ArrayList<>();
                            for (Cart cart : MainActivity.listCart) {
                                CartDetail cartDetail = new CartDetail(Integer.parseInt(respone), Integer.parseInt(cart.getProductID()), cart.getProductName(), Ultil.PriceFomater(cart.getProductTotalPrice()), cart.getProductCount());
                                cartDetails.add(cartDetail);
                            }
                            String json = new Gson().toJson(cartDetails);
                            inserCartDeteailViewModel.init(json);
                            inserCartDeteailViewModel.getResult().observe(lifecycleOwner, responeValue -> {
                                if (responeValue.equals("success")) {
                                    Toast.makeText(getApplicationContext(), "Đơn hàng của bạn đã được tạo", Toast.LENGTH_SHORT).show();
                                    MainActivity.listCart.clear();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(),"Dữ liệu lỗi",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Dữ liệu lỗi",Toast.LENGTH_SHORT).show();

                        }

                    });
                }
            }
        });


    }

    private void insetCart() {
    }

    private Boolean validateInputData() {

        if (textInpuName.getEditText().getText().toString().isEmpty()) {
            textInpuName.setError("Fullname is empty");
            return false;
        } else if (textInputEmail.getEditText().getText().toString().isEmpty()) {
            textInputEmail.setError("Email is empty");
            return false;

        } else if (!Ultil.isValidEmail(textInputEmail.getEditText().getText().toString())) {
            textInputEmail.setError("Email is invalid");
            return false;
        } else if (textInputPhone.getEditText().getText().toString().isEmpty()) {
            textInputPhone.setError("Phone is empty");
            return false;
        } else return true;

    }
}
