package com.example.bartek.skateapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    Button btn;
    Marker marker;
    ArrayList<Marker> markers = new ArrayList<Marker>();


    //    GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (googleServicesAvailable()) {
            Toast.makeText(this, "Perfect", Toast.LENGTH_LONG).show();
            setContentView(R.layout.map_activity);
            initMap();
        }
    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapView2);
        mapFragment.getMapAsync(this);
    }


    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cant connect to play Services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        goToLocation(52.254432, 20.844915 , 15);


    }

    private void goToLocation(double lat, double lng, float i) {
        LatLng ll = new LatLng(lat , lng);
        CameraUpdate update  = CameraUpdateFactory.newLatLngZoom(ll , i);
        mGoogleMap.moveCamera(update);
    }

    private void goToLocation(double lat, double lng) {
        LatLng ll = new LatLng(lat , lng);
        CameraUpdate update  = CameraUpdateFactory.newLatLng(ll);
        mGoogleMap.moveCamera(update);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mapTypeNone:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.mapTypeNormal:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeSatellite:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeTerrain:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeHybrid:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;

            default:
                break;
        }
        return false;
    }


    public void dodaj(View view){
        Button b = (Button)view;
        String buttonText = b.getText().toString();
        b.setText("dodaj");
        if(buttonText == "zapisz")
        {
            Log.i("Action::", "save!!");
            b.setText("dodaj");
            if(marker != null)
                markers.add(marker);
                marker = null;
        }
        else
        {

            Log.i("Action::", "clicked!!");
            CameraPosition cm = mGoogleMap.getCameraPosition();
            Log.i("Action::", cm.target.toString());
            b.setText("zapisz");
            MarkerOptions options = new MarkerOptions().title("mojdom").position(cm.target).draggable(true);
            marker = mGoogleMap.addMarker(options);
        }



    }



}
