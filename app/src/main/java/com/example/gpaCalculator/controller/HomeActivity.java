package com.example.gpaCalculator.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.myapplication.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get GridLayout container
        GridLayout grid = findViewById(R.id.gridContainer);

        // Get all cards from layout
        CardView cardAccount = findViewById(R.id.cardAccount);
        CardView cardGroups = findViewById(R.id.cardGroups);
        CardView cardGrades = findViewById(R.id.cardGrades);
        CardView cardRecords = findViewById(R.id.cardRecords);
        CardView cardAnnouncement= findViewById(R.id.cardAnnouncement);

        // Clear everything first
        grid.removeAllViews();

        // Add account (common for all users)
        cardAnnouncement.setVisibility(View.VISIBLE);
        grid.addView(cardAnnouncement);
        cardAnnouncement.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, AnnouncementActivity.class));
        });

        cardAccount.setVisibility(View.VISIBLE);
        grid.addView(cardAccount);
        cardAccount.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, AccountActivity.class));
        });

        String userRole = SessionManager.getInstance().getUserRole();

        if ("teacher".equals(userRole)) {
            cardGroups.setVisibility(View.VISIBLE);
            grid.addView(cardGroups);
            cardGroups.setOnClickListener(v -> {
                startActivity(new Intent(HomeActivity.this, GroupListActivity.class));
            });

            cardGrades.setVisibility(View.VISIBLE);
            grid.addView(cardGrades);
            cardGrades.setOnClickListener(v -> {
                startActivity(new Intent(HomeActivity.this, TeacherGradesActivity.class));
            });

        } else {
            cardRecords.setVisibility(View.VISIBLE);
            grid.addView(cardRecords);
            cardRecords.setOnClickListener(v -> {
                startActivity(new Intent(HomeActivity.this, StudentRecordsActivity.class));
            });
        }
    }

}
