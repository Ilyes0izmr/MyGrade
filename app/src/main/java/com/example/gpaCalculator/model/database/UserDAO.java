package com.example.gpaCalculator.model.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.gpaCalculator.model.entities.Admin;
import com.example.gpaCalculator.model.entities.Student;
import com.example.gpaCalculator.model.entities.Teacher;
import com.example.gpaCalculator.model.entities.User;

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

    public User getUserByUsername(String username) {
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        String[] columns = {
                DatabaseHelper.COLUMN_USERS_ID,
                DatabaseHelper.COLUMN_USERS_USERNAME,
                DatabaseHelper.COLUMN_USERS_EMAIL,
                DatabaseHelper.COLUMN_USERS_PASSWORD_HASH,
                DatabaseHelper.COLUMN_USERS_PHONE,
                DatabaseHelper.COLUMN_USERS_SIGNUP_DATE,
                DatabaseHelper.COLUMN_USERS_ENROLLMENT_ID
        };

        String selection = DatabaseHelper.COLUMN_USERS_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        User user = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERS_ID));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERS_EMAIL));
            String passwordHash = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERS_PASSWORD_HASH));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERS_PHONE));
            String signupDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERS_SIGNUP_DATE));
            int enrollmentId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERS_ENROLLMENT_ID));

            // Determine the role and create the appropriate user object
            String role = getUserRole(enrollmentId, db);
            switch (role) {
                case "Student":
                    user = new Student(id, username, email, passwordHash, phone, signupDate, enrollmentId, "", "", "", 0);
                    break;
                case "Teacher":
                    user = new Teacher(id, username, email, passwordHash, phone, signupDate, enrollmentId, "", 0);
                    break;
                case "Admin":
                    user = new Admin(id, username, email, passwordHash, phone, signupDate, enrollmentId, "");
                    break;
                default:
                    user = new User(id, username, email, passwordHash, phone, signupDate, enrollmentId);
            }
        }
        cursor.close();
        return user;
    }

    private String getUserRole(int enrollmentId, SQLiteDatabase db) {
        String[] columns = {
                DatabaseHelper.COLUMN_ENROLLMENT_STUDENT_ID,
                DatabaseHelper.COLUMN_ENROLLMENT_PROFESSOR_ID,
                DatabaseHelper.COLUMN_ENROLLMENT_ADMIN_UNIV_ID
        };

        String selection = DatabaseHelper.COLUMN_ENROLLMENT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(enrollmentId)};

        Cursor cursor = db.query(DatabaseHelper.TABLE_ENROLLMENT, columns, selection, selectionArgs, null, null, null);

        String role = "Unknown";
        if (cursor.moveToFirst()) {
            String studentId = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ENROLLMENT_STUDENT_ID));
            String professorId = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ENROLLMENT_PROFESSOR_ID));
            String adminUnivId = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ENROLLMENT_ADMIN_UNIV_ID));

            if (studentId != null) {
                role = "Student";
            } else if (professorId != null) {
                role = "Teacher";
            } else if (adminUnivId != null) {
                role = "Admin";
            }
        }
        cursor.close();
        return role;
    }

    public String[] getEnrollmentDetails(int enrollmentId) {
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        String[] columns = {
                DatabaseHelper.COLUMN_ENROLLMENT_FIRST_NAME,
                DatabaseHelper.COLUMN_ENROLLMENT_LAST_NAME,
                DatabaseHelper.COLUMN_ENROLLMENT_BIRTHDATE
        };

        String selection = DatabaseHelper.COLUMN_ENROLLMENT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(enrollmentId)};

        Cursor cursor = db.query(DatabaseHelper.TABLE_ENROLLMENT, columns, selection, selectionArgs, null, null, null);

        String[] details = new String[3];
        if (cursor.moveToFirst()) {
            details[0] = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ENROLLMENT_FIRST_NAME));
            details[1] = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ENROLLMENT_LAST_NAME));
            details[2] = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ENROLLMENT_BIRTHDATE));
        }
        cursor.close();
        return details;
    }

}
