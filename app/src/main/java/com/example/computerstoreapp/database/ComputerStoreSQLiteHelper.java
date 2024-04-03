package com.example.computerstoreapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ComputerStoreSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "computer_store_app";
    private static final int DATABASE_VERSION = 1;

    // Tên các bảng
    public static final String TABLE_USER = "User";
    public static final String TABLE_PRODUCTS = "Products";
    public static final String TABLE_CATEGORIES = "Categories";
    public static final String TABLE_ORDERS = "Orders";
    public static final String TABLE_ORDER_DETAIL = "OrderDetail";
    public static final String TABLE_REVIEW = "Review";

    // Tên cột cho bảng User
    public static final String COLUMN_USERID = "userid";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_NUMBERPHONE = "numberphone";
    public static final String COLUMN_USERROLE = "userrole";

    // Tên cột cho bảng Products
    public static final String COLUMN_PRODUCTID = "productid";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_ACTIVE = "active";
    public static final String COLUMN_IMG = "img";

    // Tên cột cho bảng Categories
    public static final String COLUMN_CATEGORIESID = "categoriesid";

    // Tên cột cho bảng Orders
    public static final String COLUMN_ORDERID = "orderid";
    public static final String COLUMN_ORDERDATE = "orderdate";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_ADDRESS = "address";

    // Tên cột cho bảng OrderDetail

    //Tên cột bảng Review
    public static final String COLUMN_REVIEWID = "reviewid";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_COMMENT = "comment";

    // Mảng chứa tên các cột cho từng bảng
    private static final String[][] COLUMNS = {
            {COLUMN_USERID, COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_EMAIL, COLUMN_NUMBERPHONE, COLUMN_USERROLE}, // Cột cho bảng User
            {COLUMN_PRODUCTID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_PRICE, COLUMN_QUANTITY, COLUMN_ACTIVE, COLUMN_IMG, COLUMN_CATEGORIESID}, // Cột cho bảng Products
            {COLUMN_CATEGORIESID, COLUMN_NAME}, // Cột cho bảng Categories
            {COLUMN_ORDERID, COLUMN_USERID, COLUMN_ORDERDATE, COLUMN_ADDRESS,COLUMN_STATUS}, // Cột cho bảng Orders
            {COLUMN_ORDERID, COLUMN_PRODUCTID, COLUMN_QUANTITY}, // Cột cho bảng OrderDetail
            {COLUMN_REVIEWID, COLUMN_PRODUCTID, COLUMN_USERID, COLUMN_RATING, COLUMN_COMMENT} // Cột cho bảng Review
    };

    // Câu lệnh SQL để tạo bảng User
    private static final String SQL_CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " (" +
            COLUMNS[0][0] + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMNS[0][1] + " TEXT," +
            COLUMNS[0][2] + " TEXT," +
            COLUMNS[0][3] + " TEXT," +
            COLUMNS[0][4] + " TEXT," +
            COLUMNS[0][5] + " INTEGER)";

    // Câu lệnh SQL để tạo bảng Products
    private static final String SQL_CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
            COLUMNS[1][0] + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMNS[1][1] + " TEXT," +
            COLUMNS[1][2] + " TEXT," +
            COLUMNS[1][3] + " REAL," +
            COLUMNS[1][4] + " INTEGER," +
            COLUMNS[1][5] + " INTEGER," +
            COLUMNS[1][6] + " TEXT," +
            COLUMNS[1][7] + " INTEGER," +
            "FOREIGN KEY (" + COLUMNS[1][7] + ") REFERENCES " + TABLE_CATEGORIES + "(" + COLUMN_CATEGORIESID + "))";

    // Câu lệnh SQL để tạo bảng Categories
    private static final String SQL_CREATE_TABLE_CATEGORIES = "CREATE TABLE " + TABLE_CATEGORIES + " (" +
            COLUMNS[2][0] + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMNS[2][1] + " TEXT)";

    // Câu lệnh SQL để tạo bảng Orders
    private static final String SQL_CREATE_TABLE_ORDERS = "CREATE TABLE " + TABLE_ORDERS + " (" +
            COLUMNS[3][0] + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMNS[3][1] + " INTEGER," +
            COLUMNS[3][2] + " TEXT," +
            COLUMNS[3][3] + " TEXT," +
            COLUMNS[3][4] + " TEXT," +
            "FOREIGN KEY (" + COLUMNS[3][1] + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USERID + "))";

    // Câu lệnh SQL để tạo bảng OrderDetail
    private static final String SQL_CREATE_TABLE_ORDER_DETAIL = "CREATE TABLE " + TABLE_ORDER_DETAIL + " (" +
            COLUMNS[4][0] + " INTEGER," +
            COLUMNS[4][1] + " INTEGER," +
            COLUMNS[4][2] + " INTEGER," +
            "PRIMARY KEY (" + COLUMNS[4][0] + ", " + COLUMNS[4][1] + ")," +
            "FOREIGN KEY (" + COLUMNS[4][0] + ") REFERENCES " + TABLE_ORDERS + "(" + COLUMN_ORDERID + ")," +
            "FOREIGN KEY (" + COLUMNS[4][1] + ") REFERENCES " + TABLE_PRODUCTS + "(" + COLUMN_PRODUCTID + "))";

    // Câu lệnh SQL để tạo bảng Review
    private static final String SQL_CREATE_TABLE_REVIEW = "CREATE TABLE " + TABLE_REVIEW + " (" +
            COLUMNS[5][0] + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMNS[5][1] + " INTEGER," +
            COLUMNS[5][2] + " INTEGER," +
            COLUMNS[5][3] + " INTEGER," +
            COLUMNS[5][4] + " TEXT," +
            "FOREIGN KEY (" + COLUMNS[5][1] + ") REFERENCES " + TABLE_PRODUCTS + "(" + COLUMN_PRODUCTID + ")," +
            "FOREIGN KEY (" + COLUMNS[5][2] + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USERID + "))";


    public ComputerStoreSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo các bảng khi cơ sở dữ liệu được tạo lần đầu tiên
        db.execSQL(SQL_CREATE_TABLE_USER);
        db.execSQL(SQL_CREATE_TABLE_PRODUCTS);
        db.execSQL(SQL_CREATE_TABLE_CATEGORIES);
        db.execSQL(SQL_CREATE_TABLE_ORDERS);
        db.execSQL(SQL_CREATE_TABLE_ORDER_DETAIL);
        db.execSQL(SQL_CREATE_TABLE_REVIEW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa và tạo lại các bảng khi có sự thay đổi version của cơ sở dữ liệu
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEW);
        onCreate(db);
    }

}
