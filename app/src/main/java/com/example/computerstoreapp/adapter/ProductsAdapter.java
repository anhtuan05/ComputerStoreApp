package com.example.computerstoreapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.computerstoreapp.R;
import com.example.computerstoreapp.database.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> implements Filterable {

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    private Context mContext;
    private List<Product> mListProducts;
    private List<Product> mListProductsOld;

    private OnProductClickListener onProductClickListener;

    public void setOnProductClickListener(OnProductClickListener listener) {
        this.onProductClickListener = listener;
    }


    public ProductsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Product> products) {
        this.mListProducts = products;
        this.mListProductsOld = products;
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onProductClickListener != null) {
                    onProductClickListener.onProductClick(product);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListProducts != null) {
            return mListProducts.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();

                if (strSearch.isEmpty()) {
                    mListProducts = mListProductsOld;
                } else {
                    List<Product> products = new ArrayList<>();
                    for (Product product : mListProductsOld) {
                        if (product.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                            products.add(product);
                        }
                    }

                    mListProducts = products;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListProducts;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListProducts = (List<Product>) results.values;
                notifyDataSetChanged();
            }
        };
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
