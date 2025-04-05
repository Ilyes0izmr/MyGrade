package com.example.gpaCalculator.controller;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gpaCalculator.MainActivity;
import com.example.gpaCalculator.model.database.DatabaseHelper;
import com.example.gpaCalculator.model.database.DatabaseSeeder;
import com.example.gpaCalculator.model.database.UserDAO;
import com.example.myapplication.R;
import android.util.Log;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private Switch rememberMeSwitch;
    private TextView signinLink;

    private UserDAO userDAO ;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        rememberMeSwitch = findViewById(R.id.rememberMeSwitch);
        signinLink = findViewById(R.id.signinLink);
        userDAO = new UserDAO(this);
        insertDummyData();
        insertDummyDataEnrollment();
        DatabaseSeeder seeder = new DatabaseSeeder(this);
        seeder.seedDatabase(); // Seeds dummy data if database is empty
        loginButton.setOnClickListener(v -> authenticateUser());
        signinLink.setOnClickListener(v -> navigateToSignUp());




    }
    public void authenticateUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"password or username is empty",Toast.LENGTH_SHORT).show();
            return;
        }

        if (userDAO.validateUser(username, password)) {
            if (rememberMeSwitch.isChecked()) {
                //SessionManager.getInstance(this).startSession(username);
                //TODO: handle the session after this
            }
            navigateToHome();
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }

    }
    private void navigateToSignUp() {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }
    private void navigateToHome() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
    private void insertDummyData() {
        // Insert dummy data only for testing
        SQLiteDatabase db = new DatabaseHelper(this).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USERS_USERNAME, "0");
        values.put(DatabaseHelper.COLUMN_USERS_EMAIL, "00");
        values.put(DatabaseHelper.COLUMN_USERS_PASSWORD_HASH, "0"); // You can replace with a real hashed password
        values.put(DatabaseHelper.COLUMN_USERS_PHONE, "1234567890");
        values.put(DatabaseHelper.COLUMN_USERS_SIGNUP_DATE, "2025-03-15");

        long result = db.insert(DatabaseHelper.TABLE_USERS, null, values);

        if (result != -1) {
            Log.d("DB", "Dummy data inserted successfully");
        } else {
            Log.d("DB", "Failed to insert dummy data");
        }
    }
    private void insertDummyDataEnrollment() {
        // Open writable database
        SQLiteDatabase db = new DatabaseHelper(this).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ENROLLMENT_EMAIL, "1");
        values.put(DatabaseHelper.COLUMN_ENROLLMENT_FIRST_NAME, "ilyes");
        values.put(DatabaseHelper.COLUMN_ENROLLMENT_LAST_NAME, "ilyes");
        values.put(DatabaseHelper.COLUMN_ENROLLMENT_BIRTHDATE, "2000-01-01");
        values.put(DatabaseHelper.COLUMN_ENROLLMENT_GENDER, "Male");
        values.put(DatabaseHelper.COLUMN_ENROLLMENT_STUDENT_ID, "1");
        values.put(DatabaseHelper.COLUMN_ENROLLMENT_PROFESSOR_ID, ""); // Null for a student

        long result = db.insert(DatabaseHelper.TABLE_ENROLLMENT, null, values);

        if (result != -1) {
            Log.d("DB", "Dummy enrollment data inserted successfully");
        } else {
            Log.d("DB", "Failed to insert dummy enrollment data");
        }
    }


}
