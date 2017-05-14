package com.example.jb.icarnfc.Requests;

import com.example.jb.icarnfc.common.GlobalVars;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import okhttp3.Callback;
import okhttp3.Request;

/**
 * Created by mars on 14/05/2017.
 */

public class RequestListCarDb extends GlobalVars {

    public void getCarsDb(Callback callback) throws NoSuchAlgorithmException, IOException, InterruptedException {
        // Les données qui vont etre envoyées
        String carDb = IPSERVEUR +"/CarsDb";
        System.out.println(carDb );

        Request request = new Request.Builder()
                .url(carDb)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }


}
