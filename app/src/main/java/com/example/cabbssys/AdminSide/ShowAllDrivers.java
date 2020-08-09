package com.example.cabbssys.AdminSide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import com.example.cabbssys.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowAllDrivers extends AppCompatActivity {
    List<Driver> driverlist;
    ListView listView;
    FirebaseDatabase ddatabase = FirebaseDatabase.getInstance();
    DatabaseReference myref;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_drivers);
        listView = findViewById(R.id.alldrivers);
        driverlist = new ArrayList<Driver>();
        myref = ddatabase.getReference("Drivers");
        showAllDrivers();
    }

    private void showAllDrivers()
    {
      myref.addValueEventListener(new ValueEventListener()
      {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot)
          {
             for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
             {
                 Driver d= dataSnapshot1.getValue(Driver.class);
                 driverlist.add(d);
                 DriverAdapter da=new DriverAdapter(getApplicationContext(),R.layout.driverinfo,driverlist);
                 listView.setAdapter(da);
             }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError)
          {

          }
      });
    }
}
