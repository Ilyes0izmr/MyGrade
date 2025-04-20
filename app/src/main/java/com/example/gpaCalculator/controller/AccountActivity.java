package com.example.gpaCalculator.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        // Get references to TextViews
        TextView tvUsername = findViewById(R.id.tvUsername);
        TextView tvEmail = findViewById(R.id.tvEmail);
        TextView tvRole = findViewById(R.id.tvRole);
        TextView tvFirstName = findViewById(R.id.tvFirstName);
        TextView tvLastName = findViewById(R.id.tvLastName);
        TextView tvBirthdate = findViewById(R.id.tvBirthdate);
        TextView tvUniversity = findViewById(R.id.tvUniversity);
        TextView tvFaculty = findViewById(R.id.tvFaculty);
        TextView tvGroup = findViewById(R.id.tvGroup);
        TextView tvClasses = findViewById(R.id.tvClasses);
        TextView tvLevels = findViewById(R.id.tvLevels);

        // Retrieve the current user from the SessionManager
        User currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser != null) {
            tvUsername.setText("Username: " + currentUser.getUsername());
            tvEmail.setText("Email: " + currentUser.getEmail());
            String role = SessionManager.getInstance().getUserRole();
            tvRole.setText("Role: " + role);

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
                        tvUniversity.setVisibility(View.VISIBLE);
                        tvFaculty.setVisibility(View.VISIBLE);
                        tvGroup.setVisibility(View.VISIBLE);
                        tvClasses.setVisibility(View.GONE);
                        tvLevels.setVisibility(View.GONE);
                    }
                    break;

                case "teacher":
                    Teacher teacher = teacherDAO.getTeacherById(currentUser.getId());
                    if (teacher != null) {
                        tvFirstName.setText("First Name: " + teacher.getFirstName());
                        tvLastName.setText("Last Name: " + teacher.getLastName());
                        tvBirthdate.setText("Birthdate: " + teacher.getBirthdate());

                        // Fetch and display classes taught
                        //String classesTaught = String.join(", ", teacherDAO.getClassesTaught(currentUser.getId()));
                        //tvClasses.setText("Classes Taught: " + classesTaught);

                        // Fetch and display levels taught
                        //String levelsTaught = String.join(", ", teacherDAO.getLevelsTaught(currentUser.getId()));
                        //tvLevels.setText("Levels Taught: " + levelsTaught);

                        // Show relevant fields for teachers
                        tvUniversity.setVisibility(View.GONE);
                        tvFaculty.setVisibility(View.GONE);
                        tvGroup.setVisibility(View.GONE);
                        tvClasses.setVisibility(View.VISIBLE);
                        tvLevels.setVisibility(View.VISIBLE);
                    }
                    break;

                default:
                    tvFirstName.setText("First Name: Unknown");
                    tvLastName.setText("Last Name: Unknown");
                    tvBirthdate.setText("Birthdate: Unknown");
                    tvUniversity.setVisibility(View.GONE);
                    tvFaculty.setVisibility(View.GONE);
                    tvGroup.setVisibility(View.GONE);
                    tvClasses.setVisibility(View.GONE);
                    tvLevels.setVisibility(View.GONE);
                    break;
            }
        } else {
            tvUsername.setText("No user logged in");
            tvEmail.setText("");
            tvRole.setText("");
        }

        // Back to Home Button
        Button btnBackToHome = findViewById(R.id.btnBackToHome);
        btnBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}