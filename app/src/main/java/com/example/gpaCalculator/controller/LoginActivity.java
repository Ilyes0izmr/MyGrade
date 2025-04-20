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
import com.example.gpaCalculator.model.entities.User;
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
        DatabaseSeeder seeder = new DatabaseSeeder(this);

        seeder.seedDatabase(); // Seeds dummy data if database is empty
        loginButton.setOnClickListener(v -> authenticateUser());
        signinLink.setOnClickListener(v -> navigateToSignUp());
    }

    public void authenticateUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (userDAO.validateUser(username, password)) {
            // Retrieve user details
            User user = userDAO.getUserByUsername(username);
            if (user != null) {
                // Start session with the logged-in user
                SessionManager.getInstance().startSession(user);

                // Navigate to HomeActivity
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Failed to retrieve user details", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }

    }

    private void navigateToSignUp() {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }

    private void navigateToHome() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }


}
