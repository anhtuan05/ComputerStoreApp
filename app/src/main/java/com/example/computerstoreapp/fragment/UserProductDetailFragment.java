package com.example.computerstoreapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.computerstoreapp.R;
import com.example.computerstoreapp.database.Product;

import java.text.DecimalFormat;

public class UserProductDetailFragment extends Fragment {

    public interface OnAddToCartListener {
        void onAddToCart(Product product);
    }

    private OnAddToCartListener addToCartListener;
    private ImageView img;
    private TextView name;
    private TextView price;
    private TextView description;

    private Button addToCart;

    public UserProductDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_detail, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        img = view.findViewById(R.id.img_product_detail);
        name = view.findViewById(R.id.name_product_detail);
        price = view.findViewById(R.id.price_product_detail);
        description = view.findViewById(R.id.description_product_detail);

        Button backButton = view.findViewById(R.id.btn_back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại Fragment cha
                getParentFragmentManager().popBackStack();
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            Product product = bundle.getParcelable("product");
            if (product != null) {
                // Hiển thị thông tin sản phẩm lên giao diện người dùng
                displayProductDetails(view.getContext(), product);

                addToCart = view.findViewById(R.id.btn_add_to_cart);
                addToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (addToCartListener != null) {
                            addToCartListener.onAddToCart(product);
                        }
                    }
                });
            }
        }


    }

    private void displayProductDetails(Context context, Product product) {
        int resourceId = context.getResources().getIdentifier(product.getImg(), "drawable", context.getPackageName());
        img.setImageResource(resourceId);

        name.setText(product.getName());

        float price_t = product.getPrice() * 100000;
        DecimalFormat formatter = new DecimalFormat("#,###đ");
        String formattedPrice = formatter.format(price_t);
        price.setText(formattedPrice);

        description.setText(product.getDescription());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            addToCartListener = (OnAddToCartListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnAddToCartListener");
        }

    }
}
