package com.example.gpaCalculator.model.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyGrades.db";
    private static final int DATABASE_VERSION = 1;



    //USER TABLE ==========================================================================
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERS_ID = "id";
    public static final String COLUMN_USERS_USERNAME = "username";
    public static final String COLUMN_USERS_EMAIL = "email";
    public static final String COLUMN_USERS_PASSWORD_HASH = "password_hash";
    public static final String COLUMN_USERS_PHONE = "phone";
    public static final String COLUMN_USERS_SIGNUP_DATE = "signup_date";
    public static final String COLUMN_USERS_ENROLLMENT_ID = "enrollment_id";

    // ENROLLMENT Table =====================================================================
    public static final String TABLE_ENROLLMENT = "enrollment";
    public static final String COLUMN_ENROLLMENT_ID = "id";
    public static final String COLUMN_ENROLLMENT_EMAIL = "email";
    public static final String COLUMN_ENROLLMENT_FIRST_NAME = "first_name";
    public static final String COLUMN_ENROLLMENT_LAST_NAME = "last_name";
    public static final String COLUMN_ENROLLMENT_BIRTHDATE = "birthdate";
    public static final String COLUMN_ENROLLMENT_GENDER = "gender";
    public static final String COLUMN_ENROLLMENT_STUDENT_ID = "student_id";
    public static final String COLUMN_ENROLLMENT_PROFESSOR_ID = "professor_id";
    public static final String COLUMN_ENROLLMENT_ADMIN_UNIV_ID = "admin_univ_id"; // Updated column

    // GROUPS Table =========================================================================
    public static final String TABLE_GROUPS = "groups";
    public static final String COLUMN_GROUPS_ID = "id";
    public static final String COLUMN_GROUP_NAME = "name";
    public static final String COLUMN_GROUP_LEVEL = "level";
    public static final String COLUMN_GROUP_SPECIALTY = "specialty";
    public static final String COLUMN_GROUP_DEPARTMENT = "department";

    // STUDENTS Table ==============================================================
    public static final String TABLE_STUDENTS = "students";
    public static final String COLUMN_STUDENTS_ID = "id";
    public static final String COLUMN_STUDENT_ID = "student_id";
    public static final String COLUMN_UNIVERSITY = "university";
    public static final String COLUMN_FACULTY = "faculty";
    public static final String COLUMN_GROUP_ID = "group_id";  // FK to groups

    // TEACHERS Table =======================================================
    public static final String TABLE_TEACHERS = "teachers";
    public static final String COLUMN_TEACHERS_ID = "id";
    public static final String COLUMN_TEACHERS_PROFESSOR_ID = "professor_id";
    public static final String COLUMN_TEACHERS_HOURS_PER_WEEK = "hours_per_week";

    // TEACHER_SUBJECTS Table =========================================================
    public static final String TABLE_TEACHER_SUBJECTS = "teacher_subjects";
    public static final String COLUMN_TEACHER_ID = "teacher_id";
    public static final String COLUMN_SUBJECT_NAME = "subject_name";

    // TEACHER_GROUPS Table
    public static final String TABLE_TEACHER_GROUPS = "teacher_groups";

    // ADMINS Table =======================================================================
    public static final String TABLE_ADMINS = "admins";
    public static final String COLUMN_ADMINS_ID = "id";
    public static final String COLUMN_ADMINS_EMAIL = "email";
    public static final String COLUMN_ADMINS_PASSWORD_HASH = "password_hash";
    public static final String COLUMN_ADMINS_PHONE = "phone";
    public static final String COLUMN_ADMINS_SIGNUP_DATE = "signup_date";
    public static final String COLUMN_ADMINS_UNIV_ID = "admin_univ_id"; // New column

    // CREATE USERS
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " (" +
            COLUMN_USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USERS_USERNAME + " TEXT UNIQUE NOT NULL, " +
            COLUMN_USERS_EMAIL + " TEXT UNIQUE NOT NULL, " +
            COLUMN_USERS_PASSWORD_HASH + " TEXT NOT NULL, " +
            COLUMN_USERS_PHONE + " TEXT, " +
            COLUMN_USERS_SIGNUP_DATE + " TEXT NOT NULL, " +
            COLUMN_USERS_ENROLLMENT_ID + " INTEGER UNIQUE NOT NULL, " + // New column
            "FOREIGN KEY(" + COLUMN_USERS_ENROLLMENT_ID + ") REFERENCES " + TABLE_ENROLLMENT + "(" + COLUMN_ENROLLMENT_ID + ") ON DELETE CASCADE" +
            ");";

    // CREATE ENROLLMENT
    private static final String CREATE_TABLE_ENROLLMENT = "CREATE TABLE " + TABLE_ENROLLMENT + " (" +
            COLUMN_ENROLLMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ENROLLMENT_EMAIL + " TEXT UNIQUE NOT NULL, " +
            COLUMN_ENROLLMENT_FIRST_NAME + " TEXT NOT NULL, " +
            COLUMN_ENROLLMENT_LAST_NAME + " TEXT NOT NULL, " +
            COLUMN_ENROLLMENT_BIRTHDATE + " TEXT NOT NULL, " +
            COLUMN_ENROLLMENT_GENDER + " TEXT CHECK(" + COLUMN_ENROLLMENT_GENDER + " IN ('Male', 'Female')) NOT NULL, " +
            COLUMN_ENROLLMENT_STUDENT_ID + " TEXT UNIQUE, " +
            COLUMN_ENROLLMENT_PROFESSOR_ID + " TEXT UNIQUE, " +
            COLUMN_ENROLLMENT_ADMIN_UNIV_ID + " TEXT UNIQUE" +
            ");";

    // CREATE GROUPS TABLE
    private static final String CREATE_TABLE_GROUPS = "CREATE TABLE " + TABLE_GROUPS + " (" +
            COLUMN_GROUPS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_GROUP_NAME + " TEXT NOT NULL, " +
            COLUMN_GROUP_LEVEL + " TEXT CHECK(" + COLUMN_GROUP_LEVEL + " IN ('L1', 'L2', 'L3', 'M1', 'M2', 'PhD')) NOT NULL, " +
            COLUMN_GROUP_SPECIALTY + " TEXT NOT NULL, " +
            COLUMN_GROUP_DEPARTMENT + " TEXT NOT NULL" +
            ");";

    // CREATE STUDENTS TABLE
    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE " + TABLE_STUDENTS + " (" +
            COLUMN_STUDENTS_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_STUDENT_ID + " TEXT UNIQUE NOT NULL, " +
            COLUMN_UNIVERSITY + " TEXT NOT NULL, " +
            COLUMN_FACULTY + " TEXT NOT NULL, " +
            COLUMN_GROUP_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + COLUMN_STUDENTS_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USERS_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_GROUP_ID + ") REFERENCES " + TABLE_GROUPS + "(" + COLUMN_GROUPS_ID + ") ON DELETE CASCADE" +
            ");";

    // CREATE TEACHERS TABLE
    private static final String CREATE_TABLE_TEACHERS = "CREATE TABLE " + TABLE_TEACHERS + " (" +
            COLUMN_TEACHERS_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_TEACHERS_PROFESSOR_ID + " TEXT UNIQUE NOT NULL, " +
            COLUMN_TEACHERS_HOURS_PER_WEEK + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + COLUMN_TEACHERS_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USERS_ID + ") ON DELETE CASCADE" +
            ");";

    // CREATE TEACHER_SUBJECTS TABLE
    private static final String CREATE_TABLE_TEACHER_SUBJECTS = "CREATE TABLE " + TABLE_TEACHER_SUBJECTS + " (" +
            COLUMN_TEACHER_ID + " INTEGER NOT NULL, " +
            COLUMN_SUBJECT_NAME + " TEXT NOT NULL, " +
            "PRIMARY KEY (" + COLUMN_TEACHER_ID + ", " + COLUMN_SUBJECT_NAME + "), " +
            "FOREIGN KEY(" + COLUMN_TEACHER_ID + ") REFERENCES " + TABLE_TEACHERS + "(" + COLUMN_TEACHERS_ID + ") ON DELETE CASCADE" +
            ");";

    // CREATE TEACHER_GROUPS TABLE
    private static final String CREATE_TABLE_TEACHER_GROUPS = "CREATE TABLE " + TABLE_TEACHER_GROUPS + " (" +
            COLUMN_TEACHER_ID + " INTEGER NOT NULL, " +
            COLUMN_GROUP_ID + " INTEGER NOT NULL, " +
            "PRIMARY KEY (" + COLUMN_TEACHER_ID + ", " + COLUMN_GROUP_ID + "), " +
            "FOREIGN KEY(" + COLUMN_TEACHER_ID + ") REFERENCES " + TABLE_TEACHERS + "(" + COLUMN_TEACHERS_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_GROUP_ID + ") REFERENCES " + TABLE_GROUPS + "(" + COLUMN_GROUPS_ID + ") ON DELETE CASCADE" +
            ");";


    private static final String CREATE_TABLE_ADMINS = "CREATE TABLE " + TABLE_ADMINS + " (" +
            COLUMN_ADMINS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ADMINS_EMAIL + " TEXT UNIQUE NOT NULL, " +
            COLUMN_ADMINS_PASSWORD_HASH + " TEXT NOT NULL, " +
            COLUMN_ADMINS_PHONE + " TEXT, " +
            COLUMN_ADMINS_SIGNUP_DATE + " TEXT NOT NULL" +
            ");";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_USERS);
            db.execSQL(CREATE_TABLE_ENROLLMENT);
            db.execSQL(CREATE_TABLE_GROUPS);
            db.execSQL(CREATE_TABLE_STUDENTS);
            db.execSQL(CREATE_TABLE_TEACHERS);
            db.execSQL(CREATE_TABLE_TEACHER_SUBJECTS);
            db.execSQL(CREATE_TABLE_TEACHER_GROUPS);
            db.execSQL(CREATE_TABLE_ADMINS); // Create admins table
            Log.d("DBHelper", "All tables created successfully");
        } catch (SQLException e) {
            Log.e("DBHelper", "Error creating tables: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DBHelper", "Upgrading database from version " + oldVersion + " to " + newVersion);
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHER_GROUPS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHER_SUBJECTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENROLLMENT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMINS);
            onCreate(db);
        } catch (SQLException e) {
            Log.e("DBHelper", "Error upgrading database: " + e.getMessage());
        }
    }
}