package com.chonsu.ecommerce.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chonsu.ecommerce.model.CartItem;
import com.chonsu.ecommerce.databinding.ItemCartBinding;

import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private final Context context;
    private final List<CartItem> cartItems;
    private final CartListener listener;

    private RecyclerView recyclerView;
    private int openPosition = RecyclerView.NO_POSITION;
    private float downX;
    private float downY;

    private static final float DELETE_WIDTH = 76f;
    private float startTranslationX;

    public CartAdapter(Context context, List<CartItem> cartItems, CartListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
        openPosition = RecyclerView.NO_POSITION;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartBinding binding = ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CartViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        android.util.Log.d(
                "CartAdapter",
                "onBindViewHolder position = " + position
        );
        CartItem item = cartItems.get(position);
        holder.binding.imgProduct.setImageResource(item.getImageResId());
        holder.binding.tvProductName.setText(item.getName());
        holder.binding.tvProductSize.setText(String.format("Size : %s", item.getSize()));
        holder.binding.tvProductPrice.setText(String.format(Locale.US, "$%.0f", item.getPrice()));
        holder.binding.tvQuantity.setText(String.valueOf(item.getQuantity()));

        holder.binding.layoutForeground.setTranslationX(0f);
        setupSwipe(holder);
        setupQuantityButtons(holder, item);

        holder.binding.btnDelete.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();

            if (adapterPosition == RecyclerView.NO_POSITION) return;

            CartItem temp = cartItems.get(adapterPosition);
            listener.onDeleteRequested(temp);
        });
    }

    private void setupSwipe(CartViewHolder holder) {
        holder.binding.layoutForeground.setOnTouchListener((view, event) -> onTouch(holder, view, event));
    }

    private void animateOpen(View view, int position) {
        if (openPosition != RecyclerView.NO_POSITION && openPosition != position) {
            closeOpenItem();
        }
        openPosition = position;
        animateTranslation(view, view.getTranslationX(), -dpToPx(DELETE_WIDTH), null);
    }

    private void animateClose(View view, int position) {
        if (openPosition == position) openPosition = RecyclerView.NO_POSITION;
        animateTranslation(view, view.getTranslationX(), 0f, null);
    }

    private void closeOpenItem() {
        if (openPosition == RecyclerView.NO_POSITION) return;
        if (recyclerView == null) {
            openPosition = RecyclerView.NO_POSITION;
            return;
        }
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(openPosition);

        if (viewHolder instanceof CartViewHolder) {
            CartViewHolder cartViewHolder = (CartViewHolder) viewHolder;

            View foreground = cartViewHolder.binding.layoutForeground;
            animateTranslation(foreground, foreground.getTranslationX(), 0f, null);
            openPosition = RecyclerView.NO_POSITION;
        }
    }

    private void animateTranslation(View view, float from, float to, Runnable onEnd) {
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        animator.setDuration(180);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            float val = (float) animation.getAnimatedValue();
            view.setTranslationX(val);
        });

        if (onEnd != null) {
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    onEnd.run();
                }
            });
        }

        animator.start();
    }


    public void removeItem(CartItem item) {
        int position = cartItems.indexOf(item);
        if (position == -1) return;

        if (openPosition == position) openPosition = RecyclerView.NO_POSITION;
        else if (openPosition > position) {
            openPosition--;
        }
        cartItems.remove(position);
        notifyItemRemoved(position);
    }

    private void setupQuantityButtons(CartViewHolder holder, CartItem item) {
        holder.binding.btnMinus.setOnClickListener(v -> {
            int quantity = item.getQuantity();

            if (quantity > 1) {
                quantity --;
                item.setQuantity(quantity);
                holder.binding.tvQuantity.setText(String.valueOf(quantity));
                listener.onQuantityChanged(item, quantity);
            }
        });

        holder.binding.btnPlus.setOnClickListener(v -> {
            int quantity = item.getQuantity();
            quantity++;
            item.setQuantity(quantity);
            holder.binding.tvQuantity.setText(String.valueOf(quantity));
            listener.onQuantityChanged(item, quantity);
        });
    }

    private float dpToPx(float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    @Override
    public int getItemCount() {
        int count = cartItems.size();

        android.util.Log.d(
                "CartAdapter",
                "getItemCount = " + count
        );

        return count;
    }

    private boolean onTouch(CartViewHolder holder, View view, MotionEvent event) {
        int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition == RecyclerView.NO_POSITION) return false;

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                startTranslationX = view.getTranslationX();
                if (openPosition != RecyclerView.NO_POSITION && openPosition != adapterPosition) closeOpenItem();
                return true;

            case MotionEvent.ACTION_MOVE:
                float moveX = event.getRawX() - downX;
                float moveY = event.getRawY() - downY;

                if (Math.abs(moveY) > Math.abs(moveX)) return false;

                float newTranslation = startTranslationX + moveX;
                newTranslation = Math.min(newTranslation, 0f);

                newTranslation = Math.max(newTranslation, -dpToPx(DELETE_WIDTH));
                view.setTranslationX(newTranslation);
                return true;

            case MotionEvent.ACTION_UP:
                float currentTranslation = view.getTranslationX();
                if (currentTranslation < -dpToPx(DELETE_WIDTH / 2f)) animateOpen(view, adapterPosition);
                else animateClose(view, adapterPosition);

                return true;

            case MotionEvent.ACTION_CANCEL:
                float translation = view.getTranslationX();
                if (translation < -dpToPx(DELETE_WIDTH / 2f)) animateOpen(view, adapterPosition);
                else animateClose(view, adapterPosition);
                return true;
        }
        return true;
    }

    public void closeSwipe(CartItem item) {
        if (item == null) return;

        int position = cartItems.indexOf(item);
        if (position == -1) return;

        if (openPosition != position) return;

        if (recyclerView == null) {
            openPosition = RecyclerView.NO_POSITION;
            return;
        }

        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);

        if (viewHolder instanceof CartViewHolder) {
            CartViewHolder cartViewHolder = (CartViewHolder) viewHolder;
            View foreground = cartViewHolder.binding.layoutForeground;
            foreground.animate().cancel();

            animateTranslation(foreground, foreground.getTranslationX(), 0f, () -> {
                openPosition = RecyclerView.NO_POSITION;
                foreground.setTranslationX(0f);
            });
        } else {
            openPosition = RecyclerView.NO_POSITION;
        }
    }


    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ItemCartBinding binding;
        public CartViewHolder(ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface CartListener {
        void onQuantityChanged(CartItem item, int quantity);
        void onDeleteRequested(CartItem item);
    }
}
