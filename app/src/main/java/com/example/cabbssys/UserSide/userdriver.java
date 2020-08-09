package com.example.cabbssys.UserSide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cabbssys.AdminSide.Cab;
import com.example.cabbssys.Login;
import com.example.cabbssys.R;
import com.example.cabbssys.user_details;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.StringTokenizer;

public class userdriver extends FragmentActivity implements OnMapReadyCallback, LocationListener
{
    private GoogleMap mMap;
    LatLng ll1,ll2;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myref1,myref2,myref3;
    FirebaseAuth auth2;
    FirebaseUser u;
    String driveremail,useremail;
    Cab driver;
    String dlati,dlong,mylati,mylong;
    Button security,logout;
    user_details ud;
    String name,s1,s2,s3,num;

    Intent intent;
    PendingIntent pi;
    SmsManager sms;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdriver);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        logout=findViewById(R.id.logout);
        security=findViewById(R.id.securitymessage);

        intent=new Intent(getApplicationContext(),userdriver.class);
        pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth2.signOut();
                Intent in = new Intent(userdriver.this, Login.class);
                startActivity(in);
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        driveremail=bundle.getString("drivermail");

        myref1=database.getReference("users");
        myref2 = database.getReference("Cabs");

        auth2 = FirebaseAuth.getInstance();
        u = auth2.getCurrentUser();
        useremail = u.getEmail();

        myref2.orderByChild("email").equalTo(driveremail).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                 driver = dataSnapshot.getValue(Cab.class);
                 dlati = driver.latitude;
                 dlong = driver.longitude;
                Toast.makeText(userdriver.this, ""+dlati, Toast.LENGTH_LONG).show();
                LatLng point = new LatLng(Double.parseDouble(dlati), Double.parseDouble(dlong));
                MarkerOptions markerOptions2 = new MarkerOptions();
                markerOptions2.position(point);
                markerOptions2.title("Driver Location"+point.latitude+" "+point.longitude);
                markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                markerOptions2.snippet("Latitude:" + point.latitude + ",Longitude" + point.longitude);
                mMap.addMarker(markerOptions2).setTag(0);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 10));
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

        ActivityCompat.requestPermissions(userdriver.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.INTERNET, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        mapFragment.getMapAsync(this);

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat #requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this, "Gps problem", Toast.LENGTH_LONG).show();
            return;
        }
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 100, this);

        security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int permissioncheck = ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.SEND_SMS);

                if(permissioncheck == PackageManager.PERMISSION_GRANTED)
                {
                    send_sms();
                }

                else
                {
                    ActivityCompat.requestPermissions(getParent(),new String[]{Manifest.permission.SEND_SMS},0);
                }
            }
        });

    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
       /* LatLng India = new LatLng(19.0760, 72.8777);
        mMap.addMarker(new MarkerOptions().position(India).title("Marker in Bombay,India").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(India,5)); */
    }

    @Override
    public void onLocationChanged(Location location){
        ll1=new LatLng(location.getLatitude(),location.getLongitude());
        mylati = String.valueOf(location.getLatitude());
        mylong = String.valueOf(location.getLongitude());
        if (ll1 == null)
        {
            Toast.makeText(this, "Gps problem", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mMap.addMarker(new MarkerOptions().position(ll1).title("MyLocation").
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))).setTag("Don't Click");
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll1, 10));
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    void send_sms()
    {
        myref3=database.getReference("users");
        myref3.orderByChild("email").equalTo(useremail).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ud = dataSnapshot.getValue(user_details.class);
                name = ud.getName();
                s1 = ud.getS1no();
                Log.d("kyahua",s1);
                s2 = ud.getS2no();
                s3 = ud.getS3no();
                num = ud.getNumber();
                Toast.makeText(userdriver.this, "name "+name+"no1 "+s1+" no 2"+s3, Toast.LENGTH_SHORT).show();
                String message = "Hii! Alert, one of your family member "+name+" and number "+num+" is in problem. " +
                        "Be calm and don't panic. " + "The location is Longitude: "+mylong+" Latitude: "+mylati;
                sms = SmsManager.getDefault();
                String[] numbers = {s1, s2, s3};
                Log.d("numberskilength",""+s1);
//        Log.d("numberbaato",numbers[0]);
        for(String number : numbers)
        {
            Log.d("number",number);
//            Toast.makeText(this, "number"+number, Toast.LENGTH_SHORT).show();
            sms.sendTextMessage(number,null,message,null,null);
            Toast.makeText(getApplicationContext(), "Message Sended", Toast.LENGTH_SHORT).show();
        }
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


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode)
        {
            case 0:
                if(grantResults.length>=0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    send_sms();
                }
                else
                {
                    Toast.makeText(this, "You don't have access to permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}