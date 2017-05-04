package com.example.jb.icarnfc.common;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import okhttp3.OkHttpClient;

/**
 * Created by Mars on 02/05/2017.
 */

public class GlobalVars extends AppCompatActivity {

    public static final String IPSERVEUR="http://192.168.1.23:8080";
    public SharedPreferences.Editor editor;
    public SharedPreferences sharedPreferences;

    public OkHttpClient okHttpClient = new OkHttpClient();


   /* public String username, token, partyId, partyName, firstname, name, email, pseudo, avatar;

    sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    editor = sharedPreferences.edit();


    username = sharedPreferences.getString(PREFS_USERNAME, null);
    token = sharedPreferences.getString(PREFS_TOKEN, null);
    partyId = sharedPreferences.getString(PREFS_PARTY_ID, null);
    partyName = sharedPreferences.getString(PREFS_PARTY_NAME, null);
    avatar = sharedPreferences.getString(PREFS_PLAYER_AVATAR, null);*/
}
