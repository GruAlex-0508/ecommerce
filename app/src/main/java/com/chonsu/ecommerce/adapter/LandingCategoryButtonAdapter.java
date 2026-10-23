package com.chonsu.ecommerce.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chonsu.ecommerce.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class LandingCategoryButtonAdapter extends RecyclerView.Adapter<LandingCategoryButtonAdapter.ViewHolder> {
    public interface OnCategorySelectedListener {
        void onCategorySelected(int position);
    }
    private final List<String> names = new ArrayList<>();
    private final OnCategorySelectedListener listener;
    private int selectedPosition = 0;
    public LandingCategoryButtonAdapter(OnCategorySelectedListener listener) {
        this.listener = listener;
    }
    public void setCategories(List<String> newNames) {
        names.clear();
        if (newNames != null) {
            names.addAll(newNames);
        }
        selectedPosition = names.isEmpty() ? -1 : 0;
        notifyDataSetChanged();
    }

    public void setSelectedPosition(int position) {
        if (position < 0 || position >= names.size()) {
            return;
        }
        int previousPosition = selectedPosition;
        selectedPosition = position;
        if (previousPosition >= 0) {
            notifyItemChanged(previousPosition);
        }
        notifyItemChanged(selectedPosition);
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_chip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = names.get(position);
        holder.button.setText(name);
        boolean selected = position == selectedPosition;
        if (selected) {
            holder.button.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#6F4E37")));
            holder.button.setTextColor(Color.WHITE);
        } else {
            holder.button.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.WHITE));
            holder.button.setTextColor(Color.parseColor("#6F4E37"));
        }
        holder.button.setOnClickListener(v -> {
            int clickedPosition = holder.getAdapterPosition();
            if (clickedPosition == RecyclerView.NO_POSITION || clickedPosition == selectedPosition) return;
            setSelectedPosition(clickedPosition);
            if (listener != null) {
                listener.onCategorySelected(clickedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialButton button;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.btn_category_chip);
        }
    }
}