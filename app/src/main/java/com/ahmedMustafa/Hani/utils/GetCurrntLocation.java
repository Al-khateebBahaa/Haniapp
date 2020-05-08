package com.ahmedMustafa.Hani.utils;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class GetCurrntLocation implements LocationListener {

    private LocationManager locationManager;
    private Context context;
    private Location mLocation;

    private boolean isHasPermssion() {
        if (Build.VERSION.SDK_INT >= 23) {

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 5);
                return true;
            }
        }
        return false;
    }

    public void openGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("تحديد الموقع")
                .setCancelable(false)
                .setMessage("يجب تحديد الموقع الخاص بك أولا")
                .setPositiveButton("تحديد الموقع", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    public GetCurrntLocation(Context context) {

        this.context = context;
        if (!isHasPermssion()){
            initLocation();
        }
    }

    private void initLocation() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isNetWorkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isNetWorkEnable) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestSingleUpdate(criteria, this, null);
        } else {
            boolean isGpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isGpsEnable) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                locationManager.requestSingleUpdate(criteria, this, null);
            }else {
                openGps();
            }
        }
    }

    public Location getmLocation() {
        return mLocation;
    }

    public double getLat() {
        if (mLocation != null) {
            return mLocation.getLatitude();
        }
        return 0;
    }

    public double getLng() {
        if (mLocation != null) {
            return mLocation.getLongitude();
        }
        return 0;
    }

    @Override
    public void onLocationChanged(Location location) {

        mLocation = location;

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
