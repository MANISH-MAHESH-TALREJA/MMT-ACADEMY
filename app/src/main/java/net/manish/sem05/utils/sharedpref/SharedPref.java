package com.pixelnx.eacademy.utils.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.pixelnx.eacademy.model.ModelSettingRecord;
import com.pixelnx.eacademy.model.modellogin.ModelLogin;

public class SharedPref {

    public static SharedPreferences myPrefs;
    public static SharedPreferences.Editor prefsEditor;
    public static SharedPref myObj;


    private SharedPref() {
    }


    public boolean getBooleanValue(String tag) {
        return myPrefs.getBoolean(tag, false);
    }


    public void setBooleanValue(String tag, boolean token) {
        prefsEditor.putBoolean(tag, token);
        prefsEditor.commit();
    }

    public void setDate(String tag, String date) {
        prefsEditor.putString(tag, date);
        prefsEditor.commit();
    }

    public String getDate(String tag) {
        return myPrefs.getString(tag, "");

    }


    public void clearAllPreferences() {
        prefsEditor = myPrefs.edit();
        prefsEditor.clear();
        prefsEditor.commit();
    }

    public static SharedPref getInstance(Context ctx) {
        if (myObj == null) {
            myObj = new SharedPref();
            myPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
            prefsEditor = myPrefs.edit();
        }
        return myObj;
    }


    public void setUser(String key, ModelLogin response) {
        prefsEditor = myPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(response);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public void setSettingInfo(String key, ModelSettingRecord Data) {
        prefsEditor = myPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Data);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public ModelSettingRecord getSettingInfo(String key) {
        Gson gson = new Gson();
        String json = myPrefs.getString(key, "");
        ModelSettingRecord obj = gson.fromJson(json, ModelSettingRecord.class);
        return obj;
    }

    public ModelLogin getUser(String key) {
        Gson gson = new Gson();
        String json = myPrefs.getString(key, "");
        ModelLogin obj = gson.fromJson(json, ModelLogin.class);
        return obj;
    }


}
