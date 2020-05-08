package com.ahmedMustafa.Hani.utils;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.common.api.GoogleApiClient;

public class LocationLiveData extends LiveData<Location> implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private GoogleApiClient googleApiClient;
    public LocationLiveData(Context context) {
        googleApiClient =
                new GoogleApiClient.Builder(context, this, this)
                        .addApi(LocationServices.API)
                        .build();
    }
    @Override
    protected void onActive() {
        // Wait for the GoogleApiClient to be connected
        googleApiClient.connect();
    }
    @Override
    protected void onInactive() {
        if (googleApiClient.isConnected()) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
        googleApiClient.disconnect();
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(Bundle connectionHint) {
        // Try to immediately find a location
        @SuppressLint("MissingPermission") Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null) {
            setValue(lastLocation);
        }
        // Request updates if there’s someone observing
        if (hasActiveObservers()) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, new LocationRequest(), this);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,new LocationRequest(), (com.google.android.gms.location.LocationListener) this);
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        // Deliver the location changes
        setValue(location);
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

    @Override
    public void onConnectionSuspended(int cause) {
        // Cry softly, hope it comes back on its own
    }
    @Override
    public void onConnectionFailed(
            @NonNull ConnectionResult connectionResult) {
        // Consider exposing this state as described here:
        // https://d.android.com/topic/libraries/architecture/guide.html#addendum
    }
}