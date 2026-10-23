package com.chonsu.ecommerce.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.common.Global;
import com.chonsu.ecommerce.config.ThemeManager;
import com.chonsu.ecommerce.model.ProfileMenuItem;

import java.util.List;

public class ProfileMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ICON_TILE_ALPHA = 40;

    private final List<ProfileMenuItem> items;
    private final OnProfileMenuClickListener listener;

    public interface OnProfileMenuClickListener {
        void onProfileMenuClick(ProfileMenuItem item);
    }

    public ProfileMenuAdapter(List<ProfileMenuItem> items, OnProfileMenuClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView tvSectionTitle;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSectionTitle = itemView.findViewById(R.id.tvProfileSectionTitle);
        }
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        View layoutMenuIcon;
        ImageView imgMenuIcon;
        TextView tvMenuTitle;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutMenuIcon = itemView.findViewById(R.id.layoutMenuIcon);
            imgMenuIcon = itemView.findViewById(R.id.imgMenuIcon);
            tvMenuTitle = itemView.findViewById(R.id.tvMenuTitle);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == ProfileMenuItem.TYPE_HEADER) {
            View view = inflater.inflate(R.layout.item_profile_section, parent, false);
            return new HeaderViewHolder(view);
        }

        View view = inflater.inflate(R.layout.item_profile_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProfileMenuItem item = items.get(position);

        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).tvSectionTitle.setText(item.getTitleRes());
            return;
        }

        MenuViewHolder menuHolder = (MenuViewHolder) holder;
        Context context = menuHolder.itemView.getContext();

        int iconColor = item.getIconColorRes() == 0
                ? ThemeManager.getThemeColor(context, androidx.appcompat.R.attr.colorPrimary)
                : ContextCompat.getColor(context, item.getIconColorRes());

        menuHolder.tvMenuTitle.setText(item.getTitleRes());
        menuHolder.imgMenuIcon.setImageResource(item.getIconRes());
        ImageViewCompat.setImageTintList(menuHolder.imgMenuIcon, ColorStateList.valueOf(iconColor));
        menuHolder.layoutMenuIcon.setBackgroundTintList(
                ColorStateList.valueOf(ColorUtils.setAlphaComponent(iconColor, ICON_TILE_ALPHA)));

        menuHolder.itemView.setOnClickListener(v -> {
            if (listener != null)
                listener.onProfileMenuClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
