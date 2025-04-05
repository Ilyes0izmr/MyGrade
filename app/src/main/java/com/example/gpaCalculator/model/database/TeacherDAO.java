package com.example.gpaCalculator.model.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    private final DatabaseHelper dbHelper;

    public TeacherDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Fetch the classes taught by a teacher (subject + group combinations).
     *
     * @param teacherId The ID of the teacher.
     * @return A list of strings in the format "Subject - Group".
     */
    public List<String> getClassesTaught(int teacherId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<String> classesTaught = new ArrayList<>();

        // Query to join teacher_subjects and teacher_groups tables
        String query = "SELECT ts." + DatabaseHelper.COLUMN_SUBJECT_NAME + ", g." + DatabaseHelper.COLUMN_GROUP_NAME +
                " FROM " + DatabaseHelper.TABLE_TEACHER_SUBJECTS + " ts" +
                " INNER JOIN " + DatabaseHelper.TABLE_TEACHER_GROUPS + " tg" +
                " ON ts." + DatabaseHelper.COLUMN_TEACHER_ID + " = tg." + DatabaseHelper.COLUMN_TEACHER_ID +
                " INNER JOIN " + DatabaseHelper.TABLE_GROUPS + " g" +
                " ON tg." + DatabaseHelper.COLUMN_GROUP_ID + " = g." + DatabaseHelper.COLUMN_GROUPS_ID +
                " WHERE ts." + DatabaseHelper.COLUMN_TEACHER_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(teacherId)});

        if (cursor.moveToFirst()) {
            do {
                String subject = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SUBJECT_NAME));
                String group = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GROUP_NAME));
                classesTaught.add(subject + " - " + group);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return classesTaught;
    }

    /**
     * Fetch the levels taught by a teacher.
     *
     * @param teacherId The ID of the teacher.
     * @return A list of unique levels taught by the teacher.
     */
    public List<String> getLevelsTaught(int teacherId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<String> levelsTaught = new ArrayList<>();

        // Query to fetch levels from groups linked to the teacher
        String query = "SELECT DISTINCT g." + DatabaseHelper.COLUMN_GROUP_LEVEL +
                " FROM " + DatabaseHelper.TABLE_TEACHER_GROUPS + " tg" +
                " INNER JOIN " + DatabaseHelper.TABLE_GROUPS + " g" +
                " ON tg." + DatabaseHelper.COLUMN_GROUP_ID + " = g." + DatabaseHelper.COLUMN_GROUPS_ID +
                " WHERE tg." + DatabaseHelper.COLUMN_TEACHER_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(teacherId)});

        if (cursor.moveToFirst()) {
            do {
                String level = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GROUP_LEVEL));
                levelsTaught.add(level);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return levelsTaught;
    }
}