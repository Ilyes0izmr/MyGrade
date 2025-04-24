package com.example.gpaCalculator.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gpaCalculator.model.database.DatabaseHelper;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherGradesActivity extends AppCompatActivity {

    private Spinner studentSpinner;
    private TextView subjectNameLabel;
    private EditText tpGradeInput, tdGradeInput, examGradeInput;
    private String selectedSubjectId;
    private String selectedSubjectName;
    private long selectedStudentId;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    // Properly declared maps
    private final Map<Long, String> studentSubjectMap = new HashMap<>();
    private final Map<Long, String> studentSubjectNameMap = new HashMap<>();
    private final List<String> studentDisplayList = new ArrayList<>();
    private final List<Long> studentIdList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_grades);

        initializeViews();
        initializeDatabase();
        loadTeacherData();
    }

    private void initializeViews() {
        studentSpinner = findViewById(R.id.studentSpinner);
        subjectNameLabel = findViewById(R.id.subjectNameLabel);
        tpGradeInput = findViewById(R.id.tpGradeInput);
        tdGradeInput = findViewById(R.id.tdGradeInput);
        examGradeInput = findViewById(R.id.examGradeInput);
    }

    private void initializeDatabase() {
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
    }

    private void loadTeacherData() {
        String teacherId = SessionManager.getInstance().getCurrentUser().getId();
        if (teacherId == null || teacherId.isEmpty()) {
            showToast("Teacher ID not found! Please log in again.");
            finish();
            return;
        }
        loadStudentsWithCorrectSubjects(teacherId);
        setupSubmitButton();
    }

    private void loadStudentsWithCorrectSubjects(String teacherId) {
        studentSubjectMap.clear();
        studentSubjectNameMap.clear();
        studentDisplayList.clear();
        studentIdList.clear();

        String query =
                "SELECT " +
                        "  s." + DatabaseHelper.COLUMN_STUDENTS_ID      + ", " +  // student PK
                        "  u." + DatabaseHelper.COLUMN_USERS_USERNAME   + ", " +  // student username
                        "  ts." + DatabaseHelper.COLUMN_SUBJECT_ID      + ", " +  // subject PK
                        "  ts." + DatabaseHelper.COLUMN_SUBJECT_NAME       +    // subject name
                        " FROM " + DatabaseHelper.TABLE_TEACHER_GROUPS   + " tg" +
                        " JOIN " + DatabaseHelper.TABLE_GROUPS          + " g  ON tg." + DatabaseHelper.COLUMN_GROUP_ID           + " = g." + DatabaseHelper.COLUMN_GROUPS_ID +
                        " JOIN " + DatabaseHelper.TABLE_STUDENTS        + " s  ON s." + DatabaseHelper.COLUMN_GROUP_ID           + " = g." + DatabaseHelper.COLUMN_GROUPS_ID +
                        " JOIN " + DatabaseHelper.TABLE_USERS           + " u  ON s." + DatabaseHelper.COLUMN_STUDENTS_ID       + " = u." + DatabaseHelper.COLUMN_USERS_ID +
                        " JOIN " + DatabaseHelper.TABLE_TEACHER_SUBJECTS + " ts ON ts." + DatabaseHelper.COLUMN_TEACHER_ID        + " = tg." + DatabaseHelper.COLUMN_TEACHER_ID +
                        "    AND ts." + DatabaseHelper.COLUMN_GROUP_SUBJECT_ID + " = tg." + DatabaseHelper.COLUMN_GROUP_ID +
                        " WHERE tg." + DatabaseHelper.COLUMN_TEACHER_ID   + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{ teacherId });
        while (cursor.moveToNext()) {
            long   studentId    = cursor.getLong(0);
            String studentName  = cursor.getString(1);
            String subjectId    = cursor.getString(2);
            String subjectName  = cursor.getString(3);

            studentSubjectMap     .put(studentId, subjectId);
            studentSubjectNameMap .put(studentId, subjectName);
            studentDisplayList    .add(studentName + " (" + subjectName + ")");
            studentIdList         .add(studentId);
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                studentDisplayList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studentSpinner.setAdapter(adapter);
        studentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                selectedStudentId   = studentIdList.get(pos);
                selectedSubjectId   = studentSubjectMap.get(selectedStudentId);
                selectedSubjectName = studentSubjectNameMap.get(selectedStudentId);
                subjectNameLabel.setText("Grading for: " + selectedSubjectName);
            }
            @Override public void onNothingSelected(AdapterView<?> parent) { /* no-op */ }
        });
    }


    private void setupSubmitButton() {
        findViewById(R.id.submitGradesButton).setOnClickListener(v -> {
            String tpGrade = tpGradeInput.getText().toString().trim();
            String tdGrade = tdGradeInput.getText().toString().trim();
            String examGrade = examGradeInput.getText().toString().trim();

            if (tpGrade.isEmpty() || tdGrade.isEmpty() || examGrade.isEmpty()) {
                showToast("Please enter all grades!");
                return;
            }

            try {
                submitGrades(
                        Double.parseDouble(tpGrade),
                        Double.parseDouble(tdGrade),
                        Double.parseDouble(examGrade)
                );
            } catch (NumberFormatException e) {
                showToast("Please enter valid numbers for grades!");
                Log.e("GRADE_INPUT", "Invalid grade format", e);
            }
        });
    }

    private void submitGrades(double tpGrade, double tdGrade, double examGrade) {
        String teacherId = SessionManager.getInstance().getCurrentUser().getId();
        String groupId = getGroupIdForStudent(String.valueOf(selectedStudentId));

        if (groupId == null) {
            showToast("Failed to retrieve group information");
            return;
        }

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_RECORD_STUDENT_ID, selectedStudentId);
        values.put(DatabaseHelper.COLUMN_RECORD_TEACHER_ID, teacherId);
        values.put(DatabaseHelper.COLUMN_RECORD_SUBJECT_ID, selectedSubjectId);
        values.put(DatabaseHelper.COLUMN_RECORD_GROUP_ID, groupId);
        values.put(DatabaseHelper.COLUMN_RECORD_TP_NOTE, tpGrade);
        values.put(DatabaseHelper.COLUMN_RECORD_TD_NOTE, tdGrade);
        values.put(DatabaseHelper.COLUMN_RECORD_EXAM_NOTE, examGrade);

        String recordId = findExistingRecord(selectedStudentId, teacherId, selectedSubjectId, groupId);

        if (recordId != null) {
            updateExistingRecord(recordId, values);
        } else {
            insertNewRecord(values);
        }
    }

    private String findExistingRecord(long studentId, String teacherId, String subjectId, String groupId) {
        String[] columns = {DatabaseHelper.COLUMN_RECORD_ID};
        String selection = String.format("%s = ? AND %s = ? AND %s = ? AND %s = ?",
                DatabaseHelper.COLUMN_RECORD_STUDENT_ID,
                DatabaseHelper.COLUMN_RECORD_TEACHER_ID,
                DatabaseHelper.COLUMN_RECORD_SUBJECT_ID,
                DatabaseHelper.COLUMN_RECORD_GROUP_ID);

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_STUDENT_RECORDS,
                columns,
                selection,
                new String[]{String.valueOf(studentId), teacherId, subjectId, groupId},
                null, null, null);

        String recordId = null;
        if (cursor.moveToFirst()) {
            recordId = cursor.getString(0);
        }
        cursor.close();
        return recordId;
    }

    private void updateExistingRecord(String recordId, ContentValues values) {
        int rowsUpdated = db.update(
                DatabaseHelper.TABLE_STUDENT_RECORDS,
                values,
                DatabaseHelper.COLUMN_RECORD_ID + " = ?",
                new String[]{recordId});

        if (rowsUpdated > 0) {
            showToast("Grades updated successfully!");
            clearInputFields();
        } else {
            showToast("Failed to update grades");
        }
    }

    private void insertNewRecord(ContentValues values) {
        values.put(DatabaseHelper.COLUMN_RECORD_ID, System.currentTimeMillis());
        long newId = db.insert(DatabaseHelper.TABLE_STUDENT_RECORDS, null, values);

        if (newId != -1) {
            showToast("New grades record created!");
            clearInputFields();
        } else {
            showToast("Failed to create new grades record");
        }
    }

    private String getGroupIdForStudent(String studentId) {
        String[] columns = {DatabaseHelper.COLUMN_GROUP_ID};
        String selection = DatabaseHelper.COLUMN_STUDENTS_ID + " = ?";

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_STUDENTS,
                columns,
                selection,
                new String[]{studentId},
                null, null, null);

        String groupId = null;
        if (cursor.moveToFirst()) {
            groupId = cursor.getString(0);
        }
        cursor.close();
        return groupId;
    }

    private void clearInputFields() {
        tpGradeInput.setText("");
        tdGradeInput.setText("");
        examGradeInput.setText("");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null) {
            db.close();
        }
    }
}