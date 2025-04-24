package com.example.gpaCalculator.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class SignUpSection2Fragment extends Fragment {
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button buttonSignUp;
    private TextView textViewLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_section2, container, false);

        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextConfirmPassword = view.findViewById(R.id.editTextConfirmPassword);
        buttonSignUp = view.findViewById(R.id.buttonSignUp);
        textViewLogin = view.findViewById(R.id.textViewLogin);

        buttonSignUp.setOnClickListener(v -> {
            if (getActivity() instanceof SignUpActivity) {
                ((SignUpActivity) getActivity()).signUp();
            }
        });

        textViewLogin.setOnClickListener(v -> {
            if (getActivity() instanceof SignUpActivity) {
                ((SignUpActivity) getActivity()).navigateToLogIn();
            }
        });

        return view;
    }

    public String getPassword() {
        return editTextPassword.getText().toString();
    }

    public String getConfirmPassword() {
        return editTextConfirmPassword.getText().toString();
    }
}