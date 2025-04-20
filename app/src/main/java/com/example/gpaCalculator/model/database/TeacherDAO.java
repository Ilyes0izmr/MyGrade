package com.example.gpaCalculator.model.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gpaCalculator.model.entities.Teacher;

import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    private DatabaseHelper dbHelper;

    public TeacherDAO(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    /**
     * Checks if a matricule exists in the teachers table.
     *
     * @param matricule The matricule to check.
     * @return True if the matricule exists, false otherwise.
     */
    public boolean isMatriculeExists(String matricule) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        boolean exists = false;

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_TEACHERS,
                null,
                DatabaseHelper.COLUMN_TEACHERS_PROFESSOR_ID + " = ?",
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
                DatabaseHelper.TABLE_TEACHERS,
                new String[]{DatabaseHelper.COLUMN_TEACHERS_ID},
                DatabaseHelper.COLUMN_TEACHERS_PROFESSOR_ID + " = ?",
                new String[]{matricule},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            userId = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TEACHERS_ID));
            cursor.close();
        }

        db.close();
        return userId;
    }

    public Teacher getTeacherById(String userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_TEACHERS,
                null,
                DatabaseHelper.COLUMN_TEACHERS_ID + " = ?",
                new String[]{userId},
                null, null, null
        );

        Teacher teacher = null;
        if (cursor != null && cursor.moveToFirst()) {
            String professorId = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TEACHERS_PROFESSOR_ID));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TEACHER_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TEACHER_LAST_NAME));
            String birthdate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TEACHER_BIRTHDATE));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TEACHER_GENDER));
            String hoursPerWeek = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TEACHERS_HOURS_PER_WEEK)));

            // Create the Teacher object
            teacher = new Teacher(
                    userId, // id
                    null, // username (not stored in teachers table)
                    null, // email (not stored in teachers table)
                    null, // passwordHash (not stored in teachers table)
                    null, // phone (not stored in teachers table)
                    null, // signupDate (not stored in teachers table)
                    "teacher",
                    professorId,
                    firstName,
                    lastName,
                    birthdate,
                    gender,
                    hoursPerWeek
            );

            cursor.close();
        }

        db.close();
        return teacher;
    }

}