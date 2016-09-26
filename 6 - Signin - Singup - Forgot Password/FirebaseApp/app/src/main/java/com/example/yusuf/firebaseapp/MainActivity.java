package com.example.yusuf.firebaseapp;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    // Don't forget add firebase dependencies

    private Button btnSignin, btnSignup, btnForgotPassword;
    private EditText userMail, userPassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    // Firebase Dashboard, auth -> sign-in method kısmında emain-password'u enable ediyoruz
    // Check Firebase Dashboard, auth-> sign-in method -> enable email-password


    // Herhangi bir sorunda dökümana bakabilirsiniz
    // Check docs for any problem

    // https://firebase.google.com/docs/auth/android/password-auth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userMail = (EditText) findViewById(R.id.userEmail);
        userPassword = (EditText) findViewById(R.id.userPassword);

        btnSignin = (Button) findViewById(R.id.btnSignin);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnForgotPassword = (Button) findViewById(R.id.btnForgotPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Get firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        //Button Clicks
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });

    }


    public void signUp(){
        final String inputEmail = userMail.getText().toString().trim();
        final String inputPassword = userPassword.getText().toString().trim();

        if(TextUtils.isEmpty(inputEmail)){
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(inputPassword)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
        } else if (inputPassword.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short!", Toast.LENGTH_SHORT).show();
        }
        else {
            progressBar.setVisibility(View.VISIBLE);

            //Create user
            mAuth.createUserWithEmailAndPassword(inputEmail,inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if(task.isSuccessful()){
                        startActivity(new Intent(MainActivity.this,HomeActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Authentication Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public void signIn(){
        String inputEmail = userMail.getText().toString().trim();
        String inputPassword = userPassword.getText().toString().trim();

        if(TextUtils.isEmpty(inputEmail)){
            Toast.makeText(getApplicationContext(),"Enter email address!", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(inputPassword)){
            Toast.makeText(getApplicationContext(),"Enter password!", Toast.LENGTH_LONG).show();
        } else if(inputPassword.length() < 6){
            Toast.makeText(getApplicationContext(),"Password too short!", Toast.LENGTH_LONG).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);

            //Sign in user
            mAuth.signInWithEmailAndPassword(inputEmail,inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if(task.isSuccessful()){
                        startActivity(new Intent(MainActivity.this,HomeActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(),"Failed to Register", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void forgotPassword(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_email_dialog);
        dialog.setTitle("Reset Password");
        dialog.setCancelable(true);

        final EditText userEmail = (EditText) dialog.findViewById(R.id.newEmail);

        Button btnReset = (Button) dialog.findViewById(R.id.btnReset);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = userEmail.getText().toString().trim();

                if(TextUtils.isEmpty(inputEmail)){
                    Toast.makeText(getApplicationContext(),"Enter your Email", Toast.LENGTH_LONG).show();
                }else {
                    mAuth.sendPasswordResetEmail(inputEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"We have sent you instructions to reset your password!",Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}
