package com.example.jb.icarnfc.Requests;

import com.example.jb.icarnfc.common.GlobalVars;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import okhttp3.Callback;
import okhttp3.Request;

/**
 * Created by mars on 11/05/2017.
 */

public class RequestInfoCar  extends GlobalVars{


    public void getInfosCar(String id, Callback callback) throws NoSuchAlgorithmException, IOException, InterruptedException {


        String urlUserCar = IPSERVEUR + "/voiture/"+id;

        System.out.println(urlUserCar);

        Request request = new Request.Builder()
                .url(urlUserCar)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }
}
