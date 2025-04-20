package com.example.gpaCalculator.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gpaCalculator.model.database.AdminDAO;
import com.example.gpaCalculator.model.database.StudentDAO;
import com.example.gpaCalculator.model.database.TeacherDAO;
import com.example.gpaCalculator.model.database.UserDAO;
import com.example.gpaCalculator.model.entities.User;
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
    private StudentDAO studentDAO;
    private TeacherDAO teacherDAO;
    private AdminDAO adminDAO;

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
        studentDAO = new StudentDAO(this);
        teacherDAO = new TeacherDAO(this);
        adminDAO = new AdminDAO(this);

        logInLink.setOnClickListener(v -> navigateToLogIn());
        buttonSignUp.setOnClickListener(v -> signUp());
    }

    public void navigateToLogIn() {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
    }

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

        // Determine role based on matricule
        String roleType = null;
        String userId = null;

        if (studentDAO.isMatriculeExists(matricule)) {
            roleType = "student";
            userId = studentDAO.getUserIdByMatricule(matricule); // Get the ID from the students table
        } else if (teacherDAO.isMatriculeExists(matricule)) {
            roleType = "teacher";
            userId = teacherDAO.getUserIdByMatricule(matricule); // Get the ID from the teachers table
        } else if (adminDAO.isMatriculeExists(matricule)) {
            roleType = "admin";
            userId = adminDAO.getUserIdByMatricule(matricule); // Get the ID from the admins table
        }

        if (roleType == null || userId == null) {
            Toast.makeText(this, "Matricule not found in any records!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if username or email is already taken
        /*if (userDAO.isUsernameOrEmailTaken(username, email)) {
            Toast.makeText(this, "Username or email is already in use!", Toast.LENGTH_SHORT).show();
            return;
        }*/

        // Create a User object
        String signupDate = String.valueOf(System.currentTimeMillis()); // Use current timestamp
        User user = new User(userId, username, email, password, null, signupDate, roleType);

        // Insert the new user into the users table
        boolean success = userDAO.insertUser(user);
        if (success) {
            Toast.makeText(this, "Sign-up successful!", Toast.LENGTH_SHORT).show();
            navigateToLogIn();
        } else {
            Toast.makeText(this, "Sign-up failed. Try again.", Toast.LENGTH_SHORT).show();
        }
    }
}