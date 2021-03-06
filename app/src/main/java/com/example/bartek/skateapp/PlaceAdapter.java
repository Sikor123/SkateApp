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



class PlaceAdapter extends ArrayAdapter<LinkedList<Place>> {
    private LinkedList<Place> places;
    private Context con;


    public PlaceAdapter(@NonNull Context context, int resource, LinkedList<Place> list) {
        super(context, resource);

        places = list;
        con = context;
    }

    @Override
    public int getCount() {
        return places.size();
    }

    public void updateData(LinkedList<Place> newPlaces){
        places = newPlaces;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       // return super.getView(position, convertView, parent);

        LayoutInflater inflater = (LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listitem,null, true);

        TextView txtTitle = row.findViewById(R.id.txtTitle);
        TextView txtDescription = row.findViewById(R.id.txtDescription);
        ImageView doMapy = row.findViewById(R.id.doMapy);
        ImageView usunImg = row.findViewById(R.id.usunImg);

        txtTitle.setText("" + (position + 1) + ". " + places.get(position).getTitle());
        txtDescription.setText(places.get(position).getDescription());



        doMapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Przechodzę do mapy.", Toast.LENGTH_SHORT).show();

                Intent mapIntent = new Intent(con, MapActivity.class);
                double lat = places.get(position).getLat();
                mapIntent.putExtra("LAT_I_NEED", lat);
                double lng = places.get(position).getLng();
                mapIntent.putExtra("LNG_I_NEED", lng);
                con.startActivity(mapIntent);
            }
        });
        usunImg.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Toast.makeText(getContext(), "Usuwam id: " + (position + 1), Toast.LENGTH_SHORT).show();
              FeedReaderDbHelper db = new FeedReaderDbHelper(con);
              db.removePlace(places.get(position).getId());
              places = db.getAllPlaces();
              notifyDataSetChanged();
          }
      });

        return row;
    }
}
