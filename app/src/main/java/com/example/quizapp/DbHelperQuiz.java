package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelperQuiz extends SQLiteOpenHelper {
    //database
    private static final String DATABASE_NAME = "QuizDB.db";
    private static final int DATABASE_VERSION = 3; // Updated version for the new table
    //tables
    public static final String TABLE_QUESTIONS_1 = "questions1";
    public static final String TABLE_QUESTIONS_2 = "questions2";
    private static final String TABLE_USERS = "users";
    // Questions table columns
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_OPTION1 = "option1";
    private static final String COLUMN_OPTION2 = "option2";
    private static final String COLUMN_OPTION3 = "option3";

    private static final String COLUMN_ANSWER_NR = "answer_nr";
    // Users table columns
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    // Constructor
    public DbHelperQuiz(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creates questions tables
        final String SQL_CREATE_QUESTIONS1_TABLE = "CREATE TABLE " +
                TABLE_QUESTIONS_1 + " ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUESTION + " TEXT, " +
                COLUMN_OPTION1 + " TEXT, " +
                COLUMN_OPTION2 + " TEXT, " +
                COLUMN_OPTION3 + " TEXT, " +
                COLUMN_ANSWER_NR + " INTEGER" +
                ")";
        final String SQL_CREATE_QUESTIONS2_TABLE = "CREATE TABLE " +
                TABLE_QUESTIONS_2 + " ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUESTION + " TEXT, " +
                COLUMN_OPTION1 + " TEXT, " +
                COLUMN_OPTION2 + " TEXT, " +
                COLUMN_OPTION3 + " TEXT, " +
                COLUMN_ANSWER_NR + " INTEGER" +
                ")";
        // Creates users table
        final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " +
                TABLE_USERS + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT UNIQUE, " + // Ensuring username is unique
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT" + ")";
        // Executes the queries
        db.execSQL(SQL_CREATE_QUESTIONS1_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS2_TABLE);
        db.execSQL(SQL_CREATE_USERS_TABLE);
        // Pre-fill the questions tables
        fillQuestionsTable1(db);
        fillQuestionsTable2(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS_2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // add a user to the users table
    public boolean addUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1; // Return true if insertion is successful
    }

    // check if a user exists in the database
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{COLUMN_USER_ID}, // Selecting only user_id
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?", // WHEREclause
                new String[]{username, password}, // Arguments for WHERE clause
                null, null, null
        );
        boolean userExists = (cursor.getCount() > 0); // Check if any record was found
        cursor.close();
        return userExists;
    }

    //Counttries quiz questions
    private void fillQuestionsTable1(SQLiteDatabase db) {
        Questions q1 = new Questions("What is Captain America's shield made of?",
                "Vibranium ", "Adamantium ", "Titanium",  1);
        addQuestion(db, TABLE_QUESTIONS_1, q1);
        Questions q2 = new Questions("Who was the first Avenger to wield Thor’s hammer, Mjolnir, besides Thor?", "Captain America", "Iron Man", "Hulk",  1);
        addQuestion(db, TABLE_QUESTIONS_1, q2);
        Questions q3 = new Questions("What is Spider-Man’s real name?", "Peter Parker", "Miles Morales", "Ben Reilly",  1);
        addQuestion(db, TABLE_QUESTIONS_1, q3);
        Questions q4 = new Questions("Which superhero is known as the \"Sorcerer Supreme\"?",
                "Loki", "Doctor Strange", "Scarlet Witch",  2);
        addQuestion(db, TABLE_QUESTIONS_1, q4);
        Questions q5 = new Questions("What is the superhero alias of T’Challa, king of Wakanda?", "Black Panther",
                "Black Bolt", "Falcon",  1);
        addQuestion(db, TABLE_QUESTIONS_1, q5);
        Questions q6 = new Questions("Who is Tony Stark’s best friend, also known as War Machine?", "Happy Hogan",
                "James \"Rhodey\" Rhodes", "Sam Wilson",  2);
        addQuestion(db, TABLE_QUESTIONS_1, q6);
        Questions q7 = new Questions("Which Marvel hero is known for the catchphrase, \"Flame On!\"?", "Johnny Storm", "Pyro", "Nova", 1);
        addQuestion(db, TABLE_QUESTIONS_1, q7);
        Questions q8 = new Questions("What superhero team does Deadpool often work with, though reluctantly?", "The X-Force", "The Avengers", "The Fantastic Four", 1);
        addQuestion(db, TABLE_QUESTIONS_1, q8);
        Questions q9 = new Questions("Which hero possesses the Phoenix Force in the X-Men comics?", "Storm", "Cyclops", "Jean Grey", 3);
        addQuestion(db, TABLE_QUESTIONS_1, q9);
        Questions q10 = new Questions("Who is known as the \"Man Without Fear\"?", "Hawkeye", "Daredevil", "Iron Fist", 2);
        addQuestion(db, TABLE_QUESTIONS_1, q10);
    }

    //Continents quiz questions
    private void fillQuestionsTable2(SQLiteDatabase db) {
        Questions q1 = new Questions("What is the name of the villain who wiped out half of all life in the universe?", "Galactus", "Thanos", "Loki", 2);
        addQuestion(db, TABLE_QUESTIONS_2, q1);
        Questions q2 = new Questions("Who is the arch-nemesis of Spider-Man, often seen with a pumpkin bomb?", "Mysterio",
                "Green Goblin", "Venom",  2);
        addQuestion(db, TABLE_QUESTIONS_2, q2);
        Questions q3 = new Questions("Which villain is responsible for the creation of Ultron in the MCU?",
                "Red Skull", "Loki", "Tony Stark",  3);
        addQuestion(db, TABLE_QUESTIONS_2, q3);
        Questions q4 = new Questions("What villain does Doctor Doom rule as monarch?",
                "Sokovia ", "Latveria", "Madripoor",  2);
        addQuestion(db, TABLE_QUESTIONS_2, q4);
        Questions q5 = new Questions("Which X-Men villain has the ability to control magnetism?", "Juggernaut", "Apocalypse",  "Magneto", 3);
        addQuestion(db, TABLE_QUESTIONS_2, q5);
        Questions q6 = new Questions("Who is the leader of the Black Order, Thanos' elite generals?", "Corvus Glaive",
                "Proxima Midnight", "Ebony Maw", 1);
        addQuestion(db, TABLE_QUESTIONS_2, q6);
        Questions q7 = new Questions("Which villain fought Hulk in a gladiatorial arena on Sakaar in *Thor: Ragnarok?",
                "The Grandmaster ", "Hela",  "The Abomination", 2);
        addQuestion(db, TABLE_QUESTIONS_2, q7);
        Questions q8 = new Questions("What is the true identity of the villain known as the Winter Soldier?", "Bucky Barnes", "Steve Rogers", "Clint Barton",  1);
        addQuestion(db, TABLE_QUESTIONS_2, q8);
        Questions q9 = new Questions("Which villainous organization does Red Skull lead?", "AIM", "HYDRA", "The Hand",  2);
        addQuestion(db, TABLE_QUESTIONS_2, q9);
        Questions q10 = new Questions("Can you name the Marvel villain played by Tom Hiddleston in \"Thor\" and \"The Avengers\"?", "Loki",
                "Helmet Zemo", "Justin Hammer",  3);
        addQuestion(db, TABLE_QUESTIONS_2, q10);
    }

    // Helper method to insert a question into taable
    private void addQuestion(SQLiteDatabase db, String tableName, Questions question) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_QUESTION, question.getQuestion());
        cv.put(COLUMN_OPTION1, question.getOption1());
        cv.put(COLUMN_OPTION2, question.getOption2());
        cv.put(COLUMN_OPTION3, question.getOption3());
        cv.put(COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(tableName, null, cv);
    }

    // retrieve questions
    @SuppressLint("Range")
    public ArrayList<Questions> getQuestions(String tableName) {
        ArrayList<Questions> questionsList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                "_id",
                COLUMN_QUESTION,
                COLUMN_OPTION1,
                COLUMN_OPTION2,
                COLUMN_OPTION3,
                COLUMN_ANSWER_NR
        };
        Cursor c = db.query(
                tableName,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            do {
                Questions question = new Questions();
                question.setQuestion(c.getString(c.getColumnIndex(COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(COLUMN_ANSWER_NR)));
                questionsList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionsList;
    }

}
