package com.example.computerstoreapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

    }

//    private void logout() {
//        UserSessionManager sessionManager = UserSessionManager.getInstance();
//        sessionManager.setLoggedIn(false);
//        sessionManager.setUsername("");
//        sessionManager.setUserid(-1);
//        sessionManager.setUserrole(-1);
//
//        // Chuyển hướng đến màn hình đăng nhập
//        startActivity(new Intent(UserActivity.this, LoginActivity.class));
//        finish(); // Đóng activity hiện tại
//    }
}