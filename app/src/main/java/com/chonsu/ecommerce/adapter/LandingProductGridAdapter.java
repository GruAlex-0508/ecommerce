package com.chonsu.ecommerce.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chonsu.ecommerce.databinding.ItemProductGridBinding;
import com.chonsu.ecommerce.model.ProductGrid;

import java.util.List;
import java.util.Locale;

public class LandingProductGridAdapter extends RecyclerView.Adapter<LandingProductGridAdapter.ProductViewHolder>{

    private final List<ProductGrid> productGridList;

    public LandingProductGridAdapter(List<ProductGrid> productGridList) {
        this.productGridList = productGridList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        ItemProductGridBinding binding = ItemProductGridBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductGrid product = productGridList.get(position);
        ItemProductGridBinding binding = holder.binding;

        binding.ivProduct.setImageResource(product.getImageResId());
        binding.tvProductName.setText(product.getName());
        binding.tvProductSubtitle.setText(product.getSubtitle());
        binding.tvProductPrice.setText(String.format(Locale.US, "%.0f$", product.getPrice()));
        binding.tvNew.setVisibility(product.isNew() ? View.VISIBLE : View.GONE);
        updateFavoriteButton(holder, product);
        binding.btnFavorite.setOnClickListener(v -> {
            product.setFavorite(!product.isFavorite());
            updateFavoriteButton(holder, product);
        });
        binding.btnAddProduct.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), product.getName() + " added to cart", Toast.LENGTH_SHORT).show();
        });

        binding.cardProduct.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Product ID: " + product.getId(), Toast.LENGTH_SHORT).show();
        });
    }

    private void updateFavoriteButton(ProductViewHolder holder, ProductGrid product) {
        if (product.isFavorite()) {
            holder.binding.btnFavorite.setText("★");
            holder.binding.btnFavorite.setTextColor(Color.RED);
        } else {
            holder.binding.btnFavorite.setText("★");
            holder.binding.btnFavorite.setTextColor(Color.rgb(230, 160, 0));
        }
    }

    @Override
    public int getItemCount() {
        return productGridList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        final ItemProductGridBinding binding;
        public ProductViewHolder(@NonNull ItemProductGridBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
