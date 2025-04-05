// src/main/java/com/example/gpacalculator/activities/AccountActivity.java
package com.example.gpaCalculator.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gpaCalculator.controller.HomeActivity;
import com.example.gpaCalculator.model.database.DatabaseHelper;
import com.example.gpaCalculator.model.database.TeacherDAO;
import com.example.gpaCalculator.model.database.UserDAO;
import com.example.gpaCalculator.model.entities.Student;
import com.example.gpaCalculator.model.entities.Teacher;
import com.example.gpaCalculator.model.entities.User;
import com.example.myapplication.R;

import java.util.List;

public class AccountActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @SuppressLint("SetTextI18n")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

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
            tvRole.setText("Role: " + SessionManager.getInstance().getUserRole());

            // Fetch enrollment details
            UserDAO userDAO = new UserDAO(this);
            String[] enrollmentDetails = userDAO.getEnrollmentDetails(currentUser.getEnrollmentId());
            tvFirstName.setText("First Name: " + enrollmentDetails[0]);
            tvLastName.setText("Last Name: " + enrollmentDetails[1]);
            tvBirthdate.setText("Birthdate: " + enrollmentDetails[2]);

            // Display role-specific information
            if (currentUser instanceof Student) {
                Student student = (Student) currentUser;
                tvUniversity.setText("University: " + student.getUniversity());
                tvFaculty.setText("Faculty: " + student.getFaculty());
                tvGroup.setText("Group: " + student.getGroupId());
                tvUniversity.setVisibility(View.VISIBLE);
                tvFaculty.setVisibility(View.VISIBLE);
                tvGroup.setVisibility(View.VISIBLE);
            } else if (currentUser instanceof Teacher) {
                TeacherDAO teacherDAO = new TeacherDAO(this);

                // Fetch and display classes taught
                List<String> classesTaught = teacherDAO.getClassesTaught(currentUser.getId());
                tvClasses.setText("Classes Taught: " + String.join(", ", classesTaught));

                // Fetch and display levels taught
                List<String> levelsTaught = teacherDAO.getLevelsTaught(currentUser.getId());
                tvLevels.setText("Levels Taught: " + String.join(", ", levelsTaught));

                tvClasses.setVisibility(View.VISIBLE);
                tvLevels.setVisibility(View.VISIBLE);
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

    private String getUserRole(SQLiteDatabase db, int enrollmentId) {
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_ENROLLMENT,
                null,
                DatabaseHelper.COLUMN_ENROLLMENT_ID + " = ?",
                new String[]{String.valueOf(enrollmentId)},
                null, null, null
        );

        String role = "Unknown";
        if (cursor.moveToFirst()) {
            String studentId = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ENROLLMENT_STUDENT_ID));
            String professorId = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ENROLLMENT_PROFESSOR_ID));
            String adminUnivId = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ENROLLMENT_ADMIN_UNIV_ID));

            if (studentId != null) {
                role = "Student";
            } else if (professorId != null) {
                role = "Teacher";
            } else if (adminUnivId != null) {
                role = "Admin";
            }
        }
        cursor.close();
        return role;
    }
}