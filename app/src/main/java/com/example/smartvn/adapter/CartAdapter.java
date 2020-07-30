package com.example.smartvn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartvn.R;
import com.example.smartvn.activity.MainActivity;
import com.example.smartvn.databinding.ItemCartBinding;
import com.example.smartvn.model.Cart;
import com.example.smartvn.model.CartPriceFomater;
import com.example.smartvn.ultil.ButtonClickListener;
import com.example.smartvn.ultil.ItemClickListener;

import java.util.List;


// Comment for new branche
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<Cart> lstCart;
    private ButtonClickListener buttonClickListener;
    private ItemClickListener itemClickListener;
    private LayoutInflater layoutInflater;

    public CartAdapter(Context context, List<Cart> lstCart) {
        this.context = context;
        this.lstCart = lstCart;
        this.itemClickListener = itemClickListener;
    }

    public void SetOnAddOrMinusClick(ButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemCartBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.item_cart, parent, false);

        return new CartViewHolder(binding);
    }

    public void removeItem(int position) {
        lstCart.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Cart item, int position) {
        lstCart.add(position, item);
        notifyItemInserted(position);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = this.lstCart.get(position);
        CartPriceFomater mModel=  new CartPriceFomater();

        holder.binding.setCart(cart);

   //     mModel.setPriceParedString(cart.getProductTotalPrice());

        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return lstCart.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        LinearLayout linearBackground;
        public ItemCartBinding binding;
        public CartViewHolder(ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            cardView = binding.cardView;
            binding.tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClickListener.onAddLick(binding.tvAdd, getAdapterPosition());
                }
            });
            binding.tvMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClickListener.onMinusCLick(binding.tvAdd, getAdapterPosition());

                }
            });

        }
    }


}
