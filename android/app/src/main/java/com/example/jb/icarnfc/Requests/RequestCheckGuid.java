package com.example.jb.icarnfc.Requests;

import com.example.jb.icarnfc.common.GlobalVars;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import okhttp3.Callback;
import okhttp3.Request;

/**
 * Created by Mars on 09/05/2017.
 */

public class RequestCheckGuid extends GlobalVars {


    public void checkguid(String GUID,Callback callback) throws NoSuchAlgorithmException, IOException, InterruptedException {

        String urlInfosProfil = IPSERVEUR +"/checkguid";
        String urlParams = "?GUID="+GUID;


        Request request = new Request.Builder()
                .url(urlInfosProfil + urlParams)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }
}
