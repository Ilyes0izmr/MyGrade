package com.example.gpaCalculator.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gpaCalculator.model.database.EnrollmentDAO;
import com.example.gpaCalculator.model.database.UserDAO;
import com.example.myapplication.R;

public class SignUpActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextMatricule;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button buttonSignUp;
    private TextView logInLink;

    private UserDAO userDAO;
    //TODO: other DAO here


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextMatricule = findViewById(R.id.editTextMatricule);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        logInLink = findViewById(R.id.textViewLogin);

        userDAO = new UserDAO(this);

        logInLink.setOnClickListener(v -> navigateToLogIn());
        buttonSignUp.setOnClickListener(v -> signUp());
    }

    public void navigateToLogIn() {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
    }

    //TODO:if yes we check if the user has an account
    public void signUp() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String matricule = editTextMatricule.getText().toString().trim();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        // Validate input fields
        if (username.isEmpty() || email.isEmpty() || matricule.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if matricule is enrolled
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO(this);
        if (!enrollmentDAO.isMatriculeEnrolled(matricule)) {
            Toast.makeText(this, "Matricule not found in enrollment records!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if username or email is already taken
        if (userDAO.isUsernameOrEmailTaken(username, email)) {
            Toast.makeText(this, "Username or email is already in use!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if matricule is already used by anyone (students or teachers)
        if (userDAO.isMatriculeTaken(matricule)) {
            Toast.makeText(this, "This matricule is already associated with an account!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert the new user
        boolean success = userDAO.insertUser(username, email, password);
        if (success) {
            Toast.makeText(this, "Sign-up successful!", Toast.LENGTH_SHORT).show();
            navigateToLogIn();
        } else {
            Toast.makeText(this, "Sign-up failed. Try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
