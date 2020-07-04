package com.example.smartvn.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartvn.R;
import com.example.smartvn.adapter.ProductCategoryAdapter;
import com.example.smartvn.adapter.ProductListAdapter;
import com.example.smartvn.databinding.ActivityListProductBinding;
import com.example.smartvn.model.Product;
import com.example.smartvn.ultil.ApiUltil;
import com.example.smartvn.ultil.ILoadMore;
import com.example.smartvn.ultil.ItemClickListener;
import com.example.smartvn.ultil.RetrofitInterface;
import com.example.smartvn.viewmodel.ProductByCategory;
import com.facebook.shimmer.ShimmerFrameLayout;


import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RetrofitInterface mService;
    private ArrayList<Product> products;
    private ProductCategoryAdapter productCategoryAdapter;
    private ProductListAdapter productAdapter;
    private RecyclerView recyclerView;
    private View footerView;
    private ItemClickListener itemClickListener;
    private int page;
    private boolean isMoredata;
    private ShimmerFrameLayout shimmer_view_container;
    private int shortAnimationDuration;
    private ProductByCategory productViewModel;
    private ActivityListProductBinding activityListProductBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityListProductBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_list_product);
        initView();
        getDatafromserver(page);
    }
    private void initView() {
        page = 1;
        mService = ApiUltil.getRetrofitInterface();
        toolbar = activityListProductBinding.toolbardetail;
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.item_loading_data, null);
        recyclerView = activityListProductBinding.recyclerListproduct;
        shimmer_view_container = findViewById(R.id.shimmer_loading);
        products = new ArrayList<Product>();
        isMoredata = true;
        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_mediumAnimTime);
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
    }
    private void getDatafromserver(final int page) {
        Intent intent = getIntent();
        String productID = intent.getStringExtra("productcatagoryid");
        shimmer_view_container.startShimmerAnimation();
        if(page==1){
            recyclerView.setAlpha(0f);
            recyclerView.setVisibility(View.VISIBLE);
        }
        productViewModel = new ViewModelProvider(this).get(ProductByCategory.class);
        productViewModel.init(page, productID);
        productViewModel.getProductListRepository().observe(this,respone ->{
            if(respone==null){
                Toast.makeText(getApplicationContext(),"Somethings went wrong",Toast.LENGTH_SHORT).show();
            }else {
                List<Product> listRespone = respone;
                if(page==1){
                    shimmer_view_container.stopShimmerAnimation();
                    shimmer_view_container.animate()
                            .alpha(0f)
                            .setDuration(shortAnimationDuration)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    shimmer_view_container.setVisibility(View.GONE);
                                }
                            });
                    recyclerView.animate()
                            .alpha(1f)
                            .setDuration(shortAnimationDuration)
                            .setListener(null);
                }

                if (listRespone.size() > 0) {
                    if (productAdapter == null) {
                        products.addAll(listRespone);

                        productAdapter = new ProductListAdapter(recyclerView, getApplicationContext(), products);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(productAdapter);
                        productAdapter.setLoaded();
                        loadMoreData();
                    } else {
                        products.remove(products.size() - 1);
                        int scrollPosition = products.size();
                        productAdapter.notifyItemRemoved(scrollPosition);
                        products.addAll(listRespone);
                        productAdapter.notifyItemRangeInserted(scrollPosition,listRespone.size());
                        productAdapter.setLoaded();
                    }
                } else {
                    productAdapter.setLoaded();
                    isMoredata = false;
                    products.remove(products.size() - 1);
                    productAdapter.notifyItemRemoved( products.size());
                }
            }
        });

    }
    private void loadMoreData() {
        if (productAdapter != null) {
            productAdapter.setLoadMore(new ILoadMore() {
                @Override
                public void onLoadMore() {
                    if(isMoredata){
                        products.add(null);
                        productAdapter.notifyItemInserted(products.size() - 1);
                        recyclerView.scrollToPosition(products.size()-1);
                        new Handler().postDelayed(new Runnable() {
                                                      @Override
                                                      public void run() {
                                                          getDatafromserver(++page);
                                                      }
                                                  },500);
                    }
                }
            });
            productAdapter.setOnClick(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    Intent intent = new Intent(ProductListActivity.this,ProductDetail.class);
                    intent.putExtra("product", products.get(position));
                    startActivity(intent);
                }
            });
        }

    }
    public class MyClickHandler {
        Context context;

        public MyClickHandler(Context context) {
            this.context = context;
        }

    }


}
