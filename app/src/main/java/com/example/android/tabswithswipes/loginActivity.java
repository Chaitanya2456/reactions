package com.example.android.tabswithswipes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.tabswithswipes.Utils.utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class loginActivity extends AppCompatActivity {

    private static Button loginButton;
    private static TextView signUp;
    private static EditText emailId, password;
    private static CheckBox show_hidden_password;
    private static TextView forgotPassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences("MyPref",0);
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            Intent intent = new Intent(loginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            setContentView(R.layout.activity_login);
            auth = FirebaseAuth.getInstance();
            loginButton = (Button)findViewById(R.id.loginBtn);
            signUp = (TextView)findViewById(R.id.createAccount);
            emailId = (EditText)findViewById(R.id.login_emailid);
            password = (EditText)findViewById(R.id.login_password);
            show_hidden_password = (CheckBox)findViewById(R.id.show_hide_password);
            forgotPassword = (TextView)findViewById(R.id.forgotPassword);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkValidation();
                }
            });
            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(loginActivity.this, signupActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            forgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(loginActivity.this, resetPassword.class));
                }
            });
        }


    }

    private void checkValidation(){
        String getEmailId = emailId.getText().toString();
        final String getPassword = password.getText().toString();

        if(getEmailId.equals("")||getEmailId.length()==0
                ||getPassword.equals("")||getPassword.length()==0){
            Toast.makeText(this, "Enter both credentials", Toast.LENGTH_SHORT).show();

        }else if(!emailValidator(getEmailId)){
            Toast.makeText(this, "invalid email id", Toast.LENGTH_SHORT).show();

        }else{
            auth.signInWithEmailAndPassword(getEmailId, getPassword)
                    .addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                if(getPassword.length()<6){
                                    password.setError("Password is too short");
                                }else{
                                    Toast.makeText(loginActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                startActivity(new Intent(loginActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    });
        }
    }

    private boolean emailValidator(String emailId){
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(emailId);
        return matcher.matches();
    }


}
