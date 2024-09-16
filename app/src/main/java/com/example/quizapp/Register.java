package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Register extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword;
    private Button btnRegister;
    private DbHelperQuiz dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);
        dbHelper = new DbHelperQuiz(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Register.this, "Please fill all fields",
                            Toast.LENGTH_SHORT).show();
                } else {
                    boolean success = dbHelper.addUser(username, email, password);
                    if (success) {
                        Toast.makeText(Register.this, "Registration successful",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Register.this, "Registration failed. Username may already exist.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}