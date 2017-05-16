package com.example.jb.icarnfc.common;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import okhttp3.OkHttpClient;
/**
 * Created by Mars on 02/05/2017.
 */
public class GlobalVars extends AppCompatActivity {


        public static final String IPSERVEUR="http://192.168.1.14:8080";

        public static final String MY_PREFS_NAME = "MyPrefsFile";

        public OkHttpClient okHttpClient = new OkHttpClient();


        // TODO: 15/05/2017 Recuperer les variables de session

        protected void onCreate(@Nullable Bundle saveInstanceState) {
            super.onCreate(saveInstanceState);



        }


}
