package com.example.jb.icarnfc.common;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import okhttp3.OkHttpClient;

/**
 * Created by Mars on 02/05/2017.
 */

public class GlobalVars extends AppCompatActivity {

    public static final String IPSERVEUR="http://192.168.7.39:8080";

    public OkHttpClient okHttpClient = new OkHttpClient();

    public static final String PREFS_MAIL = "mail";
    //public String mail="mars";
    public SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;




    public String getData(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }


    }


