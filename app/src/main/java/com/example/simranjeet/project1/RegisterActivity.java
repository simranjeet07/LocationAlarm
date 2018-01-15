package com.example.simranjeet.project1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    EditText Email1;
    EditText Password1;
    EditText name;
    EditText Password2;
    Button Register;
    String email,password,password2;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Email1=(EditText)findViewById(R.id.email1);
        Password1=(EditText)findViewById(R.id.password1);
        Register=(Button)findViewById(R.id.register);
        name=(EditText)findViewById(R.id.name);
        Password2=(EditText)findViewById(R.id.password2);
        mAuth = FirebaseAuth.getInstance();
        progressBar=(ProgressBar)findViewById(R.id.progressbar);


    }
    private void registerUser(){
        email=Email1.getText().toString().trim();
        password=Password1.getText().toString().trim();
        password2=Password2.getText().toString().trim();
        if(email.isEmpty()){
            Email1.setError("Email is reuired");
            Email1.requestFocus();
            return;
        }
        if(password.isEmpty()){
            Password1.setError("Password is required");
            Password1.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email1.setError("Please enter a valid Email");
            Email1.requestFocus();
            return;
        }
        if(password.length()<6){
            Password1.setError("Minimum 6 charcters are required in password");
            Password1.requestFocus();
            return;
        }
        if(!password.equals(password2)){
            Toast.makeText(getApplicationContext(),"Passwords don't match", Toast.LENGTH_SHORT).show();
            Password1.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){

                    Toast.makeText(getApplicationContext(),"Successfully Registered", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    Intent i=new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(i);
                }
                else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplication(),"You are already Registered", Toast.LENGTH_SHORT).show();
                    }
                    else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }}
            }

            
        });
    }


    public void onReg(View v) {
registerUser();
    }
}
