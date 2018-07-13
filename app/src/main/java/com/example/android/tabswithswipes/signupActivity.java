package com.example.android.tabswithswipes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.tabswithswipes.Utils.utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.nio.Buffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signupActivity extends AppCompatActivity {

    private static EditText fullName, emailId, password, username, confirmPassword;
    private static TextView login;
    private static Button signUpButton;
    private FirebaseAuth auth;
    private static TextView forgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fullName = (EditText)findViewById(R.id.fullName);
        emailId = (EditText)findViewById(R.id.userEmailId);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        confirmPassword = (EditText)findViewById(R.id.confirmPassword);
        signUpButton = (Button)findViewById(R.id.signUpBtn);
        login = (TextView)findViewById(R.id.already_user);
        forgotPassword = (TextView)findViewById(R.id.forgotPassword);
        auth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(signupActivity.this, resetPassword.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signupActivity.this, loginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkValidation(){
        final String getFullName = fullName.getText().toString();
        final String getEmailId = emailId.getText().toString();
        String getPassword = password.getText().toString();
        final String getusername = username.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();
        SharedPreferences preferences = getSharedPreferences("MyPref",0);
        SharedPreferences.Editor editor = preferences.edit();
        if(getFullName!=null){
            editor.putString("userName", getFullName);
            editor.commit();
        }

        // Pattern match for email id

        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getusername.equals("")
                || getConfirmPassword.length() == 0){
            Toast.makeText(this,"All fields are required", Toast.LENGTH_SHORT).show();
        }else if(!emailValidator(getEmailId)){
            Toast.makeText(this,"Email id is invalid", Toast.LENGTH_SHORT).show();
        }else if(!getConfirmPassword.equals(getPassword)){
            Toast.makeText(this,"Passwords don't match", Toast.LENGTH_SHORT).show();
        }else if(getPassword.length()<6){
            Toast.makeText(this, "Password too short, enter minimum 6 characters", Toast.LENGTH_SHORT)
                    .show();
        }else if(!FireBaseMethods.CheckForUsernameInDataBase(getusername)){
              Toast.makeText(signupActivity.this, "This userName is already taken", Toast.LENGTH_SHORT).show();
              FireBaseMethods.reinit();
        }
        else{
            auth.createUserWithEmailAndPassword(getEmailId, getPassword)
                    .addOnCompleteListener(signupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(signupActivity.this, "createUserWithEmail:onComplete" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            if(!task.isSuccessful()){
                                Toast.makeText(signupActivity.this, "Authentication failed" + task.getException(), Toast.LENGTH_SHORT).show();
                            }else{
                                FireBaseMethods.insertInDataBase(getEmailId,getusername,getFullName);
                                Log.d("tagfors", "wg");
                                startActivity(new Intent(signupActivity.this, MainActivity.class));
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
