package com.appleto.Vitl.utills;

import android.content.Context;
import android.content.SharedPreferences;

public class Storage {

    private static final int LOGIN_STATUS = 0;

    private static final String USERID = null;
    private static final String USERNAME = null;
    private static final String USEREMAIL = null;
    private static final String USERPHONE = null;
    private static final String USERIMAGE = null;

    private static final String FIREBASE_TOKEN = null;

    private static final String USER_LATITUDE = null;
    private static final String USER_LONGITUDE = null;

    private static final int VENDER_STATUS = 0;
    private static final String VENDER_ID = null;
    private static final String MIN_VALUE = "0";
    private static final String MAX_VALUE = "3000";

    private Context context;

    public Storage(Context context) {
        this.context = context;
    }

    public SharedPreferences.Editor getPreferencesEditor() {
        return getsharedPreferences().edit();
    }

    private SharedPreferences getsharedPreferences() {
        return context.getSharedPreferences("VitlApp", Context.MODE_PRIVATE);
    }

    public void saveLogInSate(Integer p) {
        getPreferencesEditor().putInt("logged_in", p).commit();
    }

    public int getLogInState() {
        return getsharedPreferences().getInt("logged_in", LOGIN_STATUS);
    }

    public void saveUserName(String name) {
        getPreferencesEditor().putString("name", name).commit();
    }

    public String getUserName() {
        return getsharedPreferences().getString("name", USERNAME);
    }

    public void saveUserEmail(String email) {
        getPreferencesEditor().putString("email", email).commit();
    }

    public String getUserEmail() {
        return getsharedPreferences().getString("email", USEREMAIL);
    }


    public void saveUserPhone(String phone) {
        getPreferencesEditor().putString("phone", phone).commit();
    }

    public String getUserPhone() {
        return getsharedPreferences().getString("phone", USERPHONE);
    }

    public void saveUserImage(String image) {
        getPreferencesEditor().putString("image", image).commit();
    }

    public String getUserImage() {
        return getsharedPreferences().getString("image", USERIMAGE);
    }


    public void saveVenderSignin(Integer v) {
        getPreferencesEditor().putInt("vender_in", v).commit();
    }

    public int getVenderSignin() {
        return getsharedPreferences().getInt("vender_in", VENDER_STATUS);
    }

    public void saveVenderId(String id) {
        getPreferencesEditor().putString("vender_id", id).commit();
    }

    public String getVenderId() {
        String vendorID = getsharedPreferences().getString("vender_id", VENDER_ID);
        if(vendorID == null){
            return "";
        } else {
            return vendorID;
        }
    }

    public String getFirebaseToken() {
        return getsharedPreferences().getString("fire_token", FIREBASE_TOKEN);
    }

    public void saveFirebaseToken(String token) {
        getPreferencesEditor().putString("fire_token", token).commit();

    }

    public void saveUserId(String id) {
        getPreferencesEditor().putString("id", id).commit();
    }

    public String getUserId() {
        return getsharedPreferences().getString("id", USERID);
    }


    public void saveUserlatitude(String lat) {
        getPreferencesEditor().putString("lat", lat).commit();
    }

    public String getlatitude() {
        return getsharedPreferences().getString("lat", USER_LATITUDE);
    }


    public void saveUserlongitude(String longi) {
        getPreferencesEditor().putString("long", longi).commit();
    }

    public String getlongitude() {
        return getsharedPreferences().getString("long", USER_LONGITUDE);
    }

    public void saveMinValue(String min) {
        getPreferencesEditor().putString("min_value", min).commit();
    }

    public String getMinValue() {
        return getsharedPreferences().getString("min_value", MIN_VALUE);
    }

    public void saveMaxValue(String max) {
        getPreferencesEditor().putString("max_value", max).commit();
    }

    public String getMaxValue() {
        return getsharedPreferences().getString("max_value", MAX_VALUE);
    }
}
