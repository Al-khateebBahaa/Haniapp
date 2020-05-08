package com.ahmedMustafa.Hani.utils;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class PrefManager {

    private final String FileName = "prefManager";
    private final String id = "id";
    private final String firstTime = "firstTime";
    private final String name = "name";
    private final String token = "token";
    private final String email = "email";
    private final String phone = "phone";
    private final String image = "image";
    private final String loginMode = "loginMode";
    private final String lat = "lat";
    private final String lng = "lng";
    private final String nearbyNotify = "nearby_notify";
    private final String notify = "notify";
    private final String userType = "user type";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public @Inject
    PrefManager(Context context) {
        pref = context.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setUserType(int type){
        editor.putInt(this.userType,type).apply();
    }

    public int getUserType(){
        return pref.getInt(userType,0);
    }

    public void setFirstTime(){
        editor.putBoolean(firstTime,false);
    }

    public boolean isFirstTime(){
       return pref.getBoolean(firstTime,true);
    }
    public void setId(String id) {
        editor.putString(this.id, id).apply();
    }

    public void setName(String name) {
        editor.putString(this.name, name).apply();
    }

    public void setEmail(String email) {
        editor.putString(this.email, email).apply();
    }

    public void setPhone(String phone) {
        editor.putString(this.phone, phone).apply();
    }

    public void setToken(String token) {
        editor.putString(this.token, token).apply();
    }

    public void setLoginMode(int mode) {
        editor.putInt(this.loginMode, mode).apply();
    }

    public void setLat(String lat) {
        editor.putString(this.lat, lat).apply();
        ;
    }

    public void setNearbyNotify(boolean isNotify) {
        editor.putBoolean(nearbyNotify, isNotify).apply();
    }

    public boolean isNearbyNotify() {
        return pref.getBoolean(nearbyNotify, true);
    }

    public void setLng(String lng) {
        editor.putString(this.lng, lng).apply();
    }

    public String getLat() {
        return pref.getString(lat, "0");
    }

    public String getLng() {
        return pref.getString(lng, "0");
    }

    public String getName() {
        return pref.getString(name, "");
    }

    public String getEmail() {
        return pref.getString(email, "");
    }

    public String getPhone() {
        return pref.getString(phone, "");
    }

    public int getLoginMode() {
        return pref.getInt(loginMode, 0);
    }

    public void setLogout() {
        setUserType(0);
        setLoginMode(0);
    }

    public boolean isLogin() {
        return getLoginMode() != 0;
    }

    public String getUserId() {
        return pref.getString(id, "0");
    }

    public void setImage(String image) {
        editor.putString(this.image, image).apply();
    }

    public String getImage() {
        return pref.getString(image, "");
    }

    public void updateData(String id, String name, String email, String phone, String lat, String lng,String image) {
        setId(id);
        setName(name);
        setEmail(email);
        setPhone(phone);
        setLat(lat);
        setLng(lng);
        setImage(image);
    }

}
