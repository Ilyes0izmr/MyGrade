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
        long[] enrollmentIds = insertDummyEnrollment(db); // Insert enrollments first
        insertDummyUsers(db, enrollmentIds); // Insert users with valid enrollment IDs
        long[] groupIds = insertDummyGroups(db); // Insert groups
        insertDummyStudents(db, enrollmentIds, groupIds); // Insert students
        insertDummyTeachers(db, enrollmentIds); // Insert teachers
        insertDummyTeacherSubjects(db); // Insert teacher-subject relationships
        insertDummyTeacherGroups(db); // Insert teacher-group relationships
        insertDummyAdmins(db); // Insert admins
        Log.d("SEED", "Dummy data insertion completed.");
    }
    private void clearAllTables(SQLiteDatabase db) {
        Log.d("SEED", "Clearing all tables...");
        dbHelper.onUpgrade(db, 1, 1);
    }



    private long[] insertDummyEnrollment(SQLiteDatabase db) {
        String[][] enrollments = {
                // Students (student_id is populated, professor_id is null)
                {"john@example.com", "John", "Doe", "2000-01-01", "Male", "STU123", null},
                {"jane@example.com", "Jane", "Smith", "1999-05-15", "Female", "STU124", null},
                {"alice@example.com", "Alice", "Jones", "1998-11-10", "Female", "STU125", null},
                {"bob@example.com", "Bob", "Brown", "1997-07-20", "Male", "STU126", null},
                // Teachers (professor_id is populated, student_id is null)
                {"prof1@example.com", "Michael", "Johnson", "1980-03-10", "Male", null, "PROF999"},
                {"prof2@example.com", "Sarah", "Davis", "1982-07-25", "Female", null, "PROF998"},
                {"prof3@example.com", "David", "Wilson", "1978-12-05", "Male", null, "PROF997"},
                {"prof4@example.com", "Emily", "Taylor", "1985-09-15", "Female", null, "PROF996"}
        };

        long[] enrollmentIds = new long[enrollments.length];
        for (int i = 0; i < enrollments.length; i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_ENROLLMENT_EMAIL, enrollments[i][0]);
            values.put(DatabaseHelper.COLUMN_ENROLLMENT_FIRST_NAME, enrollments[i][1]);
            values.put(DatabaseHelper.COLUMN_ENROLLMENT_LAST_NAME, enrollments[i][2]);
            values.put(DatabaseHelper.COLUMN_ENROLLMENT_BIRTHDATE, enrollments[i][3]);
            values.put(DatabaseHelper.COLUMN_ENROLLMENT_GENDER, enrollments[i][4]);
            values.put(DatabaseHelper.COLUMN_ENROLLMENT_STUDENT_ID, enrollments[i][5]); // Can be null for teachers
            values.put(DatabaseHelper.COLUMN_ENROLLMENT_PROFESSOR_ID, enrollments[i][6]); // Can be null for students
            enrollmentIds[i] = db.insert(DatabaseHelper.TABLE_ENROLLMENT, null, values);
            Log.d("SEED", "Inserted dummy enrollment with ID: " + enrollmentIds[i]);
        }
        return enrollmentIds;
    }

    private void insertDummyUsers(SQLiteDatabase db, long[] enrollmentIds) {
        String[][] users = {
                {"john_doe", "john@example.com", "123", "0555123456", "2025-04-05"},
                {"jane_smith", "jane@example.com", "456", "0555678901", "2025-04-06"},
                {"alice_jones", "alice@example.com", "789", "0555112233", "2025-04-07"},
                {"bob_brown", "bob@example.com", "101", "0555445566", "2025-04-08"},
                {"prof1", "prof1@example.com", "prof1", "0555123123", "2025-04-09"},
                {"prof2", "prof2@example.com", "prof2", "0555234234", "2025-04-10"},
                {"prof3", "prof3@example.com", "prof3", "0555345345", "2025-04-11"},
                {"prof4", "prof4@example.com", "prof4", "0555456456", "2025-04-12"}
        };

        for (int i = 0; i < users.length; i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_USERS_USERNAME, users[i][0]);
            values.put(DatabaseHelper.COLUMN_USERS_EMAIL, users[i][1]);
            values.put(DatabaseHelper.COLUMN_USERS_PASSWORD_HASH, users[i][2]);
            values.put(DatabaseHelper.COLUMN_USERS_PHONE, users[i][3]);
            values.put(DatabaseHelper.COLUMN_USERS_SIGNUP_DATE, users[i][4]);
            values.put(DatabaseHelper.COLUMN_USERS_ENROLLMENT_ID, enrollmentIds[i]); // Assign valid enrollment_id
            long id = db.insert(DatabaseHelper.TABLE_USERS, null, values);
            Log.d("SEED", "Inserted dummy user with ID: " + id);
        }
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
                {"STU123", "MIT", "Engineering", "1"},
                {"STU124", "Harvard", "Engineering", "2"},
                {"STU125", "Stanford", "Science", "3"},
                {"STU126", "Caltech", "Science", "4"}
        };

        for (int i = 0; i < students.length; i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_STUDENTS_ID, userIds[i]);
            values.put(DatabaseHelper.COLUMN_STUDENT_ID, students[i][0]);
            values.put(DatabaseHelper.COLUMN_UNIVERSITY, students[i][1]);
            values.put(DatabaseHelper.COLUMN_FACULTY, students[i][2]);
            values.put(DatabaseHelper.COLUMN_GROUP_ID, groupIds[Integer.parseInt(students[i][3]) - 1]);
            db.insert(DatabaseHelper.TABLE_STUDENTS, null, values);
            Log.d("SEED", "Inserted dummy student");
        }
    }

    private void insertDummyTeachers(SQLiteDatabase db, long[] userIds) {
        String[][] teachers = {
                {"PROF999", "12"},
                {"PROF998", "10"},
                {"PROF997", "15"},
                {"PROF996", "20"}
        };

        for (int i = 0; i < teachers.length; i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_TEACHERS_ID, userIds[i + 4]); // Skip first 4 users (students)
            values.put(DatabaseHelper.COLUMN_TEACHERS_PROFESSOR_ID, teachers[i][0]);
            values.put(DatabaseHelper.COLUMN_TEACHERS_HOURS_PER_WEEK, Integer.parseInt(teachers[i][1]));
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

    private void insertDummyAdmins(SQLiteDatabase db) {
        String[][] admins = {
                {"admin@example.com", "hashed_admin_password_123", "0555987654", "2025-01-01"}
        };

        for (String[] admin : admins) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_ADMINS_EMAIL, admin[0]);
            values.put(DatabaseHelper.COLUMN_ADMINS_PASSWORD_HASH, admin[1]);
            values.put(DatabaseHelper.COLUMN_ADMINS_PHONE, admin[2]);
            values.put(DatabaseHelper.COLUMN_ADMINS_SIGNUP_DATE, admin[3]);
            long id = db.insert(DatabaseHelper.TABLE_ADMINS, null, values);
            Log.d("SEED", "Inserted dummy admin with ID: " + id);
        }
    }
}