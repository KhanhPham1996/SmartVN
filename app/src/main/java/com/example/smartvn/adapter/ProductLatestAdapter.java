package com.example.smartvn.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.smartvn.R;
import com.example.smartvn.databinding.ItemLastestProductBinding;
import com.example.smartvn.model.Product;
import com.example.smartvn.ultil.ItemClickListener;

import java.text.DecimalFormat;
import java.util.List;

public class ProductLatestAdapter extends RecyclerView.Adapter<ProductLatestAdapter.ProductsViewHoder> {


    private Context context;
    private List<Product> productList;
    private ItemClickListener itemClickListener;
    private RequestOptions myOptions;
    private LayoutInflater layoutInflater;

    public ProductLatestAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        myOptions = new RequestOptions()
                .override(300, 300);
    }


    @NonNull
    @Override
    public ProductsViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemLastestProductBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.item_lastest_product, parent, false);
        return new ProductsViewHoder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHoder holder, int position) {
//        Product product = productList.get(position);
//        holder.tvProductName.setText(product.getProductname());
//        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        int priceToShow = Integer.parseInt(product.getPrice());
//        holder.tvProductPrice.setText(decimalFormat.format(priceToShow) + " VNĐ");
//
//        Glide.with(context).load(product.getImage()).apply(myOptions).listener(new RequestListener<Drawable>() {
//            @Override
//            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                Toast.makeText(context,"Somethings went wrong",Toast.LENGTH_SHORT);
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                return false;
//            }
//        }).into(holder.ivProductAva);
        Product product = productList.get(position);
        holder.binding.setProduct(product);
        holder.binding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setOnItemClick(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class ProductsViewHoder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductPrice;
        ImageView ivProductAva;
        ItemLastestProductBinding binding;

        public ProductsViewHoder(@NonNull final View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            ivProductAva = itemView.findViewById(R.id.img_product_ava);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick(itemView, getAdapterPosition(), false);
                }
            });
        }

        public ProductsViewHoder(final ItemLastestProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick(itemView, getAdapterPosition(), false);
                }
            });
        }
    }
}
