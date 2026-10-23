package com.chonsu.ecommerce.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.model.FeedbackMessage;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<FeedbackMessage> feedbackMessages;

    public FeedbackAdapter(ArrayList<FeedbackMessage> feedbackMessages) {
        this.feedbackMessages = feedbackMessages;
    }

    public static class FeedbackManagerViewHolder extends RecyclerView.ViewHolder {
        ImageView ivManagerAvatar;
        TextView tvManagerMessage;

        public FeedbackManagerViewHolder(@NonNull View itemView) {
            super(itemView);
            ivManagerAvatar = itemView.findViewById(R.id.avatar_manager);
            tvManagerMessage = itemView.findViewById(R.id.tvMessage_manager);
        }
    }

    public static class FeedbackUserViewHolder extends RecyclerView.ViewHolder {
        ImageView ivUserAvatar;
        TextView tvUserMessage;

        public FeedbackUserViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserAvatar = itemView.findViewById(R.id.avatar_user);
            tvUserMessage = itemView.findViewById(R.id.tvMessage_user);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if ("Manager".equals(feedbackMessages.get(position).getRole())) {
            return 1;
        } else {
            return 2;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback_manager, parent, false);
            return new FeedbackManagerViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback_user, parent, false);
            return new FeedbackUserViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == 1) {
            FeedbackManagerViewHolder viewHolder = (FeedbackManagerViewHolder) holder;
            viewHolder.itemView.setTag(feedbackMessages.get(position));
            viewHolder.ivManagerAvatar.setImageResource(R.drawable.ic_manager_avatar);
            viewHolder.tvManagerMessage.setText(feedbackMessages.get(position).getMessage());
        } else {
            FeedbackUserViewHolder viewHolder = (FeedbackUserViewHolder) holder;
            viewHolder.itemView.setTag(feedbackMessages.get(position));
            viewHolder.ivUserAvatar.setImageResource(R.drawable.img_avatar);
            viewHolder.tvUserMessage.setText(feedbackMessages.get(position).getMessage()); // Fixed missing text binding
        }
    }


    @Override
    public int getItemCount() {
        return feedbackMessages.size();
    }
}
