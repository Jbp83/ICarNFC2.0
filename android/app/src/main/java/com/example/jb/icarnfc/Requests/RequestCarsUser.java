package com.example.jb.icarnfc.Requests;

import com.example.jb.icarnfc.common.GlobalVars;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import okhttp3.Callback;
import okhttp3.Request;

/**
 * Created by mars on 04/05/2017.
 */

public class RequestCarsUser extends GlobalVars{


    public void getCars(Callback callback) throws NoSuchAlgorithmException, IOException, InterruptedException {
        // Les données qui vont etre envoyées
        String urlInfosProfil = IPSERVEUR +"userCars";
        String urlParams = "?UserMail=mars8.6@hotmail.fr";
        System.out.println(urlInfosProfil + urlParams);

        Request request = new Request.Builder()
                .url(urlInfosProfil + urlParams)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

}
