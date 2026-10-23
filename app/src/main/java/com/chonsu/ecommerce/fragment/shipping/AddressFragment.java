package com.chonsu.ecommerce.fragment.shipping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chonsu.ecommerce.R;
import com.chonsu.ecommerce.activity.ShippingActivity;
import com.chonsu.ecommerce.adapter.ShippingAddressAdapter;
import com.chonsu.ecommerce.databinding.FragmentShippingAddressBinding;
import com.chonsu.ecommerce.model.ShippingAddress;

import java.util.ArrayList;

public class AddressFragment extends Fragment implements View.OnClickListener {

    private FragmentShippingAddressBinding binding;
    public static ArrayList<ShippingAddress> addresses;
    private ShippingAddressAdapter addressAdapter;
    private int count = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentShippingAddressBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addresses = new ArrayList<ShippingAddress>();

        addresses.add(new ShippingAddress(1, "Home", "1901 Preston Rd. Inglewood,Maine 98380"));
        addresses.add(new ShippingAddress(2, "das9dasd90", "afadfasdfwef"));
        addresses.add(new ShippingAddress(3, "vdvevevrevr", "FWEFWEFWEFWEWFEWFEWF"));
        addresses.add(new ShippingAddress(4, "12e1eqqwdc", "ERWGEGRGERGERGERGRE"));
        addresses.add(new ShippingAddress(5, "dfsfsdfsdfsd", "ergergergreg"));
        addresses.add(new ShippingAddress(6, "das9dasd90", "afadfasdfwef"));
        addresses.add(new ShippingAddress(7, "vdvevevrevr", "FWEFWEFWEFWEWFEWFEWF"));
        addresses.add(new ShippingAddress(8, "12e1eqqwdc", "ERWGEGRGERGERGERGRE"));
        addresses.add(new ShippingAddress(9, "dfsfsdfsdfsd", "ergergergreg"));
        addresses.add(new ShippingAddress(10, "das9dasd90", "afadfasdfwef"));
        addresses.add(new ShippingAddress(11, "vdvevevrevr", "FWEFWEFWEFWEWFEWFEWF"));
        addresses.add(new ShippingAddress(12, "12e1eqqwdc", "ERWGEGRGERGERGERGRE"));

        count = 12;
        binding.shippingNew.setOnClickListener(view1 -> {
            AddShippingAddressDialog dialog = new AddShippingAddressDialog(requireContext(), (title, address) -> {
                addShippingAddress(++count, title, address);
            });

            dialog.show();
        });

        RecyclerView recyclerView = view.findViewById(R.id.address_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        ShippingActivity activity = (ShippingActivity) requireActivity();
        addressAdapter = new ShippingAddressAdapter(activity, addresses);
        recyclerView.setAdapter(addressAdapter);
    }

    private void addShippingAddress(int cnt, String title, String address) {
        ShippingAddress shippingAddress = new ShippingAddress(cnt, title, address);
        addresses.add(shippingAddress);
        addressAdapter.notifyItemInserted(addresses.size() - 1);
    }

    @Override
    public void onClick(View view) {

    }
}

