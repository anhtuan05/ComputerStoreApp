package com.example.computerstoreapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.computerstoreapp.database.UserDataSource;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextNewUsername, editTextNewPassword, editTextNewConfirmPassword, editTextNewEmail, editTextNewPhoneNumber;
    private Button buttonRegister;

    private UserDataSource userDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextNewUsername = findViewById(R.id.editTextNewUsername);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextNewConfirmPassword = findViewById(R.id.editTextNewConfirmPassword);
        editTextNewEmail = findViewById(R.id.editTextNewEmail);
        editTextNewPhoneNumber = findViewById(R.id.editTextNewPhoneNumber);

        buttonRegister = findViewById(R.id.buttonRegister);

        userDataSource = new UserDataSource(this);
        userDataSource.open();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform register operation here
                registerUser500();
            }
        });

    }

    private void registerUser500() {
        String newUsername = editTextNewUsername.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();
        String newConfirmPassword = editTextNewConfirmPassword.getText().toString();
        String newEmail = editTextNewEmail.getText().toString();
        String newPhoneNumber = editTextNewPhoneNumber.getText().toString();

        if (newUsername.isEmpty() || newPassword.isEmpty() ||
                newConfirmPassword.isEmpty() || newEmail.isEmpty() || newPhoneNumber.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {

            if (newPassword.equals(newConfirmPassword) && (isValidEmail(newEmail))) {
                long userId = userDataSource.userCreate(newUsername, newPassword, newEmail, newPhoneNumber);
                if (userId != -1) {
                    Toast.makeText(this, "User created successfully", Toast.LENGTH_SHORT).show();
                    // You may add further actions here, such as navigating to another activity
                } else {
                    Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Email or Password invalid", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private boolean isValidEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
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