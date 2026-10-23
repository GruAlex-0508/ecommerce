package com.chonsu.ecommerce.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.adapter.FeedbackAdapter;
import com.chonsu.ecommerce.common.DisplayUtils;
import com.chonsu.ecommerce.config.ThemeManager;
import com.chonsu.ecommerce.databinding.ActivityFeedbackBinding;
import com.chonsu.ecommerce.model.FeedbackMessage;

import java.util.ArrayList;

public class FeedBackActivity extends BaseActivity implements View.OnClickListener {

    private ActivityFeedbackBinding binding;
    private ArrayList<FeedbackMessage> feedbackMessages;
    private FeedbackAdapter feedbackAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DisplayUtils.setStatusBarColor(this, Color.TRANSPARENT, false, true);
        DisplayUtils.setNavigationBarColor(this, ThemeManager.getThemeColor(this, R.attr.bgGradient2), true);

        feedbackMessages = new ArrayList<>();
        feedbackMessages.add(new FeedbackMessage("Manager", "fdlkfjsdlkfjsdcscdsacsdcsdcsdcsdcsdcdscsdcsdcsdcsdcsdcwcedclfjsdl"));
        feedbackMessages.add(new FeedbackMessage("Manager", "fdlkfjsdlkfjsdlfjsdl"));
        feedbackMessages.add(new FeedbackMessage("user", "fdlkfjsdlkfjsdlfjsdl"));
        feedbackMessages.add(new FeedbackMessage("Manager", "fdlkfjsdlkfjsdlfjsdl"));
        feedbackMessages.add(new FeedbackMessage("user", "fdlkfjsdlkfjsdlfjsdl"));
        feedbackMessages.add(new FeedbackMessage("Manager", "fdlkfjsdlkfjsdlfjsdl"));

        binding.feedbackList.setHasFixedSize(true);
        binding.feedbackList.setLayoutManager(new LinearLayoutManager(this));

        feedbackAdapter = new FeedbackAdapter(feedbackMessages);
        binding.feedbackList.setAdapter(feedbackAdapter);

        binding.sendButton.setOnClickListener(this);
        binding.backButton.setOnClickListener(this);
        binding.phoneButton.setOnClickListener(this);
        binding.micButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        String newMessage = binding.feedbackInput.getText().toString().trim();

        if (id == R.id.sendButton && !newMessage.isEmpty()) {
            newMessage = binding.feedbackInput.getText().toString().trim();
            feedbackMessages.add(new FeedbackMessage(getString(R.string.feedback_add_user), newMessage));

            feedbackAdapter.notifyItemInserted(feedbackMessages.size() - 1);

            binding.feedbackList.scrollToPosition(feedbackMessages.size() - 1);
            binding.feedbackInput.setText("");
        } else if (id == R.id.sendButton && newMessage.isEmpty()) {
            Toast.makeText(this, R.string.feedback_input_notification, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.backButton) {
            handleOnBackPressed();
        } else if (id == R.id.micButton) {
            Toast.makeText(this, R.string.feedback_mic_notification, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.phoneButton) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(getString(R.string.feedback_tel_number)));
            startActivity(intent);
        }
    }

    @Override
    protected void handleOnBackPressed() {
        finish();
    }
}
