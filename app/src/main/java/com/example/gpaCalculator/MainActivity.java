package com.example.gpaCalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpaCalculator.model.Module;
import com.example.gpaCalculator.model.ModuleFetcher;
import com.example.gpaCalculator.controller.ModuleAdapter;
import com.example.myapplication.R;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ModuleAdapter moduleAdapter;
    private TextView gpaResult;
    private Button calculateGPAButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewModules);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        gpaResult = findViewById(R.id.gpaResult);
        calculateGPAButton = findViewById(R.id.calculateGPAButton);

        fetchModules(); // Load modules from API

        calculateGPAButton.setOnClickListener(v -> calculateGPA());
    }

    private void fetchModules() {
        new Thread(() -> {
            try {
                ModuleFetcher moduleFetcher = new ModuleFetcher();
                List<Module> modules = moduleFetcher.fetchModules();

                runOnUiThread(() -> {
                    if (modules != null && !modules.isEmpty()) {
                        moduleAdapter = new ModuleAdapter(modules);
                        recyclerView.setAdapter(moduleAdapter);
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to load modules.", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, "Error fetching modules", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }

    private void calculateGPA() {
        if (moduleAdapter == null) {
            Toast.makeText(this, "Modules not loaded yet.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Module> modules = moduleAdapter.getModules();
        float totalScore = 0;
        float totalCoefficient = 0;

        for (Module module : modules) {
            float moduleResult = module.getCoursNote();
            float coefficient = module.getCoefficient();

            totalScore += moduleResult * coefficient;
            totalCoefficient += coefficient;
        }

        if (totalCoefficient > 0) {
            float gpa = totalScore / totalCoefficient;
            gpaResult.setText("GPA: " + String.format("%.2f", gpa));
        } else {
            gpaResult.setText("GPA: N/A");
        }
    }
}
