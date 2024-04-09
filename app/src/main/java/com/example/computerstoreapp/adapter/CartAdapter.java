package com.example.computerstoreapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.computerstoreapp.R;
import com.example.computerstoreapp.database.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context mContext;
    private List<Product> mListProducts;

    public CartAdapter(Context mContext) {
        this.mContext = mContext;
        this.mListProducts = new ArrayList<>();
    }

    public void setData(List<Product> products) {
        this.mListProducts = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = mListProducts.get(position);

        if (product == null) {
            return;
        }
        int resourceId = mContext.getResources().getIdentifier(product.getImg(), "drawable", mContext.getPackageName());
        holder.imgProduct.setImageResource(resourceId);

        holder.txName.setText(product.getName());

        float price = product.getPrice() * 100000;
        DecimalFormat formatter = new DecimalFormat("#,###đ");
        String formattedPrice = formatter.format(price);
        holder.txPrice.setText(formattedPrice);

        holder.quantityEditText.setText(String.valueOf(product.getOrderQuantity()));

        holder.btnIncrease.setOnClickListener(v -> {
            if (holder.quantityEditText.getText().toString().equals("")) {
                holder.quantityEditText.setText("1");
                mListProducts.get(position).setOrderQuantity(1);
            } else {
                int currentQuantity = Integer.parseInt(holder.quantityEditText.getText().toString());
                currentQuantity++;
                holder.quantityEditText.setText(String.valueOf(currentQuantity));
                mListProducts.get(position).setOrderQuantity(currentQuantity);
            }
        });

        holder.btnDecrease.setOnClickListener(v -> {
            if (holder.quantityEditText.getText().toString().equals("")) {
                holder.quantityEditText.setText("1");
                mListProducts.get(position).setOrderQuantity(1);
            } else {
                int currentQuantity = Integer.parseInt(holder.quantityEditText.getText().toString());
                if (currentQuantity >= 1) {
                    currentQuantity--;
                    if (currentQuantity == 0) {
                        int position1 = holder.getAdapterPosition();
                        mListProducts.remove(position1);
                        notifyItemRemoved(position1);
                        notifyItemRangeChanged(position1, mListProducts.size());
                    } else {
                        holder.quantityEditText.setText(String.valueOf(currentQuantity));
                        mListProducts.get(position).setOrderQuantity(currentQuantity);
                    }
                }
            }
        });
    }

    public List<Product> getProducts() {
        return mListProducts;
    }


    @Override
    public int getItemCount() {
        if (mListProducts != null) {
            return mListProducts.size();
        }
        return 0;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduct;
        private TextView txName;
        private TextView txPrice;

        private Button btnDecrease;
        private EditText quantityEditText;
        private Button btnIncrease;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.img_product_cart);
            txName = itemView.findViewById(R.id.name_product_cart);
            txPrice = itemView.findViewById(R.id.price_product_cart);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            quantityEditText = itemView.findViewById(R.id.quantityEditText);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
        }
    }
}
