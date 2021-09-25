package com.lekhamm.project.first.by.dry.lekhamm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private TextView already;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseUser mUser;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText confirmPassword;

    private Button signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        progressDialog = new ProgressDialog(this);


        email = findViewById(R.id.emailEditId);
        password = findViewById(R.id.passwordEditId);
        confirmPassword = findViewById(R.id.confirmEditId);
        signup = findViewById(R.id.signupBtnId);

        mAuth = FirebaseAuth.getInstance();


        already = findViewById(R.id.already);
        already.setText(Html.fromHtml("<u>Already have an account?</u>"));
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();
                String confirmPwdString = confirmPassword.getText().toString();

                if (emailString.equals("") || passwordString.equals("") || confirmPwdString.equals("")) {

                    Toast.makeText(RegisterActivity.this, "Please fill your credentials correctly", Toast.LENGTH_SHORT).show();
                } else if (!confirmPwdString.equals(passwordString)) {
                    Toast.makeText(RegisterActivity.this, "Password doesn't match!", Toast.LENGTH_SHORT).show();
                } else {
                    signup(emailString, passwordString);
                }
            }
        });
    }

    private void signup(String emailString, String passwordString) {
        mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();

                } else {
                    Toast.makeText(RegisterActivity.this, "Failed to create account", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}