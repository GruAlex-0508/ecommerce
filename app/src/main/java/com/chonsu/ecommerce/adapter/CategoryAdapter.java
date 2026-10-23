package com.chonsu.ecommerce.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.model.Category;
import com.chonsu.ecommerce.model.SmallCategory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final List<SmallCategory> categoryList;
    private final OnCategoryClickListener listener;

//    private int expandedPosition = RecyclerView.NO_POSITION;

    private final Set<Integer> expandedPositions = new HashSet<>();
    public interface OnCategoryClickListener {
        void onCategorySelected(SmallCategory smallCategory, Category category);
    }

    public CategoryAdapter(List<SmallCategory> categoryList, OnCategoryClickListener listener) {
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        SmallCategory smallCategory = categoryList.get(position);
        holder.tvCategoryName.setText(smallCategory.getName());

        List<Category> categories = smallCategory.getCategories();

        if (!categories.isEmpty()) {
            holder.imgCategory.setImageResource(categories.get(0).getImageResId());
        } else {
            holder.imgCategory.setImageResource(R.drawable.category_circle);
        }

        if (categories == null) {
            categories = new ArrayList<>();
        }
        holder.tvCategoryCount.setText("(" + categories.size() + ")");

        boolean isExpanded = expandedPositions.contains(position);

        if (isExpanded) {
            holder.cardSubcategory.setVisibility(View.VISIBLE);
            holder.ivExpand.setRotation(180f);

        } else {
            holder.cardSubcategory.setVisibility(View.GONE);
            holder.ivExpand.setRotation(0f);
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(holder.itemView.getContext(), 2);
        holder.rvSubcategory.setLayoutManager(gridLayoutManager);

        SubCategoryAdapter subCategoryAdapter = new SubCategoryAdapter(categories, category -> {
            if (listener != null) {
                listener.onCategorySelected(smallCategory, category);
            }
        });
        holder.rvSubcategory.setAdapter(subCategoryAdapter);
        holder.rvSubcategory.setNestedScrollingEnabled(false);
        holder.layoutCategoryHeader.setOnClickListener(v -> {
            int clickedPosition = holder.getAdapterPosition();

            if (expandedPositions.contains(clickedPosition)) {
                expandedPositions.remove(clickedPosition);
            } else {
                expandedPositions.add(clickedPosition);
            }
            notifyItemChanged(clickedPosition);
        });
    }

    public void setCategories(List<SmallCategory> categories) {
        categoryList.clear();
        if (categories != null) {
            categoryList.addAll(categories);
        }
        expandedPositions.clear();
        notifyDataSetChanged();
    }
    public List<SmallCategory> getCategories() {
        return new ArrayList<>(categoryList);
    }

    @Override
    public int getItemCount() {
        return categoryList == null ? 0 : categoryList.size();
    }
    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName;
        LinearLayout layoutCategoryHeader;
        CardView cardSubcategory;
        RecyclerView rvSubcategory;
        ImageView ivExpand;
        ImageView imgCategory;
        TextView tvCategoryCount;
        public CategoryViewHolder(@NonNull View itemView) {

            super(itemView);
            imgCategory = itemView.findViewById(R.id.imgMainCategory);
            ivExpand = itemView.findViewById(R.id.imgExpandCategory);
            tvCategoryName = itemView.findViewById(R.id.tvMainCategoryName);
            tvCategoryCount = itemView.findViewById(R.id.tvMainCategoryCount);
            layoutCategoryHeader = itemView.findViewById(R.id.layoutCategoryHeader);
            cardSubcategory = itemView.findViewById(R.id.cardSubcategory);
            rvSubcategory = itemView.findViewById(R.id.rvSubcategory);
        }
    }
}
