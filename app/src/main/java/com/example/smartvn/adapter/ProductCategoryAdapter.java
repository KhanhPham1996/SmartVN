package com.example.smartvn.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.smartvn.R;
import com.example.smartvn.model.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryAdapter extends BaseAdapter {
    ArrayList<ProductCategory> productCategories;
    Context context;

    public ProductCategoryAdapter(Context context, ArrayList productCategories) {
        this.productCategories = productCategories;
        this.context = context;
    }

    @Override
    public int getCount() {
        return productCategories.size();
    }

    @Override
    public Object getItem(int position) {
        return productCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder {
        TextView categoryName;
        ImageView categoryImage;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_product_category,null);
            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.textview_catagoryname);
            viewHolder.categoryImage = (ImageView) convertView.findViewById(R.id.imgview_catagory);
            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        ProductCategory productCategory = (ProductCategory) getItem(position);
        viewHolder.categoryName.setText(productCategory.getCategoryname());
        Glide.with(context).load(productCategory.getCatagoryimage()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(viewHolder.categoryImage);
        return convertView;
    }
}
