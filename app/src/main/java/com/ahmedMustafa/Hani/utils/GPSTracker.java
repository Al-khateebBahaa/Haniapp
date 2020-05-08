package com.ahmedMustafa.Hani.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnSuccessListener;


/**
 */

public class GPSTracker implements LocationListener {
    private Context context;
    private boolean canGetLocation, isGpsEnable, isNetworkEnable;
    private Location location;
    private double lat, lon;
    private LocationManager locationManager;

    private final int MIN_TIME_BT_UPDATES = 1000 * 60 * 1;
    private final int MIN_DISTANS_FOR_UPDATES = 10;
    private PrefManager manager;

    public GPSTracker(Context context) {
        this.context = context;
        manager = new PrefManager(context);
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            isGpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isNetworkEnable && !isGpsEnable) {
                canGetLocation = false;
                Log.e("provider", "np provider_enable");
            } else {
                canGetLocation = true;
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                }
                if (isNetworkEnable) {
                    Log.e("provider", "network_enable");
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BT_UPDATES, MIN_DISTANS_FOR_UPDATES, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                            Log.e("location_network", location.getLatitude() + "");
                        }
                    }


                } else if (isGpsEnable) {
                    Log.e("provider", "gps_enable");
                    locationManager.
                            requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BT_UPDATES, MIN_DISTANS_FOR_UPDATES, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            Log.e("location_gps", location.getLatitude() + "");
                            lon = location.getLongitude();
                            lat = location.getLatitude();
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e("ex", e.getMessage() + "");
        }
        return location;
    }

    @Override
    public void onLocationChanged(Location location) {

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

    public boolean isCanGetLocation() {
        return canGetLocation;
    }

    public void stopUseGps() {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    public double getLat() {
        if (lat == 0.0) {
            initLocationApi();
        }
        return lat;
    }

    public double getLon() {
        if (lon == 0.0) {
            initLocationApi();
        }
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    private FusedLocationProviderClient mFusedLocationClient;

    private void initLocationApi() {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                }else {
                    lat =Double.parseDouble(manager.getLat());
                    lon =Double.parseDouble(manager.getLng());
                }
            }
        });
    }
}