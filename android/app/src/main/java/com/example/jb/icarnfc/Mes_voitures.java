package com.example.jb.icarnfc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jb.icarnfc.Requests.RequestListCar;
import com.example.jb.icarnfc.common.GlobalVars;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Mes_voitures extends GlobalVars {

    ListView mListView;
    EditText txtString;
    private static Response response;
    private static final String TAG = "TEST";
    String mailparticuler ;
    String nom,modele,immatriculation,urlimage,DateImmat,id_proprietaire,marque,cvfinal;

    int cv,id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_voitures);
        mListView = (ListView) findViewById(R.id.listView);
        ImageView poubelle = (ImageView) findViewById(R.id.poubelle);
        String mailparticuler = (String) getIntent().getSerializableExtra("mailparticulier");
        //getCarUser();


        try {
            afficherListeVoitures();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                //Intent myIntent = new Intent(getBaseContext(), Infos_car.class);
                //startActivity(myIntent);

                //Toast.makeText(Mes_voitures.this, "VOus avez cliquer ", Toast.LENGTH_LONG).show();

                String selectedFromList = (mListView.getItemAtPosition(position).toString());
                Toast.makeText(Mes_voitures.this, selectedFromList, Toast.LENGTH_LONG).show();


            }
        });


    }


  private void afficherListeVoitures() throws InterruptedException, NoSuchAlgorithmException, IOException {


        List<Voiture> voitures = GenererVoiture();
        VoitureAdapter adapter = new VoitureAdapter(Mes_voitures.this, voitures);
        mListView.setAdapter(adapter);
    }




/*    private List<Voiture> genererVoitures() {

        List<Voiture> voituretest = new ArrayList<Voiture>();
        voituretest.add(new Voiture(1, "Porsche Panamera", "789 LD 83", "Porsche", marque, dateImmat, urlimage, 430, id));
        voituretest.add(new Voiture(2, "Porsche Turbo S", "859 LD 83", "Porsche", marque, dateImmat, urlimage, 246, id));
        voituretest.add(new Voiture(3, "Porsche Boxster S", "7454 LD 83", "Porsche", marque, dateImmat, urlimage, 125, id));
        return voituretest;
    }*/


    private List <Voiture> GenererVoiture() throws InterruptedException, NoSuchAlgorithmException, IOException

    {
        final List<Voiture> voituretest = new ArrayList<Voiture>();
        RequestListCar test = new RequestListCar();
        test.getCarsUser(mailparticuler, new Callback() {
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
                    JSONArray Jarray = Jobject.getJSONArray("Cars");

                    for (int i = 0; i < Jarray.length(); i++)
                    {
                        JSONObject object     = Jarray.getJSONObject(i);
                        nom=object.getString("nom");
                        cv=object.getInt("CV");
                        modele = object.getString("modele");
                        marque= object.getString("marque");
                        immatriculation= object.getString("Immatriculation");
                        urlimage = object.getString("urlimage");
                        id = object.getInt("id");
                        DateImmat = object.getString("DateImmat");
                        id_proprietaire = object.getString("id_proprietaire");


                        Log.v(getClass().getName(), String.format("value = %d", cv));
                        Log.v(getClass().getName(), String.format("value = %d", id));
                        Log.v("1",nom);
                        Log.v("2",modele);
                        Log.v("3",immatriculation);
                        Log.v("4",urlimage);
                        Log.v("6",DateImmat);
                        Log.v("7",id_proprietaire);
                        voituretest.add(new Voiture(i,nom,immatriculation,modele, marque, DateImmat, urlimage,cv, id));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
       return voituretest;
    }

// FOnction fonctionnelle qui recupere le json des voitures dans la db suuivant l'utilisateur
   /* private void getCarUser() {
        try {
            RequestListCar listcar = new RequestListCar();
            listcar.getCarsUser(mailparticuler,new Callback() {
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
                        JSONArray Jarray = Jobject.getJSONArray("Cars");

                        for (int i = 0; i < Jarray.length(); i++)
                        {
                            JSONObject object     = Jarray.getJSONObject(i);
                            cv=object.getString("CV");
                            modele = object.getString("modele");
                            immatriculation= object.getString("Immatriculation");
                            urlimage = object.getString("urlimage");
                            id = object.getString("id");
                            DateImmat = object.getString("DateImmat");
                            id_proprietaire = object.getString("id_proprietaire");

                            Log.v("1",cv);
                            Log.v("2",modele);
                            Log.v("3",immatriculation);
                            Log.v("4",urlimage);
                            Log.v("5",id);
                            Log.v("6",DateImmat);
                            Log.v("7",id_proprietaire);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (InterruptedException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }*/


}






