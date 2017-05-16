package com.example.jb.icarnfc.Requests;


import com.example.jb.icarnfc.common.GlobalVars;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import okhttp3.Callback;
import okhttp3.Request;

/**
 * Created by Mars on 04/05/2017.
 */

public class RequestListCarUser  extends GlobalVars{

    public void getCarsUser(String id,Callback callback) throws NoSuchAlgorithmException, IOException, InterruptedException {


        String urlUserCar = IPSERVEUR + "/userCars/"+id;

        System.out.println(urlUserCar);

        Request request = new Request.Builder()
                .url(urlUserCar)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }
}
