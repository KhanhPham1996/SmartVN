package com.example.smartvn.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.smartvn.BR;

import java.text.DecimalFormat;

public class CartPriceFomater extends BaseObservable {
    public String priceParedString;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");


    @Bindable
    public String getPriceParedString() {
        return priceParedString;
    }

    public void setPriceParedString(String productPrice) {
        priceParedString =decimalFormat.format(Integer.parseInt(productPrice));
        notifyPropertyChanged(BR.priceParedString);
    }
}
