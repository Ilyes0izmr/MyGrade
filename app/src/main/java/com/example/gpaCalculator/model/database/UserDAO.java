package com.example.gpaCalculator.model.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
public class UserDAO {

    private final DatabaseHelper dataBaseHelper;

    public UserDAO(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    // Validate if the user with the provided email exists in the database
    public boolean validateUser(String input, String password) {
        SQLiteDatabase dataBase = dataBaseHelper.getReadableDatabase();

        // Log the input parameters for debugging
        Log.d("UserDAO", "Validating user with input: " + input);

        try (Cursor cursor = dataBase.query(
                DatabaseHelper.TABLE_USERS,    // The table name
                new String[]{                  // the COLUMNS
                        DatabaseHelper.COLUMN_USERS_ID,
                        DatabaseHelper.COLUMN_USERS_PASSWORD_HASH
                },
                DatabaseHelper.COLUMN_USERS_USERNAME + "=? OR " +  //selection part
                        DatabaseHelper.COLUMN_USERS_EMAIL + "=?",
                new String[]{     //values to store the return of the selection
                        input,    //for username return
                        input     //for the email return
                },
                null,
                null,
                null)) {

            // Log the number of rows returned
            Log.d("UserDAO", "Number of rows returned: " + cursor.getCount());

            boolean isValid = false;
            if (cursor.moveToFirst()) {
                // Log the stored password for debugging
                @SuppressLint("Range") String storedPassword = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USERS_PASSWORD_HASH));
                Log.d("UserDAO", "Stored password: " + storedPassword);

                if (storedPassword.equals(password)) {
                    isValid = true;
                }
            }

            return isValid;

        } catch (Exception e) {
            Log.e("UserDAO", "Error validating user", e);
            return false;
        }

    }
    public boolean isUsernameOrEmailTaken(String username, String email) {
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

        String query = "SELECT " + DatabaseHelper.COLUMN_USERS_ID +
                " FROM " + DatabaseHelper.TABLE_USERS +
                " WHERE " + DatabaseHelper.COLUMN_USERS_USERNAME + "=? OR " +
                DatabaseHelper.COLUMN_USERS_EMAIL + "=?";

        try (Cursor cursor = database.rawQuery(query, new String[]{username, email})) {
            return cursor.getCount() > 0;
        }
    }
    public boolean insertUser(String username, String email, String password) {
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COLUMN_USERS_USERNAME, username);
        values.put(DatabaseHelper.COLUMN_USERS_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_USERS_PASSWORD_HASH, password); // Not hashed for now
        values.put(DatabaseHelper.COLUMN_USERS_PHONE, ""); // Default empty
        values.put(DatabaseHelper.COLUMN_USERS_SIGNUP_DATE, System.currentTimeMillis()); // Store timestamp

        long result = database.insert(DatabaseHelper.TABLE_USERS, null, values);
        return result != -1; // If result == -1, insertion failed
    }

    public boolean isMatriculeTaken(String matricule) {
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();

        // Query the users table for student_id
        String[] columnsUsers = {DatabaseHelper.COLUMN_USERS_ID};
        String selectionUsers = DatabaseHelper.COLUMN_STUDENT_ID + " = ?";
        String[] selectionArgsUsers = {matricule};

        Cursor cursorUsers = db.query(DatabaseHelper.TABLE_USERS, columnsUsers, selectionUsers, selectionArgsUsers, null, null, null);
        boolean isTakenInUsers = cursorUsers.getCount() > 0;
        cursorUsers.close();

        // Query the teachers table for professor_id
        String[] columnsTeachers = {DatabaseHelper.COLUMN_TEACHERS_ID};
        String selectionTeachers = DatabaseHelper.COLUMN_TEACHERS_PROFESSOR_ID + " = ?";
        String[] selectionArgsTeachers = {matricule};

        Cursor cursorTeachers = db.query(DatabaseHelper.TABLE_TEACHERS, columnsTeachers, selectionTeachers, selectionArgsTeachers, null, null, null);
        boolean isTakenInTeachers = cursorTeachers.getCount() > 0;
        cursorTeachers.close();

        // Return true if the matricule is found in either table
        return isTakenInUsers || isTakenInTeachers;
    }


}
