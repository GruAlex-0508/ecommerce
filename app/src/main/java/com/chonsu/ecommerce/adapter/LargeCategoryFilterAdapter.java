package com.chonsu.ecommerce.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chonsu.ecommerce.model.LargeCategory;

import java.util.ArrayList;
import java.util.List;

public class LargeCategoryFilterAdapter extends RecyclerView.Adapter<LargeCategoryFilterAdapter.ViewHolder> {

    public interface OnFilterClickListener {
        void onFilterClick(LargeCategory category);
    }

    private final List<LargeCategory> items = new ArrayList<>();
    private final OnFilterClickListener listener;
    private String selectedId = "ALL";
    public LargeCategoryFilterAdapter(List<LargeCategory> categories, OnFilterClickListener listener) {
        this.items.addAll(categories);
        this.listener = listener;
    }

    public void setSelectedId (String id) {
        selectedId = id;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType (int position) {
        return position == 0 ? 0 : 1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = new TextView(parent.getContext());

        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

        params.setMargins(0, 0, dp(parent, 10), 0);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);

        textView.setTextSize(14);
        textView.setPadding(dp(parent, 20), 0, dp(parent, 20), 0);
        textView.setSingleLine(true);
        return new ViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        boolean isAll = pos == 0;
        LargeCategory category = isAll ? null : items.get(pos - 1);

        String id = isAll ? "ALL" : category.getId();
        holder.tvFilter.setText(isAll ? "ALL" : category.getName());

        boolean selected = id.equals(selectedId);
        GradientDrawable background = new GradientDrawable();
        background.setCornerRadius(dp(holder.itemView, 30));

        if (selected) {
            background.setColor(Color.rgb(118, 80, 50));
            background.setStroke(dp(holder.itemView, 1), Color.rgb(118, 80, 50));
            holder.tvFilter.setTextColor(Color.WHITE);
        } else {
            background.setColor(Color.WHITE);
            background.setStroke(dp(holder.itemView, 1), Color.rgb(128, 86, 61));
            holder.tvFilter.setTextColor(Color.rgb(118, 80, 50));
        }

        holder.tvFilter.setBackground(background);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onFilterClick(category);
        });
    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }

    private int dp(View view, int value) {
        return (int) (value * view.getResources().getDisplayMetrics().density);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFilter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFilter = (TextView) itemView;
        }
    }
}
