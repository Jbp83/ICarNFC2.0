package com.example.jb.icarnfc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.example.jb.icarnfc.Requests.RequestsHistoriquePro;
import com.example.jb.icarnfc.common.GlobalVars;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class HistoriqueEntretienPro extends GlobalVars {

    private static final String TAG ="dateentretien" ;
    private final OkHttpClient client = new OkHttpClient();
    String date,id_voiture,id_etablissement,id_utilisateur,idjson,IdDetailEntretien,mail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique_entretien_pro);
        getProfilInfos();

        Intent intent = getIntent();
        mail = intent.getStringExtra("mailpro");
        Log.v("MaiUSER",mail);
    }


    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        getProfilInfos();
    }

    private void getProfilInfos() {
        try {
            RequestsHistoriquePro historiquepro = new RequestsHistoriquePro();
            historiquepro.getInfos(mail,new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String infosProfil = response.body().string();
                    Log.v(TAG,infosProfil);

                    try {

                        JSONObject Jobject = new JSONObject(infosProfil);
                        JSONArray Jarray = Jobject.getJSONArray("Fiches");

                        for (int i = 0; i < Jarray.length(); i++)
                        {
                            JSONObject object     = Jarray.getJSONObject(i);
                            date = object.getString("date");
                            id_voiture= object.getString("id_voiture");
                            id_etablissement = object.getString("id_etablissement");
                            id_utilisateur = object.getString("id_utilisateur");
                            idjson = object.getString("id");
                            IdDetailEntretien = object.getString("IdDetailEntretien");
                            Log.v("1",date);
                            Log.v("2",id_voiture);
                            Log.v("3",id_etablissement);
                            Log.v("4",id_utilisateur);
                            Log.v("5",idjson);
                            Log.v("6",IdDetailEntretien);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (InterruptedException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    }



