package com.example.cabbssys.UserSide;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cabbssys.AdminSide.Cab;
import com.example.cabbssys.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class User_Map extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener {
    LatLng ll;
    private GoogleMap mMap;
    Button show_cab;
    Cab c;
    ImageButton location;
    String dn, dno, did,demail;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefuser;
    double pi = 3.14159265358979323846;
    double earth_radius = 6371.0;
    double ilat, ilong, mylat, mylong, central_ang, delta_long, distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapu);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(User_Map.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.INTERNET, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        location = findViewById(R.id.mylocation);
        show_cab = findViewById(R.id.show_cabs);

        myRefuser = database.getReference("Cabs");
        show_cab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRefuser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            c = dataSnapshot1.getValue(Cab.class);
                            String dla = c.latitude;
                            String dlo = c.longitude;

                            ActivityCompat.requestPermissions(User_Map.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.INTERNET, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

                            int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
                            if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available
                                int requestCode = 10;
                                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getParent(), requestCode);
                                dialog.show();
                            } else {
                                dn = c.name;
                                dno = c.number;
                                did = c.id;
                                demail = c.email;
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
                                LatLng point = new LatLng(Double.parseDouble(dla), Double.parseDouble(dlo));
                                drawMarker(point);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getmylocation();
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(ll).title("MyLocation").
                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))).setTag("Don't Click");
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 5));
            }
        });
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        mMap.setOnMarkerClickListener(User_Map.this);
        LatLng India = new LatLng(19.0760, 72.8777);
        mMap.addMarker(new MarkerOptions().position(India).title("Marker in Bombay,India").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(India,5));
    }

    @Override
    public void onLocationChanged(Location location)
    {
        ll=new LatLng(location.getLatitude(),location.getLongitude());
        mylat=degtorad(location.getLatitude());
        mylong=degtorad(location.getLongitude());
        Toast.makeText(this, "Now your Gps is on", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle)
    { }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    void getmylocation()
    {
        if (ll == null)
        {
            Toast.makeText(this, "Gps problem", Toast.LENGTH_SHORT).show();
        }
        else
        {
            CameraUpdate cu2 = CameraUpdateFactory.newLatLngZoom(ll, 20);
            mMap.animateCamera(cu2);
        }
    }

    void drawMarker(LatLng point)
    {
        ilat=degtorad(point.latitude);
        ilong=degtorad(point.longitude);
        distance=earth_radius*find_angle(ilat,ilong);
        if(distance<=5.0000)
        {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(point);
            markerOptions.title(dn+" "+dno+" "+did+" "+point.latitude+" "+point.longitude+" "+demail);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            markerOptions.snippet("Latitude:" + point.latitude + ",Longitude" + point.longitude);
            mMap.addMarker(markerOptions).setTag(0);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 10));
        }
    }

    private double find_angle(double ilat, double ilong)
    {
        delta_long = ilong-mylong;
        central_ang = acos(sin(mylat) * sin(ilat) + cos(mylat) * cos(ilat) * cos(delta_long) );
        return central_ang;
    }

    private double degtorad(double deg)
    {
        return (deg * pi / 180);
    }

    @Override
    public boolean onMarkerClick(Marker marker)
    {
            //Toast.makeText(this, markerc.getTitle() + "has been clicked " + clickCount + " times.",
            //       Toast.LENGTH_LONG).show();
            if (marker.getTag()== "Don't Click")
            {
                Toast.makeText(this, "Paidal jana hai jo apne location par click kiya", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "hii"+marker.getTitle() , Toast.LENGTH_SHORT).show();
                Intent in1 = new Intent(User_Map.this, Clicked_cab.class);
                in1.putExtra("title", marker.getTitle());
                startActivity(in1);
            }

        return false;
    }
}