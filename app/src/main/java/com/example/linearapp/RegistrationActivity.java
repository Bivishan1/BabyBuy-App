package com.example.linearapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class RegistrationActivity extends AppCompatActivity {
    EditText edName, edEmail, edPassword, edCPassword;
    TextView textView;
    Button btnRegister;
    private DatabaseHelper databaseHelper;
    private User user;

    //string for get input edit text to string
    String name, email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        edName = findViewById(R.id.textInputName);
        edEmail = findViewById(R.id.textInputEditTextEmail);
        edPassword = findViewById(R.id.textInputPassword);
        edCPassword = findViewById(R.id.textInputConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        textView = findViewById(R.id.textViewLinkLogin);
//        initialize objects to be used

        databaseHelper = new DatabaseHelper(RegistrationActivity.this);
        user = new User();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edName.getText().toString();
                email = edEmail.getText().toString();
                password = edPassword.getText().toString();
                confirmPassword = edCPassword.getText().toString();

//                if (name.equals("") !! email.equals((" ")) {
//
//                })
                if (name.isEmpty()) {
                    edName.setError("Name is Required");
                } else if (email.isEmpty()) {
                    edEmail.setError("Email is required");
                } else if (password.isEmpty()) {
                    edPassword.setError("Password is required");
                } else if (confirmPassword.isEmpty()) {
                    edCPassword.setError("Confirm Password is required");
                } else if (!password.equals(confirmPassword)) {
                    edCPassword.setError(("password and confirm password need to be same"));
                } else {
                    Boolean checkUser = databaseHelper.checkUserName(email);
                    if (!checkUser) {
                        postDataToSQLite();
                    } else {
                        Snackbar.make(btnRegister, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
                    }

                }
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity((intent)); // intent started by trigger
            }
        });
    }

    private void postDataToSQLite() {

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        databaseHelper.addUser(user);

//        Snack Bar to show success message that record saved successfully
        Snackbar.make(btnRegister, "Registration Successful", Snackbar.LENGTH_LONG).show();
    }
}