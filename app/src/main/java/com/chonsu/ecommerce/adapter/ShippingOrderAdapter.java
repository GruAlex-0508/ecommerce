package com.chonsu.ecommerce.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.model.ShippingProduct;

import java.util.ArrayList;

public class ShippingOrderAdapter extends RecyclerView.Adapter<ShippingOrderAdapter.OrderViewHolder> {

    private final ArrayList<ShippingProduct> orderProducts;

    public ShippingOrderAdapter(ArrayList<ShippingProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImg;
        TextView tvProductName, tvProductSize, tvProductPrice, tvProductAmount;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProductImg = itemView.findViewById(R.id.order_image);
            tvProductName = itemView.findViewById(R.id.order_name);
            tvProductSize = itemView.findViewById(R.id.order_size);
            tvProductPrice = itemView.findViewById(R.id.order_price);
            tvProductAmount = itemView.findViewById(R.id.order_amount);
        }
    }

    @NonNull
    @Override
    public ShippingOrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shipping_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShippingOrderAdapter.OrderViewHolder holder, int position) {

        holder.itemView.setTag(position);

        holder.tvProductName.setText(orderProducts.get(position).getProductName());
        holder.tvProductSize.setText(orderProducts.get(position).getProductSize());
        holder.tvProductAmount.setText(orderProducts.get(position).getProductAmount());
        holder.tvProductPrice.setText(orderProducts.get(position).getProductPrice());


        // set ImageView's element by using ImageName of array ==================================

        int ImageResId = holder.itemView.getContext().getResources().getIdentifier(
                orderProducts.get(position).getProductImg(), "drawable", holder.itemView.getContext().getPackageName());

        if (ImageResId != 0) {
            holder.ivProductImg.setImageResource(ImageResId);
        } else {
            holder.ivProductImg.setImageResource(R.drawable.order_first);
        }
        // =======================================================================================
    }

    @Override
    public int getItemCount() {
        return orderProducts.size();
    }
}
