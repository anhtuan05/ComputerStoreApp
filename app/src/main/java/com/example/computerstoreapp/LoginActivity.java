package com.example.computerstoreapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.computerstoreapp.database.User;
import com.example.computerstoreapp.database.UserDataSource;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister;

    private UserDataSource userDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewRegister = findViewById(R.id.textViewRegister);

        userDataSource = new UserDataSource(this);
        userDataSource.open();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle login button click
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                // Perform login operation here

                User user = userDataSource.getUser(username, password);

                if (user != null) {
                    UserSessionManager sessionManager = UserSessionManager.getInstance();
                    sessionManager.setLoggedIn(true);
                    sessionManager.setUsername(user.getUsername());
                    sessionManager.setUserid(user.getUserid());
                    sessionManager.setUserrole(user.getUserrole());
                    sessionManager.setUsergmail(user.getEmail());

                    if (user.getUserrole() == 1) {
                        startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                    } else {
                        startActivity(new Intent(LoginActivity.this, UserActivity.class));
                    }

                    finish(); // Đóng activity đăng nhập
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Login unsuccessful. Please check your username and password again!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to register activity
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        userDataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        userDataSource.close();
        super.onPause();
    }
}