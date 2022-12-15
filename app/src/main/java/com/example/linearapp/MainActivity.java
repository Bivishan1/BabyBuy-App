package com.example.linearapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class  MainActivity extends AppCompatActivity {
TextView textView, textView1;
EditText edEmail, edPassword;
Button btnLogin;
String username, password;
DatabaseHelper databaseHelper;

FirebaseFirestore firebaseFirestore;
//Firebase
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

//        Firebase
        firebaseFirestore = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();
        user.put("FirstName","BiviYes");
        user.put("LastName","Shan");
        user.put("description","Popular shots");
        firebaseFirestore.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
//                Snackbar.make("test", "Wrong plaeholder",Snackbar.LENGTH_LONG);.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
            }
        });

//        firebase close


        textView = findViewById(R.id.textViewLinkRegister);
        textView1 = findViewById(R.id.btnForgot);
    edEmail = findViewById(R.id.textInputEditTextEmail);
//        btnSubmit = findViewById(R.id.btnSubmit);
        edPassword = findViewById(R.id.textInputEditTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        databaseHelper = new DatabaseHelper(MainActivity.this);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //defining source and targeting class
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity((intent)); // intent started by trigger
            }
        });

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,ForgotPassword.class );
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = edEmail.getText().toString();
                password = edPassword.getText().toString();
                Boolean checkUser = databaseHelper.checkUserDetails(username,password);

                if (checkUser == true) {
                    Snackbar.make(btnLogin,"Login Successful",Snackbar.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(btnLogin, "Wrong Email or Password", Snackbar.LENGTH_LONG).show();

                }
            }
        });
    }
}