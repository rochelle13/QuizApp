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

public class Login extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private DbHelperQuiz dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        dbHelper = new DbHelperQuiz(this);
        //login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                //user authentication
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Please fill all fields",
                            Toast.LENGTH_SHORT).show();
                } else {
                    boolean isValid = dbHelper.checkUser(username, password);
                    if (isValid) {
                        Toast.makeText(Login.this, "Login successful",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, Quiz_Option.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login.this, "Invalid username or password",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}