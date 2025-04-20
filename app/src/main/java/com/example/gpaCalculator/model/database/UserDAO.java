package com.example.gpaCalculator.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.gpaCalculator.model.entities.User;

public class UserDAO {
    private static final String TAG = "UserDAO";
    private DatabaseHelper dbHelper;

    // Constructor
    public UserDAO(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    /**
     * Validates a user by checking if the username exists and the password matches.
     *
     * @param username The username of the user.
     * @param password The password to validate.
     * @return True if the username exists and the password matches, false otherwise.
     */
    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        boolean isValid = false;

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,
                null,
                DatabaseHelper.COLUMN_USERS_USERNAME + " = ? AND " + DatabaseHelper.COLUMN_USERS_PASSWORD_HASH + " = ?",
                new String[]{username, password},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            isValid = true; // Username and password match
            cursor.close();
        }

        db.close();
        return isValid;
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to retrieve.
     * @return A User object, or null if no user is found.
     */
    public User getUserByUsername(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,
                null,
                DatabaseHelper.COLUMN_USERS_USERNAME + " = ?",
                new String[]{username},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            user = cursorToUser(cursor);
            cursor.close();
        }

        db.close();
        return user;
    }

    /**
     * Inserts a new user into the database.
     *
     * @param user The User object to insert.
     * @return The row ID of the newly inserted user, or -1 if insertion failed.
     */
    public boolean insertUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Populate the ContentValues object with user data
        values.put(DatabaseHelper.COLUMN_USERS_ID, user.getId());
        values.put(DatabaseHelper.COLUMN_USERS_USERNAME, user.getUsername());
        values.put(DatabaseHelper.COLUMN_USERS_EMAIL, user.getEmail());
        values.put(DatabaseHelper.COLUMN_USERS_PASSWORD_HASH, user.getPasswordHash());
        values.put(DatabaseHelper.COLUMN_USERS_PHONE, user.getPhone());
        values.put(DatabaseHelper.COLUMN_USERS_SIGNUP_DATE, user.getSignupDate());
        values.put("role_type", user.getRoleType()); // Add role_type

        // Attempt to insert the user into the database
        long result = db.insert(DatabaseHelper.TABLE_USERS, null, values);

        // Close the database connection
        db.close();

        // Return true if the insertion was successful, false otherwise
        if (result == -1) {
            Log.e(TAG, "Failed to insert user: " + user.getUsername());
            return false;
        } else {
            Log.d(TAG, "User inserted successfully: " + user.getUsername());
            return true;
        }
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user The User object with updated information.
     * @return The number of rows affected (should be 1 if successful).
     */
    public int updateUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COLUMN_USERS_USERNAME, user.getUsername());
        values.put(DatabaseHelper.COLUMN_USERS_EMAIL, user.getEmail());
        values.put(DatabaseHelper.COLUMN_USERS_PASSWORD_HASH, user.getPasswordHash());
        values.put(DatabaseHelper.COLUMN_USERS_PHONE, user.getPhone());
        values.put(DatabaseHelper.COLUMN_USERS_SIGNUP_DATE, user.getSignupDate());
        values.put("role_type", user.getRoleType()); // Update role_type

        int result = db.update(
                DatabaseHelper.TABLE_USERS,
                values,
                DatabaseHelper.COLUMN_USERS_ID + " = ?",
                new String[]{user.getId()}
        );

        if (result > 0) {
            Log.d(TAG, "User updated successfully: " + user.getUsername());
        } else {
            Log.e(TAG, "Failed to update user: " + user.getUsername());
        }

        db.close();
        return result;
    }

    /**
     * Deletes a user from the database.
     *
     * @param userId The ID of the user to delete.
     * @return The number of rows affected (should be 1 if successful).
     */
    public int deleteUser(String userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int result = db.delete(
                DatabaseHelper.TABLE_USERS,
                DatabaseHelper.COLUMN_USERS_ID + " = ?",
                new String[]{userId}
        );

        if (result > 0) {
            Log.d(TAG, "User deleted successfully: ID = " + userId);
        } else {
            Log.e(TAG, "Failed to delete user: ID = " + userId);
        }

        db.close();
        return result;
    }

    /**
     * Converts a database cursor to a User object.
     *
     * @param cursor The database cursor containing user data.
     * @return A User object.
     */
    private User cursorToUser(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERS_ID));
        String username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERS_USERNAME));
        String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERS_EMAIL));
        String passwordHash = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERS_PASSWORD_HASH));
        String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERS_PHONE));
        String signupDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERS_SIGNUP_DATE));
        String roleType = cursor.getString(cursor.getColumnIndexOrThrow("role_type")); // Retrieve role_type

        return new User(id, username, email, passwordHash, phone, signupDate, roleType); // Include roleType
    }
}