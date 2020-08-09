package com.example.cabbssys.AdminSide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.cabbssys.AdminSide.AddCab;
import com.example.cabbssys.Login;
import com.example.cabbssys.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Dashboard extends AppCompatActivity
{
    FirebaseAuth auth;
    CardView addcab,adddriver,showallcab,showalldrivers,logout,aboutus;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        auth=FirebaseAuth.getInstance();
        addcab =findViewById(R.id.addcab);
        adddriver = findViewById(R.id.adddriver);
        showallcab = findViewById(R.id.showallcab);
        showalldrivers = findViewById(R.id.showalldriver);
        logout = findViewById(R.id.logout);
        aboutus = findViewById(R.id.aboutus);

        addcab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent in=new Intent(getApplicationContext(), AddCab.class);
              startActivity(in);
            }
        });
        adddriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),AddDriver.class);
                startActivity(in);
            }
        });
        showalldrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),ShowAllDrivers.class);
                startActivity(in);
            }
        });
        showallcab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent in = new Intent(getApplicationContext(),ShowAllCabs.class);
               startActivity(in);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent in = new Intent(Dashboard.this, Login.class);
                startActivity(in);
                finish();
            }
        });

    }
}