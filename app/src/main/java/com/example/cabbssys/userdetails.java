package com.example.cabbssys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class userdetails extends AppCompatActivity
{
    EditText name,number,s1no,s2no,s3no,email;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    Button save;
    String e,n,no,s1num,s2num,s3num;
    user_details u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        name=findViewById(R.id.udname);
        number=findViewById(R.id.udnumber);
        s1no=findViewById(R.id.ud1sno);
        s2no=findViewById(R.id.ud2sno);
        s3no=findViewById(R.id.ud3sno);
        email=findViewById(R.id.udemail);

        myRef = database.getReference();

        save=findViewById(R.id.udsave);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savenumbers();
            }
        });

    }

    void savenumbers()
    {
        String id=myRef.push().getKey();
        e=email.getText().toString();
       n=name.getText().toString();
       no=number.getText().toString();
       s1num=s1no.getText().toString();
       s2num=s2no.getText().toString();
       s3num=s3no.getText().toString();
       u=new user_details(n,no,e,s1num,s2num,s3num);
       myRef.child("users").child(id).setValue(u);
        Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_LONG).show();
        email.setText("");
        name.setText("");
        number.setText("");
        s1no.setText("");
        s2no.setText("");
        s3no.setText("");
        Intent in=new Intent(userdetails.this,Login.class);
        startActivity(in);
        finish();
    }
}