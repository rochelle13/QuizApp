package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;

public class QuizOption1 extends AppCompatActivity {

    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;

    private Button buttonConfirmNext;
    private Button buttonLogout;
    private TextView textViewQuestions;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCorrect;
    private TextView textViewWrong;
    private ArrayList<Questions> questionList;
    private int questionCounter;
    private int questionTotalCount;
    private Questions currentQuestion;
    private boolean answered;
    // Variables to track score and incorrect/correct answers
    private int score = 0;
    private int correctAnswers = 0;
    private int incorrectAnswers = 0;
    private String quizTable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_option1);
        setupUI();
        //get quiz table with questions
        Intent intent = getIntent();
        quizTable = intent.getStringExtra("QUIZ_TABLE");
        fetchDB();
        buttonLogout = findViewById(R.id.buttonLogout);

        //logout
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to MainMenuActivity
                Intent intent = new Intent(QuizOption1.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    private void setupUI() {
        textViewCorrect = findViewById(R.id.txtCorrect);
        textViewWrong = findViewById(R.id.txtWrong);
        textViewQuestionCount = findViewById(R.id.txtTotalQuestion);
        textViewScore = findViewById(R.id.txtScore);
        buttonConfirmNext = findViewById(R.id.button);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);

        textViewQuestions = findViewById(R.id.textViewQuestions);

    }
    private void fetchDB() {
        DbHelperQuiz dbHelper = new DbHelperQuiz(this);
        questionList = dbHelper.getQuestions(DbHelperQuiz.TABLE_QUESTIONS_1); // Pass thequiz table name
        startQuiz();
    }
    private void startQuiz(){
        questionTotalCount = questionList.size();
        Collections.shuffle(questionList);
        showQuestions();
        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!answered){
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() ){
                        handleAnswerSubmission();
                    }else{
                        Toast.makeText(QuizOption1.this, "Please Select an option",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    @SuppressLint("SetTextI18n")
    public void showQuestions() {
        rbGroup.clearCheck();
        // Reset all radio button backgrounds to white
        rb1.setBackground(ContextCompat.getDrawable(this,
                R.drawable.option_default_background));
        rb2.setBackground(ContextCompat.getDrawable(this,
                R.drawable.option_default_background));
        rb3.setBackground(ContextCompat.getDrawable(this,
                R.drawable.option_default_background));

        if (questionCounter < questionTotalCount) {
            currentQuestion = questionList.get(questionCounter);
            textViewQuestions.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            questionCounter++;
            answered = false;
            buttonConfirmNext.setText("CHECK ANSWER");
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionTotalCount);
        } else {
            //if no questions left finish quiz
            Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show();
            rb1.setClickable(false);
            rb2.setClickable(false);
            rb3.setClickable(false);

            buttonConfirmNext.setClickable(false);
        }
    }
    private void handleAnswerSubmission() {
        answered = true;
        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        if (rbSelected == null) {
            Toast.makeText(this, "No option selected", Toast.LENGTH_SHORT).show();
            return;
        }
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;
        checkSolution(answerNr, rbSelected);
    }
    @SuppressLint("SetTextI18n")
    private void checkSolution(int answerNr, RadioButton rbSelected) {
        rb1.setBackground(ContextCompat.getDrawable(this,
                R.drawable.option_default_background));
        rb2.setBackground(ContextCompat.getDrawable(this,
                R.drawable.option_default_background));
        rb3.setBackground(ContextCompat.getDrawable(this,
                R.drawable.option_default_background));

        if (currentQuestion.getAnswerNr() == answerNr) {
            // Correct answer
            score += 10;
            correctAnswers++;
            switch (answerNr) {
                case 1:
                    rb1.setBackground(ContextCompat.getDrawable(this,
                            R.drawable.when_answer_right));
                    break;
                case 2:
                    rb2.setBackground(ContextCompat.getDrawable(this,
                            R.drawable.when_answer_right));
                    break;
                case 3:
                    rb3.setBackground(ContextCompat.getDrawable(this,
                            R.drawable.when_answer_right));
                    break;

            }
        } else {
            // Incorrect answer
            incorrectAnswers++;
            changetoIncorrectColor(rbSelected);
            switch (currentQuestion.getAnswerNr()) {
                case 1:
                    rb1.setBackground(ContextCompat.getDrawable(this,
                            R.drawable.when_answer_right));
                    break;
                case 2:
                    rb2.setBackground(ContextCompat.getDrawable(this,
                            R.drawable.when_answer_right));
                    break;
                case 3:
                    rb3.setBackground(ContextCompat.getDrawable(this,
                            R.drawable.when_answer_right));
                    break;

            }
        }
        // Updatescore
        updateScoreUI();
        if (questionCounter < questionTotalCount) {
            buttonConfirmNext.setText("Moving on to next question...");
        }
        // Move to the next question
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showQuestions();
            }
        }, 2000); // Delay of 2 second
    }
    private void changetoIncorrectColor(RadioButton rbSelected) {
        rbSelected.setBackground(ContextCompat.getDrawable(this,
                R.drawable.when_answer_wrong));
    }
    //updates score and correct/incorrect answer counts
    @SuppressLint("SetTextI18n")
    private void updateScoreUI() {
        textViewScore.setText("Score: " + score + "/100");
        textViewCorrect.setText("Correct: " + correctAnswers);
        textViewWrong.setText("Incorrect: " + incorrectAnswers);
    }

}