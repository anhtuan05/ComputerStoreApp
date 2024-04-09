package com.example.computerstoreapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.computerstoreapp.R;
import com.example.computerstoreapp.UserSessionManager;
import com.example.computerstoreapp.adapter.CartAdapter;
import com.example.computerstoreapp.adapter.ProductsAdapter;
import com.example.computerstoreapp.database.Order;
import com.example.computerstoreapp.database.OrderDataSource;
import com.example.computerstoreapp.database.OrderDetail;
import com.example.computerstoreapp.database.OrderDetailDataSource;
import com.example.computerstoreapp.database.Product;
import com.example.computerstoreapp.database.ProductsDataSource;

import java.util.ArrayList;
import java.util.List;

public class UserCartFragment extends Fragment {

    private RecyclerView recyclerViewCart;
    private CartAdapter cartAdapter;

    private OrderDataSource orderDataSource;

    private OrderDetailDataSource orderDetailDataSource;

    private ProductsDataSource productsDataSource;
    private List<Product> productList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewCart = view.findViewById(R.id.user_recyclerview_products_cart);
        cartAdapter = new CartAdapter(getActivity());
        LinearLayout layout_order = view.findViewById(R.id.layout_order);

        Bundle bundle = getArguments();

        if (bundle != null && bundle.containsKey("product_add_to_cart")) {
            Product product = bundle.getParcelable("product_add_to_cart");

            if (product != null) {
                // Hiển thị sản phẩm trong giỏ hàng

                if (!productList.contains(product)) {
                    productList.add(product);
                }

                cartAdapter.setData(productList);
                recyclerViewCart.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                recyclerViewCart.setAdapter(cartAdapter);
            }
        } else {
            Toast.makeText(getContext(), "No Product", Toast.LENGTH_SHORT).show();
        }

        productsDataSource = new ProductsDataSource(getActivity());
        productsDataSource.open();

        Button btnOrder = view.findViewById(R.id.btn_order);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Product> productsDB = productsDataSource.getListALlProducts();

                List<Product> productListGetAdapter = cartAdapter.getProducts();
                boolean FlagQuantity = true;
                if (productsDB != null) {
                    for (Product p : productListGetAdapter) {
                        int quantityDB = 0;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            quantityDB = productsDB.stream().filter(product -> product.getProductid() == p.getProductid())
                                    .mapToInt(Product::getQuantity).findFirst().orElse(0);
                        }
                        if (quantityDB < p.getOrderQuantity()) {
                            FlagQuantity = false;
                            Toast.makeText(getContext(), p.getName() + "is not enough", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
                boolean finalFlagQuantity = FlagQuantity;

                EditText editTextOrder = view.findViewById(R.id.address_order);
                String addressGetEditText = editTextOrder.getText().toString();

                if (addressGetEditText.trim().isEmpty()) {
                    Toast.makeText(getContext(), "No Address", Toast.LENGTH_SHORT).show();
                } else if (finalFlagQuantity && productListGetAdapter.size() >= 1) {

                    orderDataSource = new OrderDataSource(getActivity());
                    orderDataSource.open();

                    orderDetailDataSource = new OrderDetailDataSource(getActivity());
                    orderDetailDataSource.open();

                    Order order = new Order(UserSessionManager.getInstance().getUserid(),
                            addressGetEditText, "Order Success");

                    long orderIdNew = orderDataSource.createOrder(order);

                    if (orderIdNew > 0) {

                        for (Product product : productListGetAdapter) {
                            int quantityDB = 0;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                quantityDB = productsDB.stream().filter(product1 -> product1.getProductid() == product.getProductid())
                                        .mapToInt(Product::getQuantity).findFirst().orElse(0);
                            }

                            OrderDetail orderDetail = new OrderDetail((int) orderIdNew,
                                    product.getProductid(), product.getOrderQuantity());

                            long newOrderDetail = orderDetailDataSource.createOrderDetail(orderDetail);
                            if (newOrderDetail > 0) {
                                productsDataSource.updateProductQuantity(product.getProductid(),
                                        (quantityDB - product.getOrderQuantity()));
                            }
                        }

                        Toast.makeText(getContext(), "Successfully ordered " +
                                productListGetAdapter.size() + " products", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Order Failed", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "No Product", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
