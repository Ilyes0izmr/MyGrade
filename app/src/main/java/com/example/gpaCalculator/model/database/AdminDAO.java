package com.example.gpaCalculator.model.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AdminDAO {
    private DatabaseHelper dbHelper;

    public AdminDAO(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    /**
     * Checks if a matricule exists in the admins table.
     *
     * @param matricule The matricule to check.
     * @return True if the matricule exists, false otherwise.
     */
    public boolean isMatriculeExists(String matricule) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        boolean exists = false;

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_ADMINS,
                null,
                DatabaseHelper.COLUMN_ADMINS_UNIV_ID + " = ?",
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
                DatabaseHelper.TABLE_ADMINS,
                new String[]{DatabaseHelper.COLUMN_ADMINS_ID},
                DatabaseHelper.COLUMN_ADMINS_UNIV_ID + " = ?",
                new String[]{matricule},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            userId = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ADMINS_ID));
            cursor.close();
        }

        db.close();
        return userId;
    }
}
