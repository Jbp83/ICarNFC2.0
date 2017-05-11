package com.example.jb.icarnfc.Requests;


import com.example.jb.icarnfc.common.GlobalVars;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import okhttp3.Callback;
import okhttp3.Request;

/**
 * Created by Mars on 04/05/2017.
 */

public class RequestListCar  extends GlobalVars{

    public void getCarsUser(String mail,Callback callback) throws NoSuchAlgorithmException, IOException, InterruptedException {
        // Les données qui vont etre envoyées

        /*UserSessionManager session;

        session = new UserSessionManager(getApplicationContext());


        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // get id User
        String id = user.get(UserSessionManager.KEY_ID);

        // get email
        String email = user.get(UserSessionManager.KEY_EMAIL);

        Log.v("id",id);
        Log.v("email",email);*/



        mail="mars8.6@hotmail.fr";
        String urlUserCar = IPSERVEUR + "/userCars?UserMail="+mail;

        System.out.println(urlUserCar);

        Request request = new Request.Builder()
                .url(urlUserCar)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }
}
