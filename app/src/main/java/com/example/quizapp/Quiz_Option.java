package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class Quiz_Option extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz_option);

        Button buttonQuiz1 = findViewById(R.id.buttonQuiz1);
        Button buttonQuiz2 = findViewById(R.id.buttonQuiz2);
        buttonQuiz1.setOnClickListener(v -> {
            // Start the first quiz activity
            Intent intent = new Intent(Quiz_Option.this, QuizOption1.class);
            startActivity(intent);
        });
        buttonQuiz2.setOnClickListener(v -> {
            // Start the second quiz activity
            Intent intent = new Intent(Quiz_Option.this, QuizOption2.class);
            startActivity(intent);
        });

    }
}