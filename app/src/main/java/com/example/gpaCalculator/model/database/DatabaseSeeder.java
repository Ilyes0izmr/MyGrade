package com.example.gpaCalculator.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseSeeder {
    private final DatabaseHelper dbHelper;

    public DatabaseSeeder(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void seedDatabase() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.d("SEED", "Clearing all tables...");
        clearAllTables(db); // Clear all existing data
        Log.d("SEED", "Inserting dummy data...");
        insertDummyData(db); // Insert fresh dummy data
        db.close();
    }

    private void insertDummyData(SQLiteDatabase db) {
        long[] userIds = insertDummyUsers(db); // Insert users first
        long[] groupIds = insertDummyGroups(db); // Insert groups
        insertDummyStudents(db, userIds, groupIds); // Insert students with user IDs
        insertDummyTeachers(db, userIds); // Insert teachers with user IDs
        insertDummyTeacherSubjects(db); // Insert teacher-subject relationships
        insertDummyTeacherGroups(db); // Insert teacher-group relationships
        Log.d("SEED", "Dummy data insertion completed.");
    }

    private void clearAllTables(SQLiteDatabase db) {
        Log.d("SEED", "Clearing all tables...");
        dbHelper.onUpgrade(db, 1, 1);
    }

    private long[] insertDummyUsers(SQLiteDatabase db) {
        String[][] users = {
                // Students
                {"john_doe", "john@example.com", "123", "0555123456", "2025-04-05", "student"},
                {"jane_smith", "jane@example.com", "456", "0555678901", "2025-04-06", "student"},
                {"alice_jones", "alice@example.com", "789", "0555112233", "2025-04-07", "student"},
                {"bob_brown", "bob@example.com", "101", "0555445566", "2025-04-08", "student"},
                // Teachers
                {"prof1", "prof1@example.com", "prof1", "0555123123", "2025-04-09", "teacher"},
                {"prof2", "prof2@example.com", "prof2", "0555234234", "2025-04-10", "teacher"},
                {"prof3", "prof3@example.com", "prof3", "0555345345", "2025-04-11", "teacher"},
                {"prof4", "prof4@example.com", "prof4", "0555456456", "2025-04-12", "teacher"}
        };

        long[] userIds = new long[users.length];
        for (int i = 0; i < users.length; i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_USERS_USERNAME, users[i][0]);
            values.put(DatabaseHelper.COLUMN_USERS_EMAIL, users[i][1]);
            values.put(DatabaseHelper.COLUMN_USERS_PASSWORD_HASH, users[i][2]);
            values.put(DatabaseHelper.COLUMN_USERS_PHONE, users[i][3]);
            values.put(DatabaseHelper.COLUMN_USERS_SIGNUP_DATE, users[i][4]);
            values.put("role_type", users[i][5]); // Assign role type
            userIds[i] = db.insert(DatabaseHelper.TABLE_USERS, null, values);
            Log.d("SEED", "Inserted dummy user with ID: " + userIds[i]);
        }
        return userIds;
    }

    private long[] insertDummyGroups(SQLiteDatabase db) {
        String[][] groups = {
                {"A", "L3", "Computer Science", "Engineering"},
                {"B", "L3", "Electrical Engineering", "Engineering"},
                {"C", "M1", "Data Science", "Science"},
                {"D", "M2", "Mathematics", "Science"}
        };

        long[] groupIds = new long[groups.length];
        for (int i = 0; i < groups.length; i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_GROUP_NAME, groups[i][0]);
            values.put(DatabaseHelper.COLUMN_GROUP_LEVEL, groups[i][1]);
            values.put(DatabaseHelper.COLUMN_GROUP_SPECIALTY, groups[i][2]);
            values.put(DatabaseHelper.COLUMN_GROUP_DEPARTMENT, groups[i][3]);
            groupIds[i] = db.insert(DatabaseHelper.TABLE_GROUPS, null, values);
            Log.d("SEED", "Inserted dummy group with ID: " + groupIds[i]);
        }
        return groupIds;
    }

    private void insertDummyStudents(SQLiteDatabase db, long[] userIds, long[] groupIds) {
        String[][] students = {
                // student_id, first_name, last_name, birthdate, gender, university, faculty, group_index
                {"STU123", "John", "Doe", "2000-01-01", "Male", "MIT", "Engineering", "0"},
                {"STU124", "Jane", "Smith", "1999-05-15", "Female", "Harvard", "Engineering", "1"},
                {"STU125", "Alice", "Jones", "1998-11-10", "Female", "Stanford", "Science", "2"},
                {"STU126", "Bob", "Brown", "1997-07-20", "Male", "Caltech", "Science", "3"}
        };

        for (int i = 0; i < students.length; i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_STUDENTS_ID, userIds[i]); // Use user ID
            values.put(DatabaseHelper.COLUMN_STUDENT_ID, students[i][0]);
            values.put(DatabaseHelper.COLUMN_STUDENT_FIRST_NAME, students[i][1]);
            values.put(DatabaseHelper.COLUMN_STUDENT_LAST_NAME, students[i][2]);
            values.put(DatabaseHelper.COLUMN_STUDENT_BIRTHDATE, students[i][3]);
            values.put(DatabaseHelper.COLUMN_STUDENT_GENDER, students[i][4]);
            values.put(DatabaseHelper.COLUMN_UNIVERSITY, students[i][5]);
            values.put(DatabaseHelper.COLUMN_FACULTY, students[i][6]);
            values.put(DatabaseHelper.COLUMN_GROUP_ID, groupIds[Integer.parseInt(students[i][7])]);
            db.insert(DatabaseHelper.TABLE_STUDENTS, null, values);
            Log.d("SEED", "Inserted dummy student");
        }
    }

    private void insertDummyTeachers(SQLiteDatabase db, long[] userIds) {
        String[][] teachers = {
                // professor_id, first_name, last_name, birthdate, gender, hours_per_week
                {"PROF999", "Michael", "Johnson", "1980-03-10", "Male", "12"},
                {"PROF998", "Sarah", "Davis", "1982-07-25", "Female", "10"},
                {"PROF997", "David", "Wilson", "1978-12-05", "Male", "15"},
                {"PROF996", "Emily", "Taylor", "1985-09-15", "Female", "20"}
        };

        for (int i = 0; i < teachers.length; i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_TEACHERS_ID, userIds[i + 4]); // Skip first 4 users (students)
            values.put(DatabaseHelper.COLUMN_TEACHERS_PROFESSOR_ID, teachers[i][0]);
            values.put(DatabaseHelper.COLUMN_TEACHER_FIRST_NAME, teachers[i][1]);
            values.put(DatabaseHelper.COLUMN_TEACHER_LAST_NAME, teachers[i][2]);
            values.put(DatabaseHelper.COLUMN_TEACHER_BIRTHDATE, teachers[i][3]);
            values.put(DatabaseHelper.COLUMN_TEACHER_GENDER, teachers[i][4]);
            values.put(DatabaseHelper.COLUMN_TEACHERS_HOURS_PER_WEEK, Integer.parseInt(teachers[i][5]));
            db.insert(DatabaseHelper.TABLE_TEACHERS, null, values);
            Log.d("SEED", "Inserted dummy teacher");
        }
    }

    private void insertDummyTeacherSubjects(SQLiteDatabase db) {
        String[][] teacherSubjects = {
                {"1", "Operating Systems"},
                {"1", "Algorithms"},
                {"2", "Electronics"},
                {"3", "Machine Learning"},
                {"4", "Advanced Mathematics"}
        };

        for (String[] subject : teacherSubjects) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_TEACHER_ID, Integer.parseInt(subject[0]));
            values.put(DatabaseHelper.COLUMN_SUBJECT_NAME, subject[1]);
            db.insert(DatabaseHelper.TABLE_TEACHER_SUBJECTS, null, values);
            Log.d("SEED", "Inserted dummy teacher subject");
        }
    }

    private void insertDummyTeacherGroups(SQLiteDatabase db) {
        String[][] teacherGroups = {
                {"1", "1"},
                {"1", "2"},
                {"2", "2"},
                {"3", "3"},
                {"4", "4"}
        };

        for (String[] group : teacherGroups) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_TEACHER_ID, Integer.parseInt(group[0]));
            values.put(DatabaseHelper.COLUMN_GROUP_ID, Integer.parseInt(group[1]));
            db.insert(DatabaseHelper.TABLE_TEACHER_GROUPS, null, values);
            Log.d("SEED", "Inserted dummy teacher group");
        }
    }
}