package com.example.gpaCalculator.model.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EnrollmentDAO {

    private final DatabaseHelper dataBaseHelper;

    public EnrollmentDAO(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public boolean isMatriculeEnrolled(String matricule) {
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

        try (Cursor cursor = database.query(
                DatabaseHelper.TABLE_ENROLLMENT,  // The table name
                new String[]{DatabaseHelper.COLUMN_ENROLLMENT_ID},
                DatabaseHelper.COLUMN_ENROLLMENT_STUDENT_ID + "=? OR " +
                        DatabaseHelper.COLUMN_ENROLLMENT_PROFESSOR_ID + "=?",
                new String[]{matricule, matricule},
                null,
                null,
                null
        )) {
            return cursor.getCount() > 0;  // Return true if found, false otherwise
        }
    }


}
