package com.example.computerstoreapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.computerstoreapp.R;
import com.example.computerstoreapp.UserSessionManager;
import com.example.computerstoreapp.adapter.CartAdapter;
import com.example.computerstoreapp.adapter.OrderAdapter;
import com.example.computerstoreapp.database.CategoriesDataSource;
import com.example.computerstoreapp.database.Order;
import com.example.computerstoreapp.database.OrderDataSource;
import com.example.computerstoreapp.database.OrderDetailDataSource;
import com.example.computerstoreapp.database.Product;
import com.example.computerstoreapp.database.ProductsDataSource;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class UserHistoryFragment extends Fragment {

    private RecyclerView recyclerViewOrder;
    private OrderDataSource orderDataSource;
    private OrderDetailDataSource orderDetailDataSource;
    private ProductsDataSource productsDataSource;
    private OrderAdapter orderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewOrder = view.findViewById(R.id.user_recyclerview_order);
        orderAdapter = new OrderAdapter(getActivity());

        orderDataSource = new OrderDataSource(getActivity());
        orderDataSource.open();
        List<Order> listOrder = new ArrayList<>();
        try {
            listOrder = orderDataSource.getOrdersByUserId(UserSessionManager.getInstance().getUserid());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        orderAdapter.setData(listOrder);
        recyclerViewOrder.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerViewOrder.setAdapter(orderAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (orderDataSource != null) {
            orderDataSource.close();
        }
        if (orderDetailDataSource != null) {
            orderDetailDataSource.close();
        }
        if (productsDataSource != null) {
            productsDataSource.close();
        }
    }
}
