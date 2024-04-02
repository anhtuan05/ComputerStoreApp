package com.example.computerstoreapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserDataSource {
    // Database fields
    private SQLiteDatabase database;

    private ComputerStoreSQLiteHelper dbHelper;
    private String[] allColumns = {ComputerStoreSQLiteHelper.COLUMN_USERID, ComputerStoreSQLiteHelper.COLUMN_USERNAME, ComputerStoreSQLiteHelper.COLUMN_PASSWORD, ComputerStoreSQLiteHelper.COLUMN_EMAIL, ComputerStoreSQLiteHelper.COLUMN_NUMBERPHONE, ComputerStoreSQLiteHelper.COLUMN_USERROLE};

    public UserDataSource(Context context) {
        dbHelper = new ComputerStoreSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long userCreate(String username, String password, String email, String numberphone) {

        if (isUsernameExists(username)) {
            return -1;
        } else {
            String hashedPassword = hashPassword(password);

            ContentValues values = new ContentValues();
            values.put(ComputerStoreSQLiteHelper.COLUMN_USERNAME, username);
            values.put(ComputerStoreSQLiteHelper.COLUMN_PASSWORD, hashedPassword);
            values.put(ComputerStoreSQLiteHelper.COLUMN_EMAIL, email);
            values.put(ComputerStoreSQLiteHelper.COLUMN_NUMBERPHONE, numberphone);
            values.put(ComputerStoreSQLiteHelper.COLUMN_USERROLE, 500);//phân quyền cho user bình thường

            long insertId = database.insert(ComputerStoreSQLiteHelper.TABLE_USER, null, values);

            return insertId;
        }
    }

    public User getUser(String username, String password) {

        String hashedPassword = hashPassword(password);

        Cursor cursor = database.query(ComputerStoreSQLiteHelper.TABLE_USER, allColumns,
                ComputerStoreSQLiteHelper.COLUMN_USERNAME + " = ? AND " +
                        ComputerStoreSQLiteHelper.COLUMN_PASSWORD + " = ? ",
                new String[]{username, hashedPassword}, null, null, null);
        if (cursor.moveToFirst()) {
            User user = cursorToUser(cursor);
            cursor.close();
            return user;
        } else {
            cursor.close();
            return null;
        }
    }

    private User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setUserid((int) cursor.getLong(0));
        user.setUsername(cursor.getString(1));
        //bỏ qua password
        user.setEmail(cursor.getString(3));
        user.setNumberphone(cursor.getString(4));
        user.setUserrole(Integer.parseInt(cursor.getString(5)));
        return user;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isUsernameExists(String username) {
        Cursor cursor = database.query(ComputerStoreSQLiteHelper.TABLE_USER, new String[]{ComputerStoreSQLiteHelper.COLUMN_USERNAME}, ComputerStoreSQLiteHelper.COLUMN_USERNAME + " = ?", new String[]{username}, null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
