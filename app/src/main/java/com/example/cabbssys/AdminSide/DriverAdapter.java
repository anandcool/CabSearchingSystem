package com.example.cabbssys.AdminSide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.cabbssys.R;
import java.util.List;

public class DriverAdapter extends ArrayAdapter<Driver>
{
    Context context;
    int resource;
    List<Driver> list;
    public DriverAdapter(@NonNull Context context, int resource, List<Driver> list)
    {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.driverinfo,null);
        TextView drivername = view.findViewById(R.id.drivername);
        TextView driverlicensenumber = view.findViewById(R.id.driverlicensenumber);
        TextView driverphonenubmer = view.findViewById(R.id.driverphonenubmer);
        TextView driveremail = view.findViewById(R.id.driveremail);
        Driver driver = list.get(position);
        drivername.setText("Driver Name: "+driver.getName());
        driverlicensenumber.setText("License Number: "+driver.getLicensenumber());
        driverphonenubmer.setText("Phone Number: "+driver.getPhone());
        driveremail.setText("Email: "+driver.getEmail());
        return view;
    }
}

