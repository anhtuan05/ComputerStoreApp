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
import com.example.computerstoreapp.adapter.ProductsAdapter;
import com.example.computerstoreapp.database.Product;
import com.example.computerstoreapp.database.ProductsDataSource;

import java.util.List;

public class UserHomeFragment extends Fragment {

    private RecyclerView recyclerViewProducts;
    private ProductsAdapter productsAdapter;

    private ProductsDataSource productsDataSource;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewProducts = view.findViewById(R.id.user_recyclerview_products);

        productsAdapter = new ProductsAdapter(getActivity());

        displayProduct();

        recyclerViewProducts.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerViewProducts.setAdapter(productsAdapter);
    }

    public void displayProduct() {
        productsDataSource = new ProductsDataSource(getActivity());
        productsDataSource.open();
        List<Product> productList = productsDataSource.getListALlProducts();
        if (productList != null) {
            productsAdapter.setData(productList);
        } else {

            Toast.makeText(getActivity(), "No Product", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        productsDataSource.close();
    }
}
