package com.example.jb.icarnfc.Requests;

import android.util.Log;

import com.example.jb.icarnfc.common.GlobalVars;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import okhttp3.Callback;
import okhttp3.Request;


/**
 * Created by Mars on 04/05/2017.
 */

public class RequestsHistoriquePro  extends GlobalVars
{
      public void getInfos(Callback callback) throws NoSuchAlgorithmException, IOException, InterruptedException {
        // Les données qui vont etre envoyées
        String urlInfosProfil = IPSERVEUR + "/ficheEntretien?UserMail="+mail+"&id_etablissement="+id;

        System.out.println(urlInfosProfil);

        Request request = new Request.Builder()
                .url(urlInfosProfil)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

}
