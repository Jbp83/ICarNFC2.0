package com.example.jb.icarnfc.Requests;

import com.example.jb.icarnfc.common.GlobalVars;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

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

    public void setFiche(String Date,String idVoiture,String id_etab, String idUser, String description,String id_mecanicien,Callback callback) throws NoSuchAlgorithmException, IOException, InterruptedException {

        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("date_creation", Date);
        formBuilder.add("id_voiture", idVoiture);
        formBuilder.add("id_etablissement", id_etab);
        formBuilder.add("id_utilisateur", idUser);
        formBuilder.add("description", description);
        formBuilder.add("id_mecanicien", id_mecanicien);

        RequestBody formBody = formBuilder.build();

        Request request = new Request.Builder()
                .url(IPSERVEUR+"/addEntretien")
                .post(formBody)
                .build();

        OkHttpClient client = new OkHttpClient();
    }
}
