package com.example.projectlogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etRePassword;
    ImageView btnSignupArrow;
    TextView tvAlreadyHave;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = new DBHelper(this);

        etName = findViewById(R.id.editTextTextPersonName);
        etEmail = findViewById(R.id.editTextTextPersonName2);
        etPassword = findViewById(R.id.editTextTextpassword2);
        etRePassword = findViewById(R.id.editTextTextpassword3);
        btnSignupArrow = findViewById(R.id.imageView5);
        tvAlreadyHave = findViewById(R.id.textView5);

        btnSignupArrow.setOnClickListener(v -> registerUser());

        tvAlreadyHave.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void registerUser() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim().toLowerCase();
        String password = etPassword.getText().toString().trim();
        String rePassword = etRePassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etName.setError("Please enter your name");
            etName.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email");
            etEmail.requestFocus();
            return;        }
        if (password.length() < 6) {
            etPassword.setError("Minimum 6 characters");
            etPassword.requestFocus();
            return;
        }
        if (!password.equals(rePassword)) {
            etRePassword.setError("Passwords do not match");
            etRePassword.requestFocus();
            return;
        }
        if (db.emailExists(email)) {
            etEmail.setError("This email is already registered");
            etEmail.requestFocus();
            return;
        }

        if (db.registerUser(name, email, password)) {
            Toast.makeText(this, "Account created! Please log in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Signup failed, please try again", Toast.LENGTH_SHORT).show();
        }
    }
}