package com.example.gpaCalculator.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class SignUpSection1Fragment extends Fragment {
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextMatricule;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_section1, container, false);

        // Initialize EditText fields
        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextMatricule = view.findViewById(R.id.editTextMatricule);

        return view;
    }

    public String getUsername() {
        return editTextUsername.getText().toString().trim();
    }

    public String getEmail() {
        return editTextEmail.getText().toString().trim();
    }

    public String getMatricule() {
        return editTextMatricule.getText().toString().trim();
    }
}