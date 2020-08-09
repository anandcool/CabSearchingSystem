package com.example.cabbssys.AdminSide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cabbssys.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CabAdapter extends ArrayAdapter<Cab>
{
    Context context;
    int resource;
    List<Cab> list2;

    public CabAdapter(@NonNull Context context, int resource,List<Cab> list2)
    {
        super(context,resource,list2);
        this.context=context;
        this.resource=resource;
        this.list2=list2;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater lf = LayoutInflater.from(context);
        View view = lf.inflate(R.layout.cabinfo,null);
        TextView cabname = view.findViewById(R.id.cabname);
        TextView cablicenseplatenumber = view.findViewById(R.id.cablicenseplatenumber);
        TextView cabphonenumber = view.findViewById(R.id.cabphonenubmer);
        TextView cabemail = view.findViewById(R.id.cabemail);
        TextView cablongitude = view.findViewById(R.id.cablongitude);
        TextView cablatitude = view.findViewById(R.id.cablatitude);
        Cab cb = list2.get(position);
        cabname.setText("Name:  "+cb.getName());
        cabemail.setText("Email:  "+cb.getEmail());
        cablicenseplatenumber.setText("License Number Plate:  "+cb.getLicensenumberplate());
        cabphonenumber.setText("Number:  "+cb.getNumber());
        cablongitude.setText("Longitude:  "+cb.getLongitude());
        cablatitude.setText("Latitude:  "+cb.getLatitude());
        return view;
    }
}
