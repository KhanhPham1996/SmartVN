package com.example.smartvn.model;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.smartvn.BR;
import com.example.smartvn.ultil.Ultil;

import static com.example.smartvn.ultil.Ultil.myOptions;

public class Cart extends BaseObservable {
    public String productID;
    public String productName;
    public String productTotalPrice;
    public int staticPrice;
    public String productImage;
    public int productCount;

    public Cart(String productID, String productName, String productTotalPrice, String productImage, int productCount,int staticPrice) {
        this.productID = productID;
        this.productName = productName;
        this.productTotalPrice = productTotalPrice;
        this.productImage = productImage;
        this.productCount = productCount;
        this.staticPrice = staticPrice;
    }

    public int getStaticPrice() {
        return staticPrice;
    }

    public void setStaticPrice(int staticPrice) {
        this.staticPrice = staticPrice;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
    @Bindable
    public String getProductName() {
        return productName;

    }

    public void setProductName(String productName) {
        this.productName = productName;
        notifyPropertyChanged(BR.productName);
    }
    @Bindable
    public String getProductTotalPrice() {
        return  Ultil.DECIMAL_FORMAT.format(Integer.parseInt(productTotalPrice)) +" VNĐ";
    }

    public void setProductTotalPrice(String productTotalPrice) {
        this.productTotalPrice =productTotalPrice;
        notifyPropertyChanged(BR.productTotalPrice);

    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
    @Bindable
    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
        notifyPropertyChanged(BR.productCount);

    }

    @BindingAdapter({"productImage"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(myOptions)
                .into(view);

        // If you consider Picasso, follow the below
        // Picasso.with(view.getContext()).load(imageUrl).placeholder(R.drawable.placeholder).into(view);
    }
}
