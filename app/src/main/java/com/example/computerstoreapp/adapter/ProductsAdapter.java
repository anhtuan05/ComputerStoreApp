package com.example.computerstoreapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.computerstoreapp.R;
import com.example.computerstoreapp.database.Product;

import java.text.DecimalFormat;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private Context mContext;
    private List<Product> mListProducts;

    public ProductsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Product> products) {
        this.mListProducts = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
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
    }

    @Override
    public int getItemCount() {
        if (mListProducts != null) {
            return mListProducts.size();
        }
        return 0;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduct;
        private TextView txName;
        private TextView txPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.img_product);
            txName = itemView.findViewById(R.id.name_product);
            txPrice = itemView.findViewById(R.id.price_product);
        }
    }
}
