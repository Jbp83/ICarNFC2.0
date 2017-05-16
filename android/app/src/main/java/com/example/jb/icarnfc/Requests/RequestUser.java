package com.example.jb.icarnfc.Requests;

import com.example.jb.icarnfc.common.GlobalVars;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import okhttp3.Callback;
import okhttp3.Request;

/**
 * Created by Jb on 16/05/2017.
 */

public class RequestUser extends GlobalVars {

    public void getUserbyEntreprise(String id, Callback callback) throws NoSuchAlgorithmException, IOException, InterruptedException {


        String urlUserCar = IPSERVEUR + "/UserbyEntreprise/"+id;

        Request request = new Request.Builder()
                .url(urlUserCar)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    public void getCarbyUSer(String id, Callback callback) throws NoSuchAlgorithmException, IOException, InterruptedException {


        String urlUserCar = IPSERVEUR + "/userCars/"+id;

        Request request = new Request.Builder()
                .url(urlUserCar)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }
}
