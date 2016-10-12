package com.mad.gymtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEmailEt;
    private EditText mPasswordEt;
    private EditText mConfirmPasswordEt;
    private Button mRegisterBtn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        mEmailEt = (EditText) findViewById(R.id.register_email);
        mPasswordEt = (EditText) findViewById(R.id.register_password);
        mConfirmPasswordEt = (EditText) findViewById(R.id.register_confirmPassword);
        mRegisterBtn = (Button) findViewById(R.id.register_button);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }

    private void Register() {
        final String email = mEmailEt.getText().toString();
        final String password = mPasswordEt.getText().toString();
        final String confirmPassword = mConfirmPasswordEt.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Your password is too short, enter minimum 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.matches(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "Password and confirm password do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "The email is already registered", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "You have successfully signed up!", Toast.LENGTH_SHORT).show();
                    mAuth.signInWithEmailAndPassword(email, password);
                    startActivity(new Intent(RegisterActivity.this, NavigationActivity.class));
                    finish();
                }
            }
        });
    }
}
