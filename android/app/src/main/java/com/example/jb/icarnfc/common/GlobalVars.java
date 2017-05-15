package com.example.jb.icarnfc.common;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import okhttp3.OkHttpClient;

/**
 * Created by Mars on 02/05/2017.
 */

public class GlobalVars extends AppCompatActivity {


        public static final String IPSERVEUR="http://10.136.124.93:8080";
        public static final String MY_PREFS_NAME = "MyPrefsFile";

        public OkHttpClient okHttpClient = new OkHttpClient();

        public static final String PREFS_NAME = "PrefAlex";
        public static final String PREFS_MAIL = "";
        public static final String PREFS_IDUSER = "";




        public SharedPreferences sharedPreferences;
        public SharedPreferences.Editor editor;

        public String idusersession,emailsession;


        // TODO: 15/05/2017 Recuperer les variables de session

        protected void onCreate(@Nullable Bundle saveInstanceState) {
            super.onCreate(saveInstanceState);

            sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            editor = sharedPreferences.edit();

            emailsession = sharedPreferences.getString(PREFS_MAIL, null);
            idusersession = sharedPreferences.getString(PREFS_IDUSER, null);


        }





        //editor.commit();

}
