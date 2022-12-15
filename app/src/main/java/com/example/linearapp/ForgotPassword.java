package com.example.linearapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class ForgotPassword extends AppCompatActivity {

    TextView textView;
    EditText edEmail;
    Button btnSendEmail;
    private DatabaseHelper databaseHelper;

    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edEmail = findViewById(R.id.textInputEditTextEmail);
        btnSendEmail = findViewById(R.id.btnsendEmail);
        databaseHelper = new DatabaseHelper(ForgotPassword.this);

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edEmail.getText().toString();

                if (email.isEmpty()) {
                    edEmail.setError("Insert your registered email");
                }
                else {
                    Boolean checkEmail = databaseHelper.checkUserName(email);

                    if (checkEmail==true) {
                        Snackbar.make(btnSendEmail,"Password reset link send successfully",Snackbar.LENGTH_LONG).show();
                    }
                    else {
                        Snackbar.make(btnSendEmail,"Email is not registered yet",Snackbar.LENGTH_LONG).show();

                    }
                }
            }
        });


    }
}