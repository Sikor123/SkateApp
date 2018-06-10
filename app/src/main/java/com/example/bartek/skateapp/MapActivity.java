package com.example.bartek.skateapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 *Klasa wyświetlająca aktywność z dużą mapą zajmującą prawie cały ekran.
 * Na mapie wyświetlane są znaczniki w miejscach, gdzie zostały dodane spoty dla rolkarzy.
 * w prawym górnym rogu znajduje się przycisk, który wyświetla menu z wyborem mapy
 * w lewym górnym rogu mapy znajduje się przycisk do dodawania spotów.
 * po ustawieniu spotu w odpowiednie miejsce za pomocą metody Drag&Drop,
 * nazeży zatwierdzić miejsce klikając w ptaszek i wpisać dane do okna które się wyświetli.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private int STORAGE_PERMISSION_CODE = 1;
    GoogleMap mGoogleMap;
    Marker marker;
    ArrayList<MapMarker> markers = new ArrayList<MapMarker>();
    FeedReaderDbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ImageView usunImg = findViewById(R.id.usunImg);


        if (googleServicesAvailable()) {
            Toast.makeText(this, "Perfect", Toast.LENGTH_LONG).show();
            setContentView(R.layout.map_activity);
            ImageButton b = (ImageButton) findViewById(R.id.dodaj);
            b.setBackgroundResource(R.drawable.ic_add);
            initMap();
        }
    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapView2);
        mapFragment.getMapAsync(this);
    }


    private boolean googleServicesAvailable() {
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

    /**
     * Metoda, która ustawia najważniejsze parametry mapy, przystosowane do naszej aplikacji
     * @param googleMap obiekt mapy Google
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            /**
             * Metoda wywoływana podczas podnoszenia markera metodą Drag&Drop
             * @param marker obiekt markera który będziemy przesuwac
             */
            @Override
            public void onMarkerDragStart(Marker marker) {

            }
            /**
             * Metoda wywoływana podczas przenoszenia markera metodą Drag&Drop
             * @param marker obiekt markera który będziemy przesuwac
             */
            @Override
            public void onMarkerDrag(Marker marker) {

            }
            /**
             * Metoda wywoływana podczas opuszczania markera metodą Drag&Drop
             * @param m obiekt markera który jest teraz na mapie
             */
            @Override
            public void onMarkerDragEnd(Marker m) {
                    marker.setPosition(m.getPosition());
            }
        });
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=   PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, STORAGE_PERMISSION_CODE);
            ActivityCompat.requestPermissions(this,
                    new String[] {android.Manifest.permission.ACCESS_COARSE_LOCATION}, STORAGE_PERMISSION_CODE);


            Log.i("Action::", "nie uda sie ustawic mapy!" + ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) + "\n"



            + ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION));
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        if(mGoogleMap != null){
            mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter(){



                @Override
                public View getInfoWindow(Marker marker) {
                    return  null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    View v = getLayoutInflater().inflate(R.layout.info_window, null);


                    TextView tvLocality = (TextView) v.findViewById(R.id.tv_locality);
                    TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
                    TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
                    TextView tvSnippet = (TextView) v.findViewById(R.id.tv_snippet);


                    LatLng ll = marker.getPosition();
                    tvLocality.setText(marker.getTitle());
                    tvLat.setText("Latitude: " + ll.latitude);
                    tvLng.setText("Longitude: " + ll.longitude);
                    tvSnippet.setText(marker.getSnippet());


                    return v;
                }
            });
        }


        double newLat, newLng;
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            newLat = 52.254432;
            newLng = 20.844915;
        } else {
            newLat = extras.getDouble("LAT_I_NEED");
            newLng = extras.getDouble("LNG_I_NEED");
        }




        goToLocation(newLat, newLng , 15);
        db = new FeedReaderDbHelper(this);
        LinkedList<Place> miejsca;
        miejsca = db.getAllPlaces();
        Log.i("DB::", "jestem");
        for (Place m:miejsca ) {
            Log.i("DB::", "wlazlem");
            MarkerOptions options = new MarkerOptions().position(new LatLng(m.getLat() , m.getLng())).title(m.getTitle()).snippet(m.getDescription());
            Log.i("DB::", "wylazlem");
            marker = mGoogleMap.addMarker(options);
            markers.add(new MapMarker(marker , m.getId()));
            int height = 100;
            int width = 100;
            BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.mipmap.rolek);
            Bitmap b=bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

            marker.setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        }

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

    /**
     * metoda wywoływana podczas tworzenia menu
     * @param menu jest to menu, które będzie wyświetlane
     * @return zwracany typ informuje o poprawności wywołania metody
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * metoda wywołująca się podczas kliknięcia na jakiś element rozwojanego menu.
     * zmienia typ wyświetlanej mapy.
     * @param item jets to obiekt klasy item będący rekordem menu na który kliknął urzytkownik.
     * @return metoda zwraca false za każdym razem
     */
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

    private boolean dodaj = true;
    protected void dodaj(View view){
        ImageButton b = (ImageButton) view;


        if(dodaj == false)
        {
            Log.i("Action::", "save!!");
            Intent intent = new Intent(this , PopActivity.class);
            startActivityForResult(intent , 999);
            b.setBackgroundResource(R.drawable.ic_add);
            dodaj = true;


        }
        else
        {
            dodaj = false;
            b.setBackgroundResource(R.drawable.ic_done);
            CameraPosition cm = mGoogleMap.getCameraPosition();
            Toast.makeText(this, "Drag&Drop", Toast.LENGTH_LONG).show();

            MarkerOptions options = new MarkerOptions().title("mojdom").position(cm.target).draggable(true);
            marker = mGoogleMap.addMarker(options);
        }
    }

        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 999 && resultCode == RESULT_OK)
        {

            long id;
            String tytul = data.getStringExtra("tytul") ;
            marker.setTitle(tytul);
            marker.setSnippet(data.getStringExtra("opis"));
            marker.setVisible(true);
            marker.setDraggable(false);
            Log.i("Action::", marker.getId());

            int height = 100;
            int width = 100;
            BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.mipmap.rolek);
            Bitmap b=bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

            marker.setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            LatLng ll = marker.getPosition();
            marker.setPosition(ll);
            id = db.addPlace(marker.getTitle() , marker.getSnippet() ,ll.latitude , ll.longitude );

            if(marker != null){

            markers.add(new MapMarker(marker , id));
            marker = null;
        }
        }
    }






}
