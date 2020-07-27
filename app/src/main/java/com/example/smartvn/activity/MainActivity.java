package com.example.smartvn.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartvn.R;
import com.example.smartvn.adapter.ProductCategoryAdapter;
import com.example.smartvn.adapter.ProductLatestAdapter;
import com.example.smartvn.databinding.ActivityMainBinding;
import com.example.smartvn.model.Cart;
import com.example.smartvn.model.Product;
import com.example.smartvn.model.ProductCategory;
import com.example.smartvn.ultil.ApiUltil;
import com.example.smartvn.ultil.ItemClickListener;
import com.example.smartvn.ultil.RetrofitInterface;
import com.example.smartvn.viewmodel.LatestProductViewModel;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ArrayList<ProductCategory> listProductCategory;
    private List<Product> products;
    private RetrofitInterface mService;
    private ProductCategoryAdapter productCategoryAdapter;
    private ProductLatestAdapter productAdapter;
    public static ArrayList<Cart> listCart;
    private RequestOptions requestOptions;
    private int shortAnimationDuration;
    private LatestProductViewModel latestProductViewModel;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;

    private ActivityMainBinding binding;
    String priceInString;
    DecimalFormat formater = new DecimalFormat("###,###,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setIDForView();
        viewConfiguration();
        getDataFromServer();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void getDataFromServer() {
        latestProductViewModel    = new ViewModelProvider(this).get(LatestProductViewModel.class);
        binding.shimmerViewContainer.startShimmerAnimation();
        binding.mainRecycerview.setAlpha(0f);
        binding.mainRecycerview.setVisibility(View.VISIBLE);
        latestProductViewModel.init();
        latestProductViewModel.getLatestProductRepository().observe(this, respone -> {
            binding.shimmerViewContainer.stopShimmerAnimation();
            binding.shimmerViewContainer.animate()
                    .alpha(0f)
                    .setDuration(shortAnimationDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            binding.shimmerViewContainer.setVisibility(View.GONE);
                        }
                    });
            binding.mainRecycerview.animate()
                    .alpha(1f)
                    .setDuration(shortAnimationDuration)
                    .setListener(null);
            products = respone;
            productAdapter = new ProductLatestAdapter(getApplicationContext(), products);
            binding.mainRecycerview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
            binding.mainRecycerview.setAdapter(productAdapter);
            productAdapter.setOnItemClick(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    Intent intent = new Intent(MainActivity.this, ProductDetail.class);
                    intent.putExtra("product", products.get(position));
                    startActivity(intent);
                }
            });
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        Drawable drawable = menu.findItem(R.id.action_cart).getIcon();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.colorWhite));
        menu.findItem(R.id.action_cart).setIcon(drawable);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        }

        return false;
    }

    private void fadeOutAndHideImage(final View img) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(1000);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                img.setVisibility(View.GONE);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });

        img.startAnimation(fadeOut);
    }

    private void viewConfiguration() {
        setSupportActionBar(binding.toolbarmain);
        setTitle("Trang chủ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarmain.setNavigationIcon(R.drawable.ic_menu_home);
        binding.toolbarmain.setTitleTextColor(Color.WHITE);
        binding.toolbarmain.setSubtitleTextColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            binding.toolbarmain.getNavigationIcon().setColorFilter(new BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP));
        } else {
            binding.toolbarmain.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
        binding.toolbarmain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        setDataViewFlipper();
    }

    private void setDataViewFlipper() {
        List<String> listImageURL = new ArrayList<String>();
        listImageURL.add("https://cdn.tgdd.vn/2020/06/banner/Combo-800-300-800x300.png");
        listImageURL.add("https://cdn.tgdd.vn/2020/05/banner/60749522-6C82-4FF3-BEF0-76FD21BB4D25-800x300.png");
        listImageURL.add("https://cdn.tgdd.vn/2020/05/banner/dh-tre-em-800-300-800x300-1.png");
        listImageURL.add("https://cdn.tgdd.vn/2020/05/banner/800-300-800x300-23.png");

        for (String url : listImageURL) {

            ImageView imgBanner = new ImageView(getApplicationContext());
            imgBanner.setScaleType(ImageView.ScaleType.FIT_XY);

            Glide.with(getApplicationContext()).load(url).apply(requestOptions).into(imgBanner);
            binding.viewflipper.addView(imgBanner);
        }
        binding.viewflipper.setFlipInterval(5000);
        binding.viewflipper.setAutoStart(true);
        Animation anim_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation anim_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        binding.viewflipper.setInAnimation(anim_slide_in);
        binding.viewflipper.setOutAnimation(anim_slide_out);
    }

    private void setIDForView() {
        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_mediumAnimTime);
        if (listCart == null) listCart = new ArrayList<Cart>();
        mService = ApiUltil.getRetrofitInterface();
        products = new ArrayList<Product>();
        requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        listProductCategory = new ArrayList<ProductCategory>();
        productCategoryAdapter = new ProductCategoryAdapter(getApplicationContext(), listProductCategory);
        binding.navigationview.getMenu().clear();
        binding.navigationview.inflateMenu(R.menu.main_navigation_items);
        binding.navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_mobile: {
                        Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                        intent.putExtra("productcatagoryid", "1");
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_laptop: {
                        Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                        intent.putExtra("productcatagoryid", "2");
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_home: {
                        break;
                    }
                    case R.id.nav_info: {
                        //do somthing
                        break;
                    }
                    case R.id.nav_support: {
                        break;
                    }
                }
                //close navigation drawer
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }


}
