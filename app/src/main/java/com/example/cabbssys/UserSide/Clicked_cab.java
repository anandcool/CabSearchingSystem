package com.example.cabbssys.UserSide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cabbssys.AdminSide.Cab;
import com.example.cabbssys.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Clicked_cab extends AppCompatActivity
{
    String uid,ids,name,number,email,latitude,longitude;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myref;
    FirebaseAuth auth1;
    FirebaseUser us;
    Cab sc;
    Button cancel,book;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_cab);
        cancel = findViewById(R.id.cancelbook);
        book = findViewById(R.id.bookcab);
        Bundle bundle = getIntent().getExtras();
        uid=bundle.getString("title");
        String[] ar=uid.split(" ");
        name=ar[0];
        number=ar[1];
        ids=ar[2];
        latitude=ar[3];
        longitude=ar[4];
        email=ar[5];

        auth1 = FirebaseAuth.getInstance();
        us = auth1.getCurrentUser();
        myref=database.getReference("Cabs");

       // Toast.makeText(this, "email"+us.getEmail()+"id: "+ids , Toast.LENGTH_LONG).show();

        // here i am getting that cab info whose id is equal to ids
       /* DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(ids);
        myref.orderByChild("id").equalTo(ids).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    sc = dataSnapshot.getValue(Cab.class);
                   // Toast.makeText(Clicked_cab.this, "Number"+sc.getNumber(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Clicked_cab.this, "nhi cahaal", Toast.LENGTH_SHORT).show();
            }
        }); */

        myref.orderByChild("email").equalTo(email).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                  sc = dataSnapshot.getValue(Cab.class);
                Toast.makeText(Clicked_cab.this, "Latitude "+sc.getLatitude()+" name  "+sc.getName(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        show_cab(sc);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookcab();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void bookcab(){
        Intent inu=new Intent(Clicked_cab.this,userdriver.class);
        inu.putExtra("drivermail",email);
        startActivity(inu);
    }

    void show_cab(Cab sc)
    {
        //Toast.makeText(this, "sc", Toast.LENGTH_SHORT).show();
        TextView cabname = findViewById(R.id.cccabname);
        TextView cabphonenumber = findViewById(R.id.cccabphonenubmer);
        TextView cabemail = findViewById(R.id.cccabemail);
        TextView cablongitude = findViewById(R.id.cccablongitude);
        TextView cablatitude = findViewById(R.id.cccablatitude);
        cabname.setText("Name:    "+name);
        cabphonenumber.setText("Number:  "+number);
        cabemail.setText("Email:    "+email);
        cablongitude.setText("Longitude:  "+longitude);
        cablatitude.setText("Latitude:   "+latitude);
    }
}