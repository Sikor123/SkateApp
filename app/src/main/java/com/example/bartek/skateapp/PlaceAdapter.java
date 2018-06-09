package com.example.bartek.skateapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       // return super.getView(position, convertView, parent);

        LayoutInflater inflater = (LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listitem,null, true);

        TextView txtId = row.findViewById(R.id.txtId);
        TextView txtTitle = row.findViewById(R.id.txtTitle);
        TextView txtDescription = row.findViewById(R.id.txtDescription);

        txtId.setText("" + places.get(position).getId());
        txtTitle.setText(places.get(position).getTitle());
        txtDescription.setText(places.get(position).getDescription());

        return row;
    }
}
