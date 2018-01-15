package com.example.simranjeet.project1;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    private Boolean exit = false;
    Button btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

      btn1=(Button)findViewById(R.id.task);
      btn2=(Button)findViewById(R.id.logout);

    }
    public void onTask(View v){
        Toast.makeText(getApplicationContext(),"Enable your location", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(ProfileActivity.this,TaskActivity.class);
        startActivity(i);
    }
    public void onLogout(View v){
        FirebaseAuth.getInstance().signOut();
        Intent i=new Intent(ProfileActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);
                }
            }, 1000);
        }
    }
}
