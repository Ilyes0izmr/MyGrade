// src/main/java/com/example/gpacalculator/activities/HomeActivity.java
package com.example.gpaCalculator.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //

        // Find the button in the layout
        Button btnGoToAccount = findViewById(R.id.btnGoToAccount);

        // Set an OnClickListener to navigate to AccountActivity
        btnGoToAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
    }
}