package com.example.smartvn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartvn.R;
import com.example.smartvn.databinding.ItemListProductBinding;
import com.example.smartvn.model.Product;
import com.example.smartvn.ultil.ILoadMore;
import com.example.smartvn.ultil.ItemClickListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    private Context context;
    private List<Product> productList;
    int lastVisibleItem, totalItemCount;
    ILoadMore loadMore;
    boolean isLoading;
    private RequestOptions myOptions;
    private ItemClickListener mListener;
    private LayoutInflater layoutInflater;


    public ProductListAdapter(RecyclerView recyclerView, Context context, List<Product> productList) {
        try {
            this.context = context;
            this.productList = productList;
            myOptions = new RequestOptions()
                    .override(300, 300).diskCacheStrategy(DiskCacheStrategy.ALL);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LinearLayoutManager linearLayoutManager = (androidx.recyclerview.widget.LinearLayoutManager) recyclerView.getLayoutManager();

                    if (linearLayoutManager != null) {
                        totalItemCount = linearLayoutManager.getItemCount();
                        lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                        if (!isLoading && lastVisibleItem == totalItemCount - 1) {
                            if (loadMore != null)
                                loadMore.onLoadMore();
                            isLoading = true;
                        }
                    }

                }
            });
        } catch (Exception ex) {

        }

    }

    public void setLoaded() {
        isLoading = false;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        if (viewType == VIEW_TYPE_ITEM) {

            ItemListProductBinding binding =
                    DataBindingUtil.inflate(layoutInflater, R.layout.item_list_product, parent, false);
            return new ProductsViewHoder(binding,mListener);

        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.shimmer_loadmore, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;

    }

    @Override
    public int getItemViewType(int position) {
        return productList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

    public void setOnClick(ItemClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof ProductsViewHoder) {
            Product product = productList.get(position);
            ((ProductsViewHoder) holder).binding.setProduct(product);
            ((ProductsViewHoder) holder).binding.executePendingBindings();

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.shimmer_view_container.startShimmerAnimation();
        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class ProductsViewHoder extends RecyclerView.ViewHolder {
        private ItemClickListener mListener;
        public ItemListProductBinding binding;
        public ProductsViewHoder(final ItemListProductBinding binding, final ItemClickListener mListener) {
            super(binding.getRoot());
            this.binding = binding;
            binding.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(v, getAdapterPosition(), false);
                }
            });
        }



    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {

        ShimmerFrameLayout shimmer_view_container;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            shimmer_view_container = (ShimmerFrameLayout) itemView.findViewById(R.id.shimmer_loadmore);
        }
    }
}