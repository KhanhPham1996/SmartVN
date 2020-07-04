package com.example.smartvn.model;

import androidx.annotation.Nullable;

public class CartDetail {
    @Nullable
    int id;

    int cartid;
    int productid;
    String productName;
    int productPrice;
    int productCount;
    public CartDetail( int cartid, int productid, String productName, int productPrice,int productCount) {
        this.cartid = cartid;
        this.productid = productid;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCount = productCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCartid() {
        return cartid;
    }

    public void setCartid(int cartid) {
        this.cartid = cartid;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }
}
