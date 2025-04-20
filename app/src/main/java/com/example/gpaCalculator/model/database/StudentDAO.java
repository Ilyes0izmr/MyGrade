package com.example.gpaCalculator.model.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gpaCalculator.model.entities.Student;

public class StudentDAO {
    private DatabaseHelper dbHelper;

    public StudentDAO(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    /**
     * Checks if a matricule exists in the students table.
     *
     * @param matricule The matricule to check.
     * @return True if the matricule exists, false otherwise.
     */
    public boolean isMatriculeExists(String matricule) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        boolean exists = false;

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_STUDENTS,
                null,
                DatabaseHelper.COLUMN_STUDENT_ID + " = ?",
                new String[]{matricule},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            exists = true;
            cursor.close();
        }

        db.close();
        return exists;
    }

    public String getUserIdByMatricule(String matricule) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String userId = null;

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_STUDENTS,
                new String[]{DatabaseHelper.COLUMN_STUDENTS_ID},
                DatabaseHelper.COLUMN_STUDENT_ID + " = ?",
                new String[]{matricule},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STUDENTS_ID));
            cursor.close();
        }

        db.close();
        return userId;
    }

    public Student getStudentById(String userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_STUDENTS,
                null,
                DatabaseHelper.COLUMN_STUDENTS_ID + " = ?",
                new String[]{userId},
                null, null, null
        );

        Student student = null;
        if (cursor != null && cursor.moveToFirst()) {
            String studentId = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STUDENT_ID));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STUDENT_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STUDENT_LAST_NAME));
            String birthdate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STUDENT_BIRTHDATE));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STUDENT_GENDER));
            String university = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_UNIVERSITY));
            String faculty = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FACULTY));
            String groupId = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GROUP_ID)));

            // Create the Student object
            student = new Student(
                    userId, // id
                    null, // username (not stored in students table)
                    null, // email (not stored in students table)
                    null, // passwordHash (not stored in students table)
                    null, // phone (not stored in students table)
                    null, // signupDate (not stored in students table)
                    "student", // roleType (always "student" for students)
                    studentId,
                    firstName,
                    lastName,
                    birthdate,
                    gender,
                    university,
                    faculty,
                    groupId
            );

            cursor.close();
        }

        db.close();
        return student;
    }
}
