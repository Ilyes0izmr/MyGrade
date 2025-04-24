package com.example.gpaCalculator.controller;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gpaCalculator.model.database.DatabaseHelper;
import com.example.gpaCalculator.model.database.TeacherDAO;
import com.example.gpaCalculator.model.entities.GroupSubject;
import com.example.myapplication.R;

import java.util.List;

public class GroupListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewGroups;
    private GroupListAdapter groupListAdapter;
    private TeacherDAO teacherDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        // Initialize views
        recyclerViewGroups = findViewById(R.id.recyclerViewGroups);

        // Initialize DAO
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        teacherDAO = new TeacherDAO(this);

        // Fetch data for the logged-in teacher
        String teacherId = SessionManager.getInstance().getCurrentUser().getId();
        List<GroupSubject> groupSubjects = teacherDAO.getTeacherGroupsAndSubjects(teacherId);

        // Set up RecyclerView
        groupListAdapter = new GroupListAdapter(groupSubjects);
        recyclerViewGroups.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewGroups.setAdapter(groupListAdapter);
    }
}