package com.example.simranjeet.project1;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText Email;
    EditText Password;
    Button Login;
    Button Signup;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Email=(EditText)findViewById(R.id.email);
        Password=(EditText)findViewById(R.id.password);
        Login=(Button)findViewById(R.id.login);
        Signup=(Button)findViewById(R.id.signup);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        mAuth=FirebaseAuth.getInstance();


    }


    @Override
    public  void onStart(){
        super.onStart();
        //check if the user is signed in (non-null) and update UI accordingly
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            sendToStart();
        }

    }

    private void sendToStart() {
        Intent startIntent=new Intent(MainActivity.this,ProfileActivity.class);
        startActivity(startIntent);
        finish();
    }

    public void onSign(View v){
        Intent i=new Intent(this,RegisterActivity.class);
        startActivity(i);
    }
    private void userLogin(){
        email=Email.getText().toString().trim();
        password=Password.getText().toString().trim();
        if(email.isEmpty()){
            Email.setError("Email is reuired");
            Email.requestFocus();
            return;
        }
        if(password.isEmpty()){
            Password.setError("Password is required");
            Password.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Please enter a valid Email");
            Email.requestFocus();
            return;
        }
        if(password.length()<6){
            Password.setError("Minimum 6 charcters are required in password");
            Password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Intent i=new Intent(MainActivity.this,ProfileActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                }
                else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
/*
    private void verifyEmail() {
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user.isEmailVerified()){
        Intent i=new Intent(MainActivity.this,ProfileActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);}
        else{
            Toast.makeText(MainActivity.this,"Verify Link sent to your email", Toast.LENGTH_SHORT).show();
        }
    }
*/
    public void onlogin(View v){
        userLogin();
    }
}
