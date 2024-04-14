package com.example.computerstoreapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.computerstoreapp.R;
import com.example.computerstoreapp.database.Order;
import com.example.computerstoreapp.database.OrderDetail;
import com.example.computerstoreapp.database.OrderDetailDataSource;
import com.example.computerstoreapp.database.Product;
import com.example.computerstoreapp.database.ProductsDataSource;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context mContext;
    private List<Order> mListOrders;

    private OrderDetailDataSource orderDetailDataSource;
    private ProductsDataSource productsDataSource;

    public OrderAdapter(Context mContext) {
        this.mContext = mContext;
        this.mListOrders = new ArrayList<>();
    }

    public void setData(List<Order> orders) {
        this.mListOrders = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = mListOrders.get(position);

        if (order == null) {
            return;
        }

        holder.orderId.setText("Order ID: " + order.getOrderid());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String orderDateStr = dateFormat.format(order.getOrderdate().getTime());
        holder.orderDate.setText("Order Date: " + orderDateStr);

        holder.orderStatus.setText("Order Status: " + order.getStatus());
        holder.orderAddress.setText("Order Address: " + order.getAddress());

        holder.orderTotal.setText("Order Total: " + "1,000,000đ");
    }

    @Override
    public int getItemCount() {
        return mListOrders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView orderId;
        private TextView orderDate;
        private TextView orderStatus;
        private TextView orderAddress;
        private TextView orderTotal;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            orderId = itemView.findViewById(R.id.order_id);
            orderDate = itemView.findViewById(R.id.order_date);
            orderStatus = itemView.findViewById(R.id.order_status);
            orderAddress = itemView.findViewById(R.id.order_address);
            orderTotal = itemView.findViewById(R.id.order_total);
        }
    }
}
