package com.chonsu.ecommerce.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.activity.ShippingActivity;
import com.chonsu.ecommerce.model.ShippingAddress;

import java.util.ArrayList;

public class ShippingAddressAdapter extends RecyclerView.Adapter<ShippingAddressAdapter.AddressViewHolder> {

    private final ArrayList<ShippingAddress> addresses;

    private final ShippingActivity shippingActivity;

    public ShippingAddressAdapter(ShippingActivity shippingActivity, ArrayList<ShippingAddress> addresses) {
        this.addresses = addresses;
        this.shippingActivity = shippingActivity;
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder {

        ImageView ivLocation, ivRadio;
        TextView tvAddressName, tvAddressLocation;
        View addressDivider;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);

            ivLocation = itemView.findViewById(R.id.ic_address_location);
            ivRadio = itemView.findViewById(R.id.ic_address_select);
            tvAddressLocation = itemView.findViewById(R.id.address_location);
            tvAddressName = itemView.findViewById(R.id.address_name);
            addressDivider = itemView.findViewById(R.id.shippingAddress_divider);
        }
    }

    @NonNull
    @Override
    public ShippingAddressAdapter.AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shipping_address, parent, false);
        return new AddressViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ShippingAddressAdapter.AddressViewHolder holder, int position) {


        holder.itemView.setTag(addresses.get(position));

        holder.ivLocation.setImageResource(R.drawable.ic_location);
        holder.ivRadio.setImageResource(R.drawable.ic_radio_unselect);
        holder.tvAddressName.setText(addresses.get(position).getAddressName());
        holder.tvAddressLocation.setText(addresses.get(position).getAddressLocation());

        long selectedShippingAddressId = 1;

        if (shippingActivity.getSelectedShippingAddress() != null)
            selectedShippingAddressId = shippingActivity.getSelectedShippingAddress().getId();

        if (addresses.get(position).getId() == selectedShippingAddressId) {
            holder.ivLocation.setAlpha(1.0f);
            holder.ivRadio.setAlpha(1.0f);
            holder.addressDivider.setAlpha(1.0f);
            holder.tvAddressName.setAlpha(1.0f);
            holder.tvAddressLocation.setAlpha(1.0f);
            holder.ivRadio.setImageResource(R.drawable.ic_radio_select);
            shippingActivity.setShippingAddressName(addresses.get(position).getAddressName());
            shippingActivity.setShippingLocation(addresses.get(position).getAddressLocation());
        } else {
            holder.ivLocation.setAlpha(0.6f);
            holder.ivRadio.setAlpha(0.6f);
            holder.addressDivider.setAlpha(0.6f);
            holder.tvAddressName.setAlpha(0.6f);
            holder.tvAddressLocation.setAlpha(0.6f);
            holder.ivRadio.setImageResource(R.drawable.ic_radio_unselect);
        }

        holder.itemView.setOnClickListener(v -> {
            shippingActivity.setSelectedShippingAddress(addresses.get(position));
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }
}
