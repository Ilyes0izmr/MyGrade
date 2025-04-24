package com.example.gpaCalculator.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.gpaCalculator.model.database.AdminDAO;
import com.example.gpaCalculator.model.database.StudentDAO;
import com.example.gpaCalculator.model.database.TeacherDAO;
import com.example.gpaCalculator.model.database.UserDAO;
import com.example.gpaCalculator.model.entities.User;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    private ViewPager2 viewPagerSignUp;
    private Button buttonPrevious;
    private Button buttonNext;
    private TextView textViewLogin;

    private SignUpPagerAdapter pagerAdapter;

    private UserDAO userDAO;
    private StudentDAO studentDAO;
    private TeacherDAO teacherDAO;
    private AdminDAO adminDAO;

    private String username;
    private String email;
    private String matricule;
    private String password;
    private String confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        viewPagerSignUp = findViewById(R.id.viewPagerSignUp);
        buttonPrevious = findViewById(R.id.buttonPrevious);
        buttonNext = findViewById(R.id.buttonNext);
        textViewLogin = findViewById(R.id.textViewLogin);

        userDAO = new UserDAO(this);
        studentDAO = new StudentDAO(this);
        teacherDAO = new TeacherDAO(this);
        adminDAO = new AdminDAO(this);

        List<androidx.fragment.app.Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new SignUpSection1Fragment());
        fragmentList.add(new SignUpSection2Fragment());

        pagerAdapter = new SignUpPagerAdapter(this, fragmentList);
        viewPagerSignUp.setAdapter(pagerAdapter);
        viewPagerSignUp.setUserInputEnabled(false); // Disable swipe navigation

        // Set consistent button texts
        buttonNext.setText("Next");
        buttonPrevious.setText("Previous");

        // Initially set to the first page
        updateNavigation(0);

        buttonNext.setOnClickListener(v -> {
            if (viewPagerSignUp.getCurrentItem() < pagerAdapter.getItemCount() - 1) {
                viewPagerSignUp.setCurrentItem(viewPagerSignUp.getCurrentItem() + 1);
            }
        });

        buttonPrevious.setOnClickListener(v -> {
            if (viewPagerSignUp.getCurrentItem() > 0) {
                viewPagerSignUp.setCurrentItem(viewPagerSignUp.getCurrentItem() - 1);
            }
        });

        textViewLogin.setOnClickListener(v -> navigateToLogIn());

        viewPagerSignUp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateNavigation(position);
            }
        });
    }

    private void updateNavigation(int position) {
        buttonPrevious.setVisibility(position > 0 ? View.VISIBLE : View.GONE);
        buttonNext.setVisibility(position < pagerAdapter.getItemCount() - 1 ? View.VISIBLE : View.GONE);
    }

    public void navigateToLogIn() {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        finish();
    }

    public void signUp() {
        // Get data from both fragments
        SignUpSection1Fragment fragment1 = (SignUpSection1Fragment) pagerAdapter.getFragment(0);
        SignUpSection2Fragment fragment2 = (SignUpSection2Fragment) pagerAdapter.getFragment(1);

        if (fragment1 != null && fragment2 != null) {
            username = fragment1.getUsername();
            email = fragment1.getEmail();
            matricule = fragment1.getMatricule();
            password = fragment2.getPassword();
            confirmPassword = fragment2.getConfirmPassword();

            // Validate input fields
            if (username.isEmpty() || email.isEmpty() || matricule.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                return;
            }

            String roleType = null;
            String userId = null;

            if (studentDAO.isMatriculeExists(matricule)) {
                roleType = "student";
                userId = studentDAO.getUserIdByMatricule(matricule);
            } else if (teacherDAO.isMatriculeExists(matricule)) {
                roleType = "teacher";
                userId = teacherDAO.getUserIdByMatricule(matricule);
            } else if (adminDAO.isMatriculeExists(matricule)) {
                roleType = "admin";
                userId = adminDAO.getUserIdByMatricule(matricule);
            }

            if (roleType == null || userId == null) {
                Toast.makeText(this, "Matricule not found in any records!", Toast.LENGTH_SHORT).show();
                return;
            }

            String signupDate = String.valueOf(System.currentTimeMillis());
            User user = new User(userId, username, email, password, null, signupDate, roleType);

            boolean success = userDAO.insertUser(user);
            if (success) {
                Toast.makeText(this, "Sign-up successful!", Toast.LENGTH_SHORT).show();
                navigateToLogIn();
            } else {
                Toast.makeText(this, "Sign-up failed. Try again.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Error accessing registration data.", Toast.LENGTH_SHORT).show();
        }
    }

    private static class SignUpPagerAdapter extends androidx.viewpager2.adapter.FragmentStateAdapter {
        private final List<androidx.fragment.app.Fragment> fragmentList;

        public SignUpPagerAdapter(SignUpActivity activity, List<androidx.fragment.app.Fragment> fragmentList) {
            super(activity);
            this.fragmentList = fragmentList;
        }

        @Override
        public androidx.fragment.app.Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }

        public androidx.fragment.app.Fragment getFragment(int position) {
            if (position >= 0 && position < fragmentList.size()) {
                return fragmentList.get(position);
            }
            return null;
        }
    }
}