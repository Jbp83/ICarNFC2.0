package com.example.jb.icarnfc.Requests;

import com.example.jb.icarnfc.common.GlobalVars;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import okhttp3.Callback;
import okhttp3.Request;

/**
 * Created by mars on 15/05/2017.
 */

public class RequestScanPro extends GlobalVars{

    public void GetCarGuid(String GUID, Callback callback) throws NoSuchAlgorithmException, IOException, InterruptedException {

        String urlInfosProfil = IPSERVEUR +"/GetCarWithGuid";
        String urlParams = "?GUID="+GUID;


        Request request = new Request.Builder()
                .url(urlInfosProfil + urlParams)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }
}
