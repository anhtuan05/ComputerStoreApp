package com.example.computerstoreapp.database;

public class OrderDetail {
    private int orderid;
    private int productid;
    private int quantity;

    public OrderDetail(int orderid, int productid, int quantity) {
        this.orderid = orderid;
        this.productid = productid;
        this.quantity = quantity;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
