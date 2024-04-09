package com.example.computerstoreapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderDataSource {

    private SQLiteDatabase database;

    private ComputerStoreSQLiteHelper dbHelper;

    private String[] allColumns = {ComputerStoreSQLiteHelper.COLUMN_ORDERID,
            ComputerStoreSQLiteHelper.COLUMN_USERID, ComputerStoreSQLiteHelper.COLUMN_ORDERDATE,
            ComputerStoreSQLiteHelper.COLUMN_ADDRESS, ComputerStoreSQLiteHelper.COLUMN_STATUS};

    public OrderDataSource(Context context) {
        dbHelper = new ComputerStoreSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createOrder(Order order) {

        ContentValues values = new ContentValues();
        values.put(ComputerStoreSQLiteHelper.COLUMN_USERID, order.getUserid());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String orderDateStr = dateFormat.format(order.getOrderdate().getTime());

        values.put(ComputerStoreSQLiteHelper.COLUMN_ORDERDATE, orderDateStr);
        values.put(ComputerStoreSQLiteHelper.COLUMN_ADDRESS, order.getAddress());
        values.put(ComputerStoreSQLiteHelper.COLUMN_STATUS, order.getStatus());

        long insertId = database.insert(ComputerStoreSQLiteHelper.TABLE_ORDERS, null, values);

        return insertId;
    }

    public List<Order> getOrdersByUserId(int userId) throws ParseException {
        List<Order> orders = new ArrayList<>();

        Cursor cursor = database.query(
                ComputerStoreSQLiteHelper.TABLE_ORDERS,
                allColumns,
                ComputerStoreSQLiteHelper.COLUMN_USERID + " = ?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                null
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Order order = cursorToOrder(cursor);
            orders.add(order);
            cursor.moveToNext();
        }
        cursor.close();
        return orders;
    }

    private Order cursorToOrder(Cursor cursor) throws ParseException {
        Order order = new Order();

        order.setOrderid((int) cursor.getLong(0));
        order.setUserid((int) cursor.getLong(1));

        String strDate = cursor.getString(2);
        order.setOrderdate(strDate);

        order.setAddress(cursor.getString(3));
        order.setStatus(cursor.getString(4));

        return order;
    }

}
