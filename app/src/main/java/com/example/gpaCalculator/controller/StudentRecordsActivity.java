package com.example.gpaCalculator.controller;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gpaCalculator.model.database.DatabaseHelper;
import com.example.myapplication.R;

import java.text.DecimalFormat;

public class StudentRecordsActivity extends AppCompatActivity {

    private LinearLayout recordsContainer;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_records);

        recordsContainer = findViewById(R.id.recordsContainer);
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();

        loadStudentRecords();
    }

    private void loadStudentRecords() {
        // Get logged in student ID from SessionManager
        String studentId = SessionManager.getInstance().getCurrentUser().getId();

        // Query to get all records for this student with subject names
        String query = "SELECT sr." + DatabaseHelper.COLUMN_RECORD_ID + ", " +
                "ts." + DatabaseHelper.COLUMN_SUBJECT_NAME + ", " +
                "sr." + DatabaseHelper.COLUMN_RECORD_TD_NOTE + ", " +
                "sr." + DatabaseHelper.COLUMN_RECORD_TP_NOTE + ", " +
                "sr." + DatabaseHelper.COLUMN_RECORD_EXAM_NOTE + " " +
                "FROM " + DatabaseHelper.TABLE_STUDENT_RECORDS + " sr " +
                "JOIN " + DatabaseHelper.TABLE_TEACHER_SUBJECTS + " ts ON sr." +
                DatabaseHelper.COLUMN_RECORD_SUBJECT_ID + " = ts." +
                DatabaseHelper.COLUMN_SUBJECT_ID + " " +
                "WHERE sr." + DatabaseHelper.COLUMN_RECORD_STUDENT_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{studentId});

        if (cursor.getCount() == 0) {
            TextView noRecords = new TextView(this);
            noRecords.setText("No grades recorded yet");
            noRecords.setTextSize(18);
            noRecords.setGravity(View.TEXT_ALIGNMENT_CENTER);
            recordsContainer.addView(noRecords);
            return;
        }

        DecimalFormat df = new DecimalFormat("#.##");

        while (cursor.moveToNext()) {
            String subjectName = cursor.getString(1);
            double tdGrade = cursor.getDouble(2);
            double tpGrade = cursor.getDouble(3);
            double examGrade = cursor.getDouble(4);

            // Calculate GPA using the formula: ((TD+TP)/2)*0.4 + Exam*0.6
            double averageTdTp = (tdGrade + tpGrade) / 2;
            double gpa = (averageTdTp * 0.4) + (examGrade * 0.6);

            // Inflate the item layout
            View gradeItem = LayoutInflater.from(this)
                    .inflate(R.layout.item_subject_grade, recordsContainer, false);

            // Set values
            TextView subjectTextView = gradeItem.findViewById(R.id.subjectName);
            subjectTextView.setText(subjectName);

            TextView tpGradeView = gradeItem.findViewById(R.id.tpGrade);
            tpGradeView.setText(df.format(tpGrade));

            TextView tdGradeView = gradeItem.findViewById(R.id.tdGrade);
            tdGradeView.setText(df.format(tdGrade));

            TextView examGradeView = gradeItem.findViewById(R.id.examGrade);
            examGradeView.setText(df.format(examGrade));

            TextView gpaView = gradeItem.findViewById(R.id.gpaValue);
            gpaView.setText(df.format(gpa));

            // Add color based on GPA
            if (gpa >= 10) {
                gpaView.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            } else {
                gpaView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }

            // Add to container
            recordsContainer.addView(gradeItem);
        }
        cursor.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null) {
            db.close();
        }
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}