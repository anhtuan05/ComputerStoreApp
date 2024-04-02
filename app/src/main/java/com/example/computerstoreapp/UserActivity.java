package com.example.computerstoreapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {

    private Button btnLogout;
    private TextView tv1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        btnLogout = findViewById(R.id.action_logout);
        tv1 = findViewById(R.id.username_login);
        tv1.setText(UserSessionManager.getInstance().getUsername());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout() {
        UserSessionManager sessionManager = UserSessionManager.getInstance();
        sessionManager.setLoggedIn(false);
        sessionManager.setUsername("");
        sessionManager.setUserid(-1);
        sessionManager.setUserrole(-1);

        // Chuyển hướng đến màn hình đăng nhập
        startActivity(new Intent(UserActivity.this, LoginActivity.class));
        finish(); // Đóng activity hiện tại
    }
}