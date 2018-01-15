package com.example.simranjeet.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FinishTask extends AppCompatActivity {
TextView placedet;
    TextView taskdet;
    Button btn;
    String ID;
    FirebaseUser currentuser;
    DatabaseReference d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_task);

        placedet=(TextView)findViewById(R.id.placedet);
        taskdet=(TextView)findViewById(R.id.taskdet);
        btn=(Button)findViewById(R.id.finish);


        Intent in=getIntent();
        String place=in.getStringExtra("place");
        String task=in.getStringExtra("task");
        ID=in.getStringExtra("id");
        placedet.setText("Place:"+place);
        taskdet.setText("Task:"+task);


        currentuser= FirebaseAuth.getInstance().getCurrentUser();
        d= FirebaseDatabase.getInstance().getReference(currentuser.getUid()).child("taskdet").child(ID);
    }
    public void onFinish(View v){
d.removeValue();
    }

}
