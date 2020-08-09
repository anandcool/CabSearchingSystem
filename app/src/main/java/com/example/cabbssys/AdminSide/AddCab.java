package com.example.cabbssys.AdminSide;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cabbssys.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AddCab extends FragmentActivity implements OnMapReadyCallback {
    EditText name, number, place, email, licensenumberplate;
    Button add, search;
    LatLng ll;
    TextView latitude, longitude;
    Cab cab;

    private GoogleMap mMap;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cab);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapc);
        mapFragment.getMapAsync(this);
        ActivityCompat.requestPermissions(AddCab.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        place = findViewById(R.id.place);
        add = findViewById(R.id.add);
        search = findViewById(R.id.search);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        email = findViewById(R.id.email);
        licensenumberplate = findViewById(R.id.licensenumberplate);
        myRef = database.getReference();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Geocoder g = new Geocoder(AddCab.this);
                    List<Address> locations = g.getFromLocationName(place.getText().toString(), 10);
                    Address address = locations.get(0);
                    String s = "" + address.getCountryName() + "," + address.getCountryCode() + "," + address.getAddressLine(0) + "," + address.getLocality() + "," + address.getSubLocality();
                    LatLng l = new LatLng(address.getLatitude(), address.getLongitude());
                    Double lat = address.getLatitude();
                    Double lon = address.getLongitude();
                    latitude.setText(lat.toString());
                    longitude.setText(lon.toString());
                    CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(l, 20);
                    mMap.moveCamera(cu);
                    mMap.animateCamera(cu);
                    mMap.addMarker(new MarkerOptions().position(l).title("" + address.getAddressLine(0) + "," + address.getAddressLine(1)));
                    Toast.makeText(getApplicationContext(), "Place Found", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adddriver();
            }
        });
    }

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
        LatLng India = new LatLng(19.0760, 72.8777);
        mMap.addMarker(new MarkerOptions().position(India).title("Marker in Bombay,India"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(India));
    }

    void adddriver()
    {
        String id=myRef.push().getKey();
        String dname = name.getText().toString();
        String dnumber = number.getText().toString();
        String dlatitude = latitude.getText().toString();
        String dlongitude = longitude.getText().toString();
        String demail = email.getText().toString();
        String dlicensenumberplate = licensenumberplate.getText().toString();
        if(dname.isEmpty() && dnumber.isEmpty() && dlatitude.isEmpty() && dlongitude.isEmpty() && demail.isEmpty())
        {
            name.setError("Fill the name of driver");
            number.setError("Contact information is needed");
            latitude.setError("Fill the location");
            longitude.setError("Required Field");
            email.setError("Required Field");
            licensenumberplate.setError("Required Field");
        }
        else {
        cab = new Cab(id,dname, dnumber, demail, dlicensenumberplate, dlatitude, dlongitude);
        myRef.child("Cabs").child(id).setValue(cab);
        Toast.makeText(this, "Cab added", Toast.LENGTH_SHORT).show();
        name.setText("");
        number.setText("");
        email.setText("");
        licensenumberplate.setText("");
        latitude.setText("");
        longitude.setText("");
    }
    }
}