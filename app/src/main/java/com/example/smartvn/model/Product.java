package com.example.smartvn.model;


import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartvn.BR;
import com.example.smartvn.ultil.Ultil;
import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Product extends BaseObservable implements Serializable {
    @Expose
    private String id;
    @Expose
    private String productname;
    @Expose
    private String price;
    @Expose
    private String image;
    @Expose
    private String description;
    @Expose
    private String categoryid;

    public Product(String id, String productname, String price, String image, String description, String categoryid) {
        this.id = id;
        this.productname = productname;
        this.price = price;
        this.image = image;
        this.description = description;
        this.categoryid = categoryid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }
    @Bindable
    public String getPrice() {
        return Ultil.DECIMAL_FORMAT.format(Integer.parseInt(price)) +" VNĐ";
    }

    public void setPrice(String price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    @BindingAdapter({"productImage"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(Ultil.myOptions)
                .into(view);

        // If you consider Picasso, follow the below
        // Picasso.with(view.getContext()).load(imageUrl).placeholder(R.drawable.placeholder).into(view);
    }
}
