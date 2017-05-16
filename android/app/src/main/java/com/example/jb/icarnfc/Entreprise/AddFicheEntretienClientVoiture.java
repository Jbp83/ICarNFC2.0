package com.example.jb.icarnfc.Entreprise;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.ListView;

import com.example.jb.icarnfc.Adapter.UserAdapter;
import com.example.jb.icarnfc.Adapter.UserVoitureAdapter;
import com.example.jb.icarnfc.Object.Voiture;
import com.example.jb.icarnfc.R;
import com.example.jb.icarnfc.Requests.RequestUser;
import com.example.jb.icarnfc.common.GlobalVars;
import com.example.jb.icarnfc.common.UserSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Jb on 16/05/2017.
 */

public class AddFicheEntretienClientVoiture extends GlobalVars {

         private String id;
        UserSessionManager session;
        ListView mListView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_fiche_entretien_client_voiture);
            mListView = (ListView) findViewById(R.id.CarListview);

            Intent intent = getIntent();
            id = intent.getStringExtra("idClient");


            try {
                RecuperationVoitureDb();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private void RecuperationVoitureDb() throws IOException {
            try {
                RequestUser reqUser = new RequestUser();
                reqUser.getCarbyUSer(id, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String repUser = response.body().string();

                        try {
                            final List<Voiture> Vlist = new ArrayList<Voiture>();
                            JSONObject Jobject = new JSONObject(repUser);
                            JSONArray Jarray = Jobject.getJSONArray("Cars");

                            for (int i = 0; i < Jarray.length(); i++) {
                                JSONObject object = Jarray.getJSONObject(i);
                                String nom = object.getString("nom");
                                int cv = object.getInt("CV");
                                String modele = object.getString("modele");
                                String marque = object.getString("marque");
                                String immatriculation = object.getString("Immatriculation");
                                String idjson = object.getString("id");
                                String DateImmat = object.getString("DateImmat");
                                String id_proprietaire = object.getString("id_proprietaire");
                                String photo = object.getString("Blob");

                                int idProp = Integer.parseInt(id_proprietaire);
                                Vlist.add(new Voiture(idProp, nom, immatriculation,modele,marque,DateImmat,photo,cv,idjson));

                            }

                            AddFicheEntretienClientVoiture.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run()
                                {

                                    UserVoitureAdapter adapter= new UserVoitureAdapter(AddFicheEntretienClientVoiture.this, Vlist);
                                    mListView.setAdapter(adapter);

                                }

                            });

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

