package com.example.cabbssys.AdminSide;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.cabbssys.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class AddDriver extends AppCompatActivity {

    EditText name, licensenumber, email, phone, address;
    Button btnadddriver;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    Driver driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);
        name = findViewById(R.id.dname);
        licensenumber = findViewById(R.id.licensenumber);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        btnadddriver = findViewById(R.id.btndriver);
        myRef = database.getReference();
        btnadddriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDriver();
            }
        });
    }

    void addDriver()
    {
        String id = myRef.push().getKey();
        String dname = name.getText().toString();
        String dphone = phone.getText().toString();
        String demail = email.getText().toString();
        String daddress = address.getText().toString();
        String dlicensenumber = licensenumber.getText().toString();

        if (dname.isEmpty() && dphone.isEmpty() && dlicensenumber.isEmpty() && demail.isEmpty() && daddress.isEmpty())
        {
            name.setError("Fill the name of driver");
            phone.setError("Contact information is needed");
            email.setError("Required Field");
            licensenumber.setError("Required Field");
            address.setError("Required Field");
        }
        else
        {
            driver = new Driver(id,dname,dlicensenumber,demail,dphone,daddress);
            myRef.child("Drivers").child(id).setValue(driver);
            Toast.makeText(this, "Driver added", Toast.LENGTH_SHORT).show();
            name.setText("");
            phone.setText("");
            email.setText("");
            licensenumber.setText("");
            address.setText("");
        }
    }

}
