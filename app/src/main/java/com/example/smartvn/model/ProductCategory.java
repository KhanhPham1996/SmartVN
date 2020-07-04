package com.example.smartvn.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductCategory {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("categoryname")
    @Expose
    private String categoryname;

    @SerializedName("catagoryimage")
    @Expose
    private String catagoryimage;

    public String getId() {
        return id;
    }

    public ProductCategory(String id, String categoryname, String catagoryimage) {
        this.id = id;
        this.categoryname = categoryname;
        this.catagoryimage = catagoryimage;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getCatagoryimage() {
        return catagoryimage;
    }

    public void setCatagoryimage(String catagoryimage) {
        this.catagoryimage = catagoryimage;
    }
}
