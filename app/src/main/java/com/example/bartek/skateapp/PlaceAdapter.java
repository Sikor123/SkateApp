package com.example.bartek.skateapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class PlaceAdapter extends ArrayAdapter<LinkedList<Place>> {
    private LinkedList<Place> places;
    Context con;

    public PlaceAdapter(@NonNull Context context, int resource, LinkedList<Place> list) {
        super(context, resource);

        places = list;
        con = context;
    }

    @Override
    public int getCount() {
        return places.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       // return super.getView(position, convertView, parent);

        LayoutInflater inflater = (LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listitem,null, true);

        TextView txtId = row.findViewById(R.id.txtId);
        TextView txtTitle = row.findViewById(R.id.txtTitle);
        TextView txtDescription = row.findViewById(R.id.txtDescription);
        ImageView doMapy = row.findViewById(R.id.doMapy);

        txtId.setText("" + (position + 1));
        txtTitle.setText(places.get(position).getTitle());
        txtDescription.setText(places.get(position).getDescription());

        doMapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Kliknalem w id: " + (position + 1), Toast.LENGTH_SHORT).show();

                Intent mapIntent = new Intent(con, MapActivity.class);
                double lat = places.get(position).getLat();
                mapIntent.putExtra("LAT_I_NEED", lat);
                double lng = places.get(position).getLng();
                mapIntent.putExtra("LNG_I_NEED", lng);
                con.startActivity(mapIntent);
            }
        });

        return row;
    }
}
