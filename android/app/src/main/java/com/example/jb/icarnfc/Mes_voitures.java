package com.example.jb.icarnfc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jb.icarnfc.Adapter.VoitureAdapter;
import com.example.jb.icarnfc.Object.Voiture;
import com.example.jb.icarnfc.Requests.RequestListCarUser;
import com.example.jb.icarnfc.common.GlobalVars;
import com.example.jb.icarnfc.common.UserSessionManager;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.jb.icarnfc.R.id.avatar;
import static com.example.jb.icarnfc.R.id.login;
import static com.example.jb.icarnfc.R.id.nocar;

public class Mes_voitures extends GlobalVars {

    ListView mListView;
    String nom, modele, immatriculation, urlimage, DateImmat, id_proprietaire, marque, mailparticulier, photo, idjson,email,id;
    int cv;
    ImageView photovoiture;
    List<Voiture> voitures;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_voitures);
        photovoiture = (ImageView) findViewById(avatar);
        message = (TextView) findViewById(nocar);
        mListView = (ListView) findViewById(R.id.listView);
        ImageView poubelle = (ImageView) findViewById(R.id.poubelle);


        //Récupération des variables session
        SharedPreferences settings = getSharedPreferences(MY_PREFS_NAME, 0);
        email = settings.getString("UserMail", "Null");
        id = settings.getString("idUser", "Null");
        String id_entreprise = settings.getString("id_entreprise", "Null");
        String nom = settings.getString("nom", "Null");
        String prenom = settings.getString("prenom", "Null");
        String status = settings.getString("status", "Null");

        Log.v("emailsession", email);
        Log.v("id_user", id);
        Log.v("id_entreprise", id_entreprise);
        Log.v("nom", nom);
        Log.v("prenom", prenom);
        Log.v("status", status);

        Button button = (Button) findViewById(R.id.addcar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(getBaseContext(), ReadTag.class);
                startActivity(myIntent);
                finish();

            }
        });


        //mailparticulier = (String) getIntent().getSerializableExtra("mailparticulier");


        try {
            voitures = GenererVoiture();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();


        try {
            voitures = GenererVoiture();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void afficherListeVoitures() throws InterruptedException, NoSuchAlgorithmException, IOException {

        VoitureAdapter adapter = new VoitureAdapter(Mes_voitures.this, voitures, this);
        mListView.setAdapter(adapter);
    }


    private List<Voiture> GenererVoiture() throws InterruptedException, NoSuchAlgorithmException, IOException

    {

        final List<Voiture> voituretest = new ArrayList<Voiture>();
        RequestListCarUser listuser = new RequestListCarUser();

        //Log.v("emailsession",emailsession);

        listuser.getCarsUser(id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String infosProfil = response.body().string();
                Log.v("prout",infosProfil);


                if (infosProfil.equals("{}")) {
                    Log.v("nbvoiture", "Pas de voiture");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            message.setVisibility(View.VISIBLE);
                        }
                    });


                } else {

                    Log.v("InfosProfil", infosProfil);

                    try {

                        JSONObject Jobject = new JSONObject(infosProfil);
                        JSONArray Jarray = Jobject.getJSONArray("Cars");

                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject object = Jarray.getJSONObject(i);
                            nom = object.getString("nom");
                            cv = object.getInt("CV");
                            modele = object.getString("modele");
                            marque = object.getString("marque");
                            immatriculation = object.getString("Immatriculation");
                            urlimage = object.getString("Blob");
                            idjson = object.getString("id");
                            DateImmat = object.getString("DateImmat");
                            id_proprietaire = object.getString("id_proprietaire");
                            photo = object.getString("Blob");


                            System.out.println("----------------------------");
                            Log.v(getClass().getName(), String.format("cv = %d", cv));
                            Log.v("idjson", idjson);
                            Log.v("Nom : ", nom);
                            Log.v("Modele :", modele);
                            Log.v("Immatriculation: ", immatriculation);
                            Log.v("blob: ", urlimage);
                            Log.v("DateImmat :", DateImmat);
                            Log.v("id_proprietaire : ", id_proprietaire);

                            // Variable session

                            voituretest.add(new Voiture(i, nom, immatriculation, modele, marque, DateImmat, photo, cv, idjson));


                            //Log.v("Bitmap",s);

                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    afficherListeVoitures();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (NoSuchAlgorithmException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        return voituretest;
    }

}






