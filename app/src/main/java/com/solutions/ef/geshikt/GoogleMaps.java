package com.solutions.ef.geshikt;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class GoogleMaps extends BaseActivity implements OnMapReadyCallback {
    @Override
    int getContentViewId() {
        return R.layout.activity_google_maps;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.google_maps;
    }




        LocationManager locationManager;
        LocationListener locationListener;

        EditText editText4;


        private GoogleMap mMap;

        private PlaceAutocompleteFragment placeAutocompleteFragment;
        Marker marker;

        /*int PLACE_PICKER_REQUEST = 1;
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();


       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == PLACE_PICKER_REQUEST) {
                if (resultCode == RESULT_OK) {
                    Place place = PlacePicker.getPlace(data, this);
                    String toastMsg = String.format("Place: %s", place.getName());
                    Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                }
            }
        }*/

    LatLng latLngAuto;
    boolean autoPlaceIsTrue=false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            editText4=findViewById(R.id.editText4);


            placeAutocompleteFragment=(PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.auto_complete);

            placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                   latLngAuto=place.getLatLng();
                   autoPlaceIsTrue=true;
                    if(marker!=null){
                        marker.remove();
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngAuto,15));
                    marker=mMap.addMarker(new MarkerOptions().position(latLngAuto).title(place.getName().toString()));
                }

                @Override
                public void onError(Status status) {

                }
            });
           // setContentView(R.layout.activity_google_maps);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

        private void updateMap(Location location) {
            LatLng jobLocation = new LatLng(location.getLatitude(), location.getLongitude());
            if(!autoPlaceIsTrue){
                mMap.clear();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jobLocation,15));
                mMap.addMarker(new MarkerOptions().position(jobLocation).title("job 1"));
            }else {
                mMap.addMarker(new MarkerOptions().position(jobLocation).title("job 1"));

            }

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


            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    updateMap(location);
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                //////////////locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                ;
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocation != null) {
                    updateMap(lastKnownLocation);
                }

            }

           /* // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        }
        public void goToLocation(double lat, double lon,int zoom,String e){

            LatLng latLng=new LatLng(lat,lon);
            CameraUpdate update=CameraUpdateFactory.newLatLngZoom(latLng,zoom);
            mMap.moveCamera(update);
            mMap.addMarker(new MarkerOptions().position(latLng).title(e));

        }


        public void findOnMap(View view){
            Geocoder geocoder=new Geocoder(this);

            try {
                List<Address> addresses=geocoder.getFromLocationName(editText4.getText().toString(),1);

                Address address=addresses.get(0);
                double lat=address.getLatitude();
                double lon=address.getLongitude();
                String s=address.getAddressLine(0);
                goToLocation(lat,lon,15,s);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (requestCode == 1) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (lastKnownLocation != null) {
                            updateMap(lastKnownLocation);
                        }
                    }
                }
            }
        }
    }

