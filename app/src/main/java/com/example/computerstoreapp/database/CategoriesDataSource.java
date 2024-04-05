package com.example.computerstoreapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CategoriesDataSource {

    private SQLiteDatabase database;

    private ComputerStoreSQLiteHelper dbHelper;

    private String[] allColumns = {ComputerStoreSQLiteHelper.COLUMN_CATEGORIESID,
            ComputerStoreSQLiteHelper.COLUMN_NAME};

    public CategoriesDataSource(Context context) {
        dbHelper = new ComputerStoreSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long categoriesCreate(String name) {

        ContentValues value = new ContentValues();
        value.put(ComputerStoreSQLiteHelper.COLUMN_NAME, name);

        long insertId = database.insert(ComputerStoreSQLiteHelper.TABLE_CATEGORIES, null, value);

        return insertId;
    }
}
