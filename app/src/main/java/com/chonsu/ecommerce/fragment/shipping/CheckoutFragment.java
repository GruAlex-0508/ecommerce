package com.chonsu.ecommerce.fragment.shipping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.activity.ShippingActivity;
import com.chonsu.ecommerce.adapter.ShippingOrderAdapter;
import com.chonsu.ecommerce.databinding.FragmentShippingCheckoutBinding;
import com.chonsu.ecommerce.model.ShippingProduct;

import java.util.ArrayList;

public class CheckoutFragment extends Fragment implements View.OnClickListener {

    private FragmentShippingCheckoutBinding binding;
    public static ArrayList<ShippingProduct> orderProducts;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        orderProducts = new ArrayList<>();

        orderProducts.add(new ShippingProduct("dfsfsdfsdfsd", "ergergergreg", "$160", "5", "order_first"));
        orderProducts.add(new ShippingProduct("dfsfsdfsdfsd", "ergergergreg", "$50", "3", "order_second"));
        orderProducts.add(new ShippingProduct("dfsfsdfsdfsd", "ergergergreg", "$340", "6", "order_third"));
        orderProducts.add(new ShippingProduct("dfsfsdfsdfsd", "ergergergreg", "$40", "68", "order_fourth"));
        orderProducts.add(new ShippingProduct("dfsfsdfsdfsd", "ergergergreg", "$540", "58", "order_fourth"));

        binding = FragmentShippingCheckoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.checkoutChangeLocation.setOnClickListener(this);
        binding.checkoutChangeType.setOnClickListener(this);

        RecyclerView recyclerView = binding.orderList;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        ShippingOrderAdapter shippingOrderAdapter = new ShippingOrderAdapter(orderProducts);
        recyclerView.setAdapter(shippingOrderAdapter);

        ShippingActivity activity = (ShippingActivity) requireActivity();
        binding.shippingType.setText(activity.getShippingType());
        binding.shippingAddress.setText(activity.getShippingAddressName());
        binding.shippingLocation.setText(activity.getShippingLocation());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        ShippingActivity activity = (ShippingActivity) requireActivity();

        if (id == R.id.checkout_change_location) {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.shipping_frag, new AddressFragment()).commit();
            activity.setShippingInfo(R.string.shipping_address, R.string.shipping_btn_continue);
            activity.setPosition(1);
            activity.setForwardVisible();
        } else if (id == R.id.checkout_change_type) {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.shipping_frag, new TypeFragment()).commit();
            activity.setShippingInfo(R.string.shipping_type, R.string.shipping_btn_continue);
            activity.setPosition(2);
            activity.setForwardVisible();
        }
        // ====================================================================================================================
    }
}