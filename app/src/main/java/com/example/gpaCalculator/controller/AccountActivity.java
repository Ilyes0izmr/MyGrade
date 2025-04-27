package com.example.gpaCalculator.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gpaCalculator.model.database.StudentDAO;
import com.example.gpaCalculator.model.database.TeacherDAO;
import com.example.gpaCalculator.model.database.UserDAO;
import com.example.gpaCalculator.model.entities.Student;
import com.example.gpaCalculator.model.entities.Teacher;
import com.example.gpaCalculator.model.entities.User;
import com.example.myapplication.R;

public class AccountActivity extends AppCompatActivity {

    private UserDAO userDAO;
    private StudentDAO studentDAO;
    private TeacherDAO teacherDAO;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Initialize DAOs
        userDAO = new UserDAO(this);
        studentDAO = new StudentDAO(this);
        teacherDAO = new TeacherDAO(this);

        // Get references to TextViews and Layouts
        TextView tvProfilePicture = findViewById(R.id.tvProfilePicture);
        TextView tvUsername = findViewById(R.id.tvUsernameHeader);
        TextView tvUsernameHeader = findViewById(R.id.tvUsernameHeader);
        TextView tvEmail = findViewById(R.id.tvEmail);
        TextView tvRole = findViewById(R.id.tvRoleHeader);
        TextView tvRoleHeader = findViewById(R.id.tvRoleHeader);
        TextView tvFirstName = findViewById(R.id.tvFirstName);
        TextView tvLastName = findViewById(R.id.tvLastName);
        TextView tvBirthdate = findViewById(R.id.tvBirthdate);
        LinearLayout layoutUniversity = findViewById(R.id.layoutUniversity);
        TextView tvUniversity = findViewById(R.id.tvUniversity);
        LinearLayout layoutFaculty = findViewById(R.id.layoutFaculty);
        TextView tvFaculty = findViewById(R.id.tvFaculty);
        LinearLayout layoutGroup = findViewById(R.id.layoutGroup);
        TextView tvGroup = findViewById(R.id.tvGroup);

        // Retrieve the current user from the SessionManager
        User currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            tvUsername.setText("Email: " + email); // Changed to Email in the list
            tvUsernameHeader.setText(currentUser.getUsername());
            tvEmail.setText("Email: " + email);
            String role = SessionManager.getInstance().getUserRole();
            tvRole.setText("Role: " + role);
            tvRoleHeader.setText(role);

            // Set profile picture
            if (email != null && !email.isEmpty()) {
                String initials = "";
                String[] parts = email.split("@");
                if (parts.length > 0) {
                    String usernamePart = parts[0];
                    if (!usernamePart.isEmpty()) {
                        if (usernamePart.length() >= 2) {
                            initials = usernamePart.substring(0, 2).toUpperCase();
                        } else {
                            initials = usernamePart.substring(0, 1).toUpperCase();
                        }
                    }
                }
                tvProfilePicture.setText(initials);
            }

            // Fetch role-specific details based on role_type
            switch (role) {
                case "student":
                    Student student = studentDAO.getStudentById(currentUser.getId());
                    if (student != null) {
                        tvFirstName.setText("First Name: " + student.getFirstName());
                        tvLastName.setText("Last Name: " + student.getLastName());
                        tvBirthdate.setText("Birthdate: " + student.getBirthdate());
                        tvUniversity.setText("University: " + student.getUniversity());
                        tvFaculty.setText("Faculty: " + student.getFaculty());
                        tvGroup.setText("Group: " + student.getGroupId());

                        // Show relevant fields for students
                        layoutUniversity.setVisibility(View.VISIBLE);
                        layoutFaculty.setVisibility(View.VISIBLE);
                        layoutGroup.setVisibility(View.VISIBLE);
                    }
                    break;

                case "teacher":
                    Teacher teacher = teacherDAO.getTeacherById(currentUser.getId());
                    if (teacher != null) {
                        tvFirstName.setText("First Name: " + teacher.getFirstName());
                        tvLastName.setText("Last Name: " + teacher.getLastName());
                        tvBirthdate.setText("Birthdate: " + teacher.getBirthdate());
                        layoutUniversity.setVisibility(View.GONE);
                        layoutFaculty.setVisibility(View.GONE);
                        layoutGroup.setVisibility(View.GONE);
                    }
                    break;

                default:
                    tvFirstName.setText("First Name: Unknown");
                    tvLastName.setText("Last Name: Unknown");
                    tvBirthdate.setText("Birthdate: Unknown");
                    layoutUniversity.setVisibility(View.GONE);
                    layoutFaculty.setVisibility(View.GONE);
                    layoutGroup.setVisibility(View.GONE);
                    break;
            }
        } else {
            tvUsername.setText("No user logged in");
            tvEmail.setText("");
            tvRole.setText("");
            tvUsernameHeader.setText("");
            tvRoleHeader.setText("");
            tvProfilePicture.setText("");
        }

        // Back to Home Button
        Button btnBackToHome = findViewById(R.id.btnBackToHome);
        btnBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        Button btnSignOut = findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}