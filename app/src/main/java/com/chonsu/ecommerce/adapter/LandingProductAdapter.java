package com.chonsu.ecommerce.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LandingProductAdapter extends RecyclerView.Adapter<LandingProductAdapter.ProductViewHolder> {

    private final List<Product> products = new ArrayList<>();

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    private final OnProductClickListener listener;

    public LandingProductAdapter(OnProductClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.format(Locale.US, "%.0f $", product.getPrice()));
        holder.productImage.setImageResource(product.getImageResId());

        if (product.isNew()) {
            holder.productBadge.setVisibility(View.VISIBLE);
        } else {
            holder.productBadge.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onProductClick(product);
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<Product> newProducts) {
        products.clear();
        if (newProducts != null) products.addAll(newProducts);

        notifyDataSetChanged();
    }
    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView productBadge;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.imgProduct);
            productName = itemView.findViewById(R.id.txtProductName);
            productPrice = itemView.findViewById(R.id.txtProductPrice);
            productBadge = itemView.findViewById(R.id.txtProductBadge);
        }
    }
}
