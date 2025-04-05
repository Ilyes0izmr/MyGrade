package com.example.gpaCalculator;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpaCalculator.model.Module;
import com.example.gpaCalculator.view.ModulAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ModulAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewModules);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a list of Module objects (static data)
        List<Module> modules = new ArrayList<>();
        modules.add(new Module("M1", "Mathematics", "true", "true", "false", "4.0", "3.0", "Science"));
        modules.add(new Module("M2", "Physics", "true", "false", "true", "3.5", "2.5", "Science"));
        modules.add(new Module("M3", "Chemistry", "true", "true", "true", "2.5", "2.0", "Science"));

        // Initialize the adapter with the static data
        adapter = new ModulAdapter(modules);
        recyclerView.setAdapter(adapter);

        // Optional: Show a message if no modules are available
        if (modules.isEmpty()) {
            Toast.makeText(this, "No modules found.", Toast.LENGTH_SHORT).show();
        }
    }
}