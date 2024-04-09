package com.example.computerstoreapp.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

        setHasOptionsMenu(true);

        recyclerViewProducts = view.findViewById(R.id.user_recyclerview_products);

        productsAdapter = new ProductsAdapter(getActivity());

        displayProduct();

        recyclerViewProducts.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerViewProducts.setAdapter(productsAdapter);

        productsAdapter.setOnProductClickListener(new ProductsAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(Product product) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("product", product);

                UserProductDetailFragment fragment = new UserProductDetailFragment();
                fragment.setArguments(bundle);

                getChildFragmentManager().beginTransaction()
                        .replace(R.id.product_detail_frame, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_products, menu);
        super.onCreateOptionsMenu(menu, inflater);

        SearchManager searchManager = (SearchManager) requireActivity().getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productsAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}
