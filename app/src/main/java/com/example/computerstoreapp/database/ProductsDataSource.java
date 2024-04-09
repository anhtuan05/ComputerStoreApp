package com.example.computerstoreapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProductsDataSource {
    private SQLiteDatabase database;

    private ComputerStoreSQLiteHelper dbHelper;

    private String[] allColumns = {ComputerStoreSQLiteHelper.COLUMN_PRODUCTID,
            ComputerStoreSQLiteHelper.COLUMN_NAME, ComputerStoreSQLiteHelper.COLUMN_DESCRIPTION,
            ComputerStoreSQLiteHelper.COLUMN_PRICE, ComputerStoreSQLiteHelper.COLUMN_QUANTITY,
            ComputerStoreSQLiteHelper.COLUMN_ACTIVE, ComputerStoreSQLiteHelper.COLUMN_IMG,
            ComputerStoreSQLiteHelper.COLUMN_CATEGORIESID};

    public ProductsDataSource(Context context) {
        dbHelper = new ComputerStoreSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    //add product
    public long productCreate(Product product) {

        ContentValues values = new ContentValues();
        values.put(ComputerStoreSQLiteHelper.COLUMN_NAME, product.getName());
        values.put(ComputerStoreSQLiteHelper.COLUMN_DESCRIPTION, product.getDescription());
        values.put(ComputerStoreSQLiteHelper.COLUMN_PRICE, product.getPrice());
        values.put(ComputerStoreSQLiteHelper.COLUMN_QUANTITY, product.getQuantity());
        values.put(ComputerStoreSQLiteHelper.COLUMN_ACTIVE, product.getActive());
        values.put(ComputerStoreSQLiteHelper.COLUMN_IMG, product.getImg());
        values.put(ComputerStoreSQLiteHelper.COLUMN_CATEGORIESID, product.getCategoriesid());

        long insertId = database.insert(ComputerStoreSQLiteHelper.TABLE_PRODUCTS, null, values);

        return insertId;
    }

    public void updateProductQuantity(int productId, int newQuantity) {
        ContentValues values = new ContentValues();
        values.put(ComputerStoreSQLiteHelper.COLUMN_QUANTITY, newQuantity);

        String selection = ComputerStoreSQLiteHelper.COLUMN_PRODUCTID + " = ?";
        String[] selectionArgs = {String.valueOf(productId)};

        database.update(ComputerStoreSQLiteHelper.TABLE_PRODUCTS, values, selection, selectionArgs);
    }

    public List<Product> getListALlProducts() {
        List<Product> products = new ArrayList<>();
        Cursor cursor = database.query(ComputerStoreSQLiteHelper.TABLE_PRODUCTS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {

                Product product = cursorToProduct(cursor);
                products.add(product);

            } while (cursor.moveToNext());
            cursor.close();

        }
        return products;
    }

    private Product cursorToProduct(Cursor cursor) {
        Product product = new Product();
        product.setProductid((int) cursor.getLong(0));
        product.setName(cursor.getString(1));
        product.setDescription(cursor.getString(2));
        product.setPrice(cursor.getFloat(3));
        product.setQuantity(cursor.getInt(4));
        product.setActive(cursor.getInt(5));
        product.setImg(cursor.getString(6));
        product.setCategoriesid(cursor.getInt(7));

        return product;
    }
}
