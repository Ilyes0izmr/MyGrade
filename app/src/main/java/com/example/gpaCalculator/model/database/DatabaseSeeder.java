package com.example.gpaCalculator.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseSeeder {
    private final DatabaseHelper dbHelper;

    public DatabaseSeeder(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void seedDatabase() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            // Uncomment to wipe existing data
            //clearAllTables(db);

            insertDummyUsers(db);
            insertDummyGroups(db);
            insertDummyStudents(db);
            insertDummyTeachers(db);
            insertDummyTeacherSubjects(db);
            insertDummyTeacherGroups(db);
            insertDummyStudentRecords(db);

            Log.d("SEED", "Database seeded successfully!");
        } finally {
            db.close();
        }
    }

    @SuppressWarnings("unused")
    private void clearAllTables(SQLiteDatabase db) {
        Log.d("SEED", "Clearing all tables...");
        dbHelper.onUpgrade(db, 1, 1);
    }

    private void insertDummyUsers(SQLiteDatabase db) {
        // Teachers (IDs 1-3)
        String[][] teachers = {
                {"1", "prof_michael", "michael@example.com", "password1", "0555123123", "teacher"},
                {"2", "p",   "sarah@example.com",   "p", "0555234234", "teacher"},
                {"3", "prof_david",   "david@example.com",   "password3", "0555345345", "teacher"}
        };
        for (String[] t : teachers) {
            ContentValues v = new ContentValues();
            v.put(DatabaseHelper.COLUMN_USERS_ID,           t[0]);
            v.put(DatabaseHelper.COLUMN_USERS_USERNAME,     t[1]);
            v.put(DatabaseHelper.COLUMN_USERS_EMAIL,        t[2]);
            v.put(DatabaseHelper.COLUMN_USERS_PASSWORD_HASH,t[3]);
            v.put(DatabaseHelper.COLUMN_USERS_PHONE,        t[4]);
            v.put(DatabaseHelper.COLUMN_USERS_SIGNUP_DATE,  "2023-01-01");
            v.put("role_type",                            t[5]);
            db.insert(DatabaseHelper.TABLE_USERS, null, v);
            Log.d("SEED", "Inserted teacher user ID=" + t[0]);
        }

        // Students (IDs 4-9)
        String[][] students = {
                {"4", "stu_john",  "john@example.com",  "password4", "0555456456", "student"},
                {"5", "stu_jane",  "jane@example.com",  "password5", "0555567567", "student"},
                {"6", "a", "alice@example.com", "a", "0555678678", "student"},
                {"7", "stu_bob",   "bob@example.com",   "password7", "0555789789", "student"},
                {"8", "stu_eve",   "eve@example.com",   "password8", "0555890890", "student"},
                {"9", "stu_mike",  "mike@example.com",  "password9", "0555901901", "student"}
        };
        for (String[] s : students) {
            ContentValues v = new ContentValues();
            v.put(DatabaseHelper.COLUMN_USERS_ID,           s[0]);
            v.put(DatabaseHelper.COLUMN_USERS_USERNAME,     s[1]);
            v.put(DatabaseHelper.COLUMN_USERS_EMAIL,        s[2]);
            v.put(DatabaseHelper.COLUMN_USERS_PASSWORD_HASH,s[3]);
            v.put(DatabaseHelper.COLUMN_USERS_PHONE,        s[4]);
            v.put(DatabaseHelper.COLUMN_USERS_SIGNUP_DATE,  "2023-01-01");
            v.put("role_type",                            s[5]);
            db.insert(DatabaseHelper.TABLE_USERS, null, v);
            Log.d("SEED", "Inserted student user ID=" + s[0]);
        }
    }

    private void insertDummyGroups(SQLiteDatabase db) {
        String[][] groups = {
                {"1", "A", "L3", "Computer Science",         "Engineering"},
                {"2", "B", "L3", "Electrical Engineering",   "Engineering"},
                {"3", "C", "M1", "Data Science",             "Science"}
        };
        for (String[] g : groups) {
            ContentValues v = new ContentValues();
            v.put(DatabaseHelper.COLUMN_GROUPS_ID,        g[0]);
            v.put(DatabaseHelper.COLUMN_GROUP_NAME,       g[1]);
            v.put(DatabaseHelper.COLUMN_GROUP_LEVEL,      g[2]);
            v.put(DatabaseHelper.COLUMN_GROUP_SPECIALTY,  g[3]);
            v.put(DatabaseHelper.COLUMN_GROUP_DEPARTMENT, g[4]);
            db.insert(DatabaseHelper.TABLE_GROUPS, null, v);
            Log.d("SEED", "Inserted group ID=" + g[0]);
        }
    }

    private void insertDummyStudents(SQLiteDatabase db) {
        // Note: Alice (ID=6) moved to group 3 to match Machine Learning
        String[][] studs = {
                {"4","STU101","John","Doe",    "2000-01-01","Male",   "MIT","Engineering","1"},
                {"5","STU102","Jane","Smith",  "1999-05-15","Female", "Harvard","Engineering","1"},
                {"6","STU103","Alice","Jones", "1998-11-10","Female", "Stanford","Science","3"},
                {"7","STU104","Bob","Brown",   "1997-07-20","Male",   "Caltech","Science","2"},
                {"8","STU105","Eve","Wilson",  "2001-03-30","Female", "MIT","Engineering","3"},
                {"9","STU106","Mike","Taylor", "2000-09-25","Male",   "Harvard","Science","3"}
        };
        for (String[] s : studs) {
            ContentValues v = new ContentValues();
            v.put(DatabaseHelper.COLUMN_STUDENTS_ID,         s[0]);
            v.put(DatabaseHelper.COLUMN_STUDENT_ID,          s[1]);
            v.put(DatabaseHelper.COLUMN_STUDENT_FIRST_NAME,  s[2]);
            v.put(DatabaseHelper.COLUMN_STUDENT_LAST_NAME,   s[3]);
            v.put(DatabaseHelper.COLUMN_STUDENT_BIRTHDATE,   s[4]);
            v.put(DatabaseHelper.COLUMN_STUDENT_GENDER,      s[5]);
            v.put(DatabaseHelper.COLUMN_UNIVERSITY,          s[6]);
            v.put(DatabaseHelper.COLUMN_FACULTY,             s[7]);
            v.put(DatabaseHelper.COLUMN_GROUP_ID,            s[8]);
            db.insert(DatabaseHelper.TABLE_STUDENTS, null, v);
            Log.d("SEED", "Inserted student record ID=" + s[0]);
        }
    }

    private void insertDummyTeachers(SQLiteDatabase db) {
        String[][] teach = {
                {"1","PROF101","Michael","Johnson","1980-03-10","Male","12"},
                {"2","PROF102","Sarah","Davis",  "1982-07-25","Female","10"},
                {"3","PROF103","David","Wilson", "1975-11-15","Male","8"}
        };
        for (String[] t : teach) {
            ContentValues v = new ContentValues();
            v.put(DatabaseHelper.COLUMN_TEACHERS_ID,          t[0]);
            v.put(DatabaseHelper.COLUMN_TEACHERS_PROFESSOR_ID,t[1]);
            v.put(DatabaseHelper.COLUMN_TEACHER_FIRST_NAME,   t[2]);
            v.put(DatabaseHelper.COLUMN_TEACHER_LAST_NAME,    t[3]);
            v.put(DatabaseHelper.COLUMN_TEACHER_BIRTHDATE,    t[4]);
            v.put(DatabaseHelper.COLUMN_TEACHER_GENDER,       t[5]);
            v.put(DatabaseHelper.COLUMN_TEACHERS_HOURS_PER_WEEK,t[6]);
            db.insert(DatabaseHelper.TABLE_TEACHERS, null, v);
            Log.d("SEED", "Inserted teacher record ID=" + t[0]);
        }
    }

    private void insertDummyTeacherSubjects(SQLiteDatabase db) {
        String[][] ts = {
                {"1","1","1","Operating Systems"},
                {"2","1","2","Algorithms"},
                {"3","2","2","Electronics"},
                {"4","2","3","Machine Learning"},
                {"5","3","1","Database Systems"},
                {"6","3","3","Artificial Intelligence"},
                {"7","1","3","Computer Networks"},
                {"8","2","1","Software Engineering"},
                {"9","3","2","Data Structures"},
                {"10","1","1","Distributed Systems"}
        };
        for (String[] s : ts) {
            ContentValues v = new ContentValues();
            v.put(DatabaseHelper.COLUMN_SUBJECT_ID,       s[0]);
            v.put(DatabaseHelper.COLUMN_TEACHER_ID,       s[1]);
            v.put(DatabaseHelper.COLUMN_GROUP_SUBJECT_ID, s[2]);
            v.put(DatabaseHelper.COLUMN_SUBJECT_NAME,     s[3]);
            db.insert(DatabaseHelper.TABLE_TEACHER_SUBJECTS, null, v);
            Log.d("SEED", "Inserted subject ID=" + s[0]);
        }
    }

    private void insertDummyTeacherGroups(SQLiteDatabase db) {
        String[][] tg = {
                {"1","1"},
                {"1","2"},
                {"2","2"},
                {"2","3"},
                {"3","1"},
                {"3","3"}
        };
        for (String[] g : tg) {
            ContentValues v = new ContentValues();
            v.put(DatabaseHelper.COLUMN_TEACHER_ID, g[0]);
            v.put(DatabaseHelper.COLUMN_GROUP_ID,   g[1]);
            db.insert(DatabaseHelper.TABLE_TEACHER_GROUPS, null, v);
            Log.d("SEED", "Assigned teacher " + g[0] + " to group " + g[1]);
        }
    }

    private void insertDummyStudentRecords(SQLiteDatabase db) {
        String[][] rec = {
                {"1","4","1","1","1","15.5","18.0","17.0"}, // John - OS
                {"2","4","3","1","5","14.0","16.5","15.5"}, // John - DB
                {"3","5","1","1","1","16.0","17.5","18.5"}, // Jane - OS
                {"4","5","3","1","5","13.5","15.0","14.0"}, // Jane - DB
                {"5","6","2","3","4","12.0","14.5","13.5"}, // Alice - ML
                {"6","6","1","3","7","17.0","18.5","19.0"}, // Alice - CN
                {"7","7","2","2","3","15.0","16.0","17.5"}, // Bob - Electronics
                {"8","7","1","2","2","14.5","15.5","16.0"}, // Bob - Algorithms
                {"9","8","3","3","6","16.5","18.0","17.5"}, // Eve - AI
                {"10","8","2","3","4","15.0","17.0","16.5"},// Eve - ML
                {"11","9","3","3","6","14.0","15.5","16.0"},// Mike - AI
                {"12","9","2","3","4","17.5","19.0","18.5"} // Mike - ML
        };
        for (String[] r : rec) {
            ContentValues v = new ContentValues();
            v.put(DatabaseHelper.COLUMN_RECORD_ID,         r[0]);
            v.put(DatabaseHelper.COLUMN_RECORD_STUDENT_ID, r[1]);
            v.put(DatabaseHelper.COLUMN_RECORD_TEACHER_ID, r[2]);
            v.put(DatabaseHelper.COLUMN_RECORD_GROUP_ID,   r[3]);
            v.put(DatabaseHelper.COLUMN_RECORD_SUBJECT_ID, r[4]);
            v.put(DatabaseHelper.COLUMN_RECORD_TD_NOTE,    Double.parseDouble(r[5]));
            v.put(DatabaseHelper.COLUMN_RECORD_TP_NOTE,    Double.parseDouble(r[6]));
            v.put(DatabaseHelper.COLUMN_RECORD_EXAM_NOTE,  Double.parseDouble(r[7]));
            db.insert(DatabaseHelper.TABLE_STUDENT_RECORDS, null, v);
            Log.d("SEED", "Inserted student record ID=" + r[0]);
        }
    }
}