package com.example.cabbssys.AdminSide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.cabbssys.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowAllCabs extends AppCompatActivity
{
    List<Cab> cablist;
    ListView listview2;
    FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance();
    DatabaseReference dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_cabs);
        listview2=findViewById(R.id.allcabs);
        cablist = new ArrayList<Cab>();
        dref = firebaseDatabase2.getReference("Cabs");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                  for(DataSnapshot d:dataSnapshot.getChildren())
                  {
                     Cab cab=d.getValue(Cab.class);
                     cablist.add(cab);
                     CabAdapter cabAdapter = new CabAdapter(getApplicationContext(),R.layout.cabinfo,cablist);
                     listview2.setAdapter(cabAdapter);
                  }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}