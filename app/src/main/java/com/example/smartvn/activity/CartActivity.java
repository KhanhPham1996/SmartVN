package com.example.smartvn.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.smartvn.R;
import com.example.smartvn.adapter.CartAdapter;
import com.example.smartvn.databinding.ActivityCartBinding;
import com.example.smartvn.model.Cart;
import com.example.smartvn.model.CartPriceFomater;
import com.example.smartvn.ultil.ButtonClickListener;
import com.example.smartvn.ultil.RecyclerItemTouchHelper;
import com.example.smartvn.ultil.Ultil;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class CartActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {


    private CartAdapter cartAdapter;
    private ActivityCartBinding binding;
    private MyClickHandlers handlers;
    private CartPriceFomater cartPriceFomater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        binding.setCart(MainActivity.listCart);
        setViewAndValue();
    }
    public String setTotalPrice(){
        int totalPrice = 0;
        String resultPrice="";
        for(int i =0 ; i< MainActivity.listCart.size();i++){
            totalPrice += Ultil.PriceFomater(MainActivity.listCart.get(i).getProductTotalPrice()); // replace "," "VND" thanh khaong trang

         //   totalPrice += Integer.valueOf(Pattern.compile("(\\,|\\VNĐ|\\s+)").matcher(MainActivity.listCart.get(i).getProductTotalPrice()).replaceAll("")); // replace "," "VND" thanh khaong trang
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        resultPrice =(decimalFormat.format(totalPrice) + " VNĐ");
        return "Total: " +resultPrice;
    }
    private void setViewAndValue() {
        binding.tvTotalPrice.setText(setTotalPrice());
        setSupportActionBar(binding.toolbarcart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        handlers = new MyClickHandlers(this);
        binding.setHandler(handlers);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            binding.toolbarcart.getNavigationIcon().setColorFilter(new BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP));
        } else {
            binding.toolbarcart.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
        binding.toolbarcart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(MainActivity.listCart.size() >0){
            cartAdapter = new CartAdapter(getApplicationContext(), MainActivity.listCart);
            binding.recyclerCart.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            binding.recyclerCart.setAdapter(cartAdapter);
            cartAdapter.SetOnAddOrMinusClick(new ButtonClickListener() {
                @Override
                public void onAddLick(View view, int position) {
                    if(MainActivity.listCart.get(position).getProductCount()<10){
                        MainActivity.listCart.get(position).setProductCount((MainActivity.listCart.get(position).getProductCount()+1));
                        int price =   MainActivity.listCart.get(position).getStaticPrice() *MainActivity.listCart.get(position).getProductCount() ;
                        MainActivity.listCart.get(position).setProductTotalPrice(String.valueOf(price));
                        binding.tvTotalPrice.setText(setTotalPrice());
                    }
                }

                @Override
                public void onMinusCLick(View view, int position) {
                    if(MainActivity.listCart.get(position).getProductCount()>1){
                        MainActivity.listCart.get(position).setProductCount((MainActivity.listCart.get(position).getProductCount()-1));
                        int price =  MainActivity.listCart.get(position).getStaticPrice() *MainActivity.listCart.get(position).getProductCount() ;
                        MainActivity.listCart.get(position).setProductTotalPrice(String.valueOf(price));
                        binding.tvTotalPrice.setText(setTotalPrice());
                    }
                }
            });
        }else {
            binding. relativeEmptyCart.setVisibility(View.VISIBLE);
        }
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerCart);
    }
    public class MyClickHandlers {
        Context context;

        public MyClickHandlers(Context context) {
            this.context = context;
        }

        public void onBtnPayClick(View view) {
            if(MainActivity.listCart.size()>0){
                Intent intent = new Intent(CartActivity.this,CustomerInfoAcivity.class);
                startActivity(intent);
            }
            else {
                Snackbar snackbar = Snackbar
                        .make(binding.linearMain,   " There is nothing to buy", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
        public void onBtnShoppingClick(View view) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

    }
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartAdapter.CartViewHolder) {
            String name = MainActivity.listCart.get(viewHolder.getAdapterPosition()).getProductName();
            final Cart deletedItem =  MainActivity.listCart.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();
            cartAdapter.removeItem(viewHolder.getAdapterPosition());
            if(MainActivity.listCart.size()<=0){
                binding.relativeEmptyCart.setVisibility(View.VISIBLE);
            }
            Snackbar snackbar = Snackbar
                    .make(binding.linearMain, name + " removed from library!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartAdapter.restoreItem(deletedItem, deletedIndex);
                    if(MainActivity.listCart.size()>0){
                        binding.relativeEmptyCart.setVisibility(View.INVISIBLE);
                    }
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
            binding.tvTotalPrice.setText(setTotalPrice());
        }
    }
}
