package com.example.computerstoreapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class OrderDetailDataSource {
    private SQLiteDatabase database;

    private ComputerStoreSQLiteHelper dbHelper;

    private String[] allColumns = {ComputerStoreSQLiteHelper.COLUMN_ORDERID,
            ComputerStoreSQLiteHelper.COLUMN_PRODUCTID, ComputerStoreSQLiteHelper.COLUMN_QUANTITY
    };

    public OrderDetailDataSource(Context context) {
        dbHelper = new ComputerStoreSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createOrderDetail(OrderDetail orderDetail) {

        ContentValues values = new ContentValues();
        values.put(ComputerStoreSQLiteHelper.COLUMN_ORDERID, orderDetail.getOrderid());
        values.put(ComputerStoreSQLiteHelper.COLUMN_PRODUCTID, orderDetail.getProductid());
        values.put(ComputerStoreSQLiteHelper.COLUMN_QUANTITY, orderDetail.getQuantity());


        long insertId = database.insert(ComputerStoreSQLiteHelper.TABLE_ORDER_DETAIL, null, values);

        return insertId;
    }

}
