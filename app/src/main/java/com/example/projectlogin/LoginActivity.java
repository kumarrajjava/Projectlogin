package com.example.projectlogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    ImageView btnLoginArrow;
    Button btnGoogle, btnFacebook;
    TextView tvRegister;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DBHelper(this);

        etEmail = findViewById(R.id.editTextTextPersonName);
        etPassword = findViewById(R.id.editTextTextpassword);
        btnLoginArrow = findViewById(R.id.imageView5);
        btnGoogle = findViewById(R.id.button3);
        btnFacebook = findViewById(R.id.buttonfacebook);
        tvRegister = findViewById(R.id.textView5);

        btnLoginArrow.setOnClickListener(v -> loginUser());

        btnGoogle.setOnClickListener(v ->
                Toast.makeText(this, "Google Sign-In coming soon", Toast.LENGTH_SHORT).show());

        btnFacebook.setOnClickListener(v ->
                Toast.makeText(this, "Facebook Sign-In coming soon", Toast.LENGTH_SHORT).show());

        tvRegister.setOnClickListener(v ->
                startActivity(new Intent(this, SignupActivity.class)));
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim().toLowerCase();
        String password = etPassword.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email");
            etEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Please enter your password");
            etPassword.requestFocus();
            return;
        }

        String name = db.loginUser(email, password);
        if (name == null) {
            Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Welcome " + name, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}