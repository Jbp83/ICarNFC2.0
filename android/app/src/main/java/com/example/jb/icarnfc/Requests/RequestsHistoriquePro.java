package com.example.jb.icarnfc.Requests;

import android.util.Log;

import com.example.jb.icarnfc.common.GlobalVars;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mars on 04/05/2017.
 */

public class RequestsHistoriquePro  extends GlobalVars {
   /*
    private static final String TAG ="dateentretien" ;
    String url="/ficheEntretien?UserMail=mars8.6@hotmail.fr&id_etablissement=1"; */

      public void getInfos(Callback callback) throws NoSuchAlgorithmException, IOException, InterruptedException {
        // Les données qui vont etre envoyées
        String urlInfosProfil = IPSERVEUR + "/ficheEntretien?UserMail=mars8.6@hotmail.fr&id_etablissement=1";

        System.out.println(urlInfosProfil);

        Request request = new Request.Builder()
                .url(urlInfosProfil)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

}
