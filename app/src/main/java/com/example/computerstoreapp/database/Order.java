package com.example.computerstoreapp.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Order {
    private int orderid;
    private int userid;
    private Calendar orderdate;
    private String address;
    private String status;

    public Order() {
    }

    public Order(int userid, String address, String status) {
        this.userid = userid;
        this.address = address;
        this.status = status;
        this.orderdate = Calendar.getInstance(); // Lấy ngày của hệ thống
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Calendar getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdateStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(orderdateStr)); // Phân tích chuỗi thành Calendar
        this.orderdate = calendar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }
}
