package com.ahmedMustafa.Hani.activity.auth;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.utils.GPSTracker;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SelectLocationActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GPSTracker gpsTracker;
    private double lat, lng;
    private View but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        but = findViewById(R.id.but);
        mapFragment.getMapAsync(this);
        gpsTracker = new GPSTracker(this);
        showProgress();
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetAddress().execute(mlocation);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!gpsTracker.isCanGetLocation()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("تحديد الموقع")
                    .setCancelable(false)
                    .setMessage("يجب تحديد الموقع الخاص بك أولا")
                    .setPositiveButton("تحديد الموقع", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, 4);
                        }
                    })
                    .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    }).create().show();
        } else {
            lat = gpsTracker.getLat();
            lng = gpsTracker.getLon();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        hideProgress();
        mMap = googleMap;
        LatLng sydney = new LatLng(lat, lng);
        mlocation = sydney;
        mMap.addMarker(new MarkerOptions().position(sydney)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.room)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                Marker marker2 = mMap.addMarker(new MarkerOptions().position(latLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.room)));
                marker2.showInfoWindow();
                mlocation = latLng;
            }
        });
    }

    private LatLng mlocation;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (gpsTracker.isCanGetLocation()) {
            lat = gpsTracker.getLat();
            lng = gpsTracker.getLon();
        }
    }

    private class GetAddress extends AsyncTask<LatLng, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();

        }

        @Override
        protected String doInBackground(LatLng... latLngs) {

            List<Address> addresses;
            Geocoder geocoder;

            try {
                geocoder = new Geocoder(SelectLocationActivity.this, Locale.getDefault());
                addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                return address;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            hideProgress();
            if (s == null) {
                toast("خطأ في تحديد الموقع ، برجاء المحاولة مرة أخري");
            }else {
                Intent i = new Intent();
                i.putExtra("lat", lat + "");
                i.putExtra("lng", lng + "");
                i.putExtra("address", s);
                setResult(RESULT_OK, i);
                finish();
            }


        }
    }
}
