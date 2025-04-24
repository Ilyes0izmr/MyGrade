package com.example.gpaCalculator.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find buttons in the layout
        Button btnGoToAccount = findViewById(R.id.btnGoToAccount);
        Button btnViewGroupsSubjects = findViewById(R.id.btnViewGroupsSubjects);
        Button btnManageGrades = findViewById(R.id.btnManageGrades);
        Button btnViewRecords = findViewById(R.id.btnViewRecords);

        // Get the current user's role from SessionManager
        String userRole = SessionManager.getInstance().getUserRole();

        if ("teacher".equals(userRole)) {
            // Teacher view
            btnViewGroupsSubjects.setVisibility(View.VISIBLE);
            btnManageGrades.setVisibility(View.VISIBLE);
            btnViewRecords.setVisibility(View.GONE);

            btnViewGroupsSubjects.setOnClickListener(v -> {
                startActivity(new Intent(HomeActivity.this, GroupListActivity.class));
            });

            btnManageGrades.setOnClickListener(v -> {
                startActivity(new Intent(HomeActivity.this, TeacherGradesActivity.class));
            });
        } else {
            // Student view
            btnViewGroupsSubjects.setVisibility(View.GONE);
            btnManageGrades.setVisibility(View.GONE);
            btnViewRecords.setVisibility(View.VISIBLE);

            btnViewRecords.setOnClickListener(v -> {
                startActivity(new Intent(HomeActivity.this, StudentRecordsActivity.class));
            });
        }

        // Set OnClickListener for the "View Account" button (for all users)
        btnGoToAccount.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, AccountActivity.class));
        });
    }
}