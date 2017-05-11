package com.example.jb.icarnfc.Requests;

import com.example.jb.icarnfc.common.GlobalVars;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import okhttp3.Callback;
import okhttp3.Request;

/**
 * Created by mars on 11/05/2017.
 */

public class RequestEtablissement extends GlobalVars{


    public void getEtablissement(Callback callback) throws NoSuchAlgorithmException, IOException, InterruptedException {
        // Les données qui vont etre envoyées
        String urlInfosProfil = IPSERVEUR +"/entreprises";
        System.out.println(urlInfosProfil );

        Request request = new Request.Builder()
                .url(urlInfosProfil)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }
}
