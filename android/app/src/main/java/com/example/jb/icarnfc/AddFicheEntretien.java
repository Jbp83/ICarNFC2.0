package com.example.jb.icarnfc;



import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jb.icarnfc.Requests.RequestEtablissement;
import com.example.jb.icarnfc.Requests.RequestInfoCar;
import com.example.jb.icarnfc.Requests.RequestListCar;
import com.example.jb.icarnfc.Requests.RequestListCarDb;
import com.example.jb.icarnfc.common.GlobalVars;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AddFicheEntretien extends GlobalVars {

    Spinner spinner;
    String IdVoiture,Marque,Modele;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fiche_entretien);

        //Création d'une liste d'élément à mettre dans le Spinner
        List exempleList = new ArrayList();
        exempleList.add("Porsche GT3RS");
        exempleList.add("Megane RS");


        // Il faut recuperer toute les voitures afin de la selectionner lors de l'ajout


        Button button= (Button) findViewById(R.id.addentretien);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    InsererFicheEntretien();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        AddFicheEntretien.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    RecuperationVoitureDb();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


    }


    private void RecuperationVoitureDb() throws IOException {
        try {
            RequestListCarDb listcardb = new RequestListCarDb();
            listcardb.getCarsDb(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String ReponseVoiture = response.body().string();

                    Log.v("id", ReponseVoiture);


                    try {

                        JSONObject Jobject = new JSONObject(ReponseVoiture);
                        JSONArray Jarray = Jobject.getJSONArray("Entreprise");

                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject object = Jarray.getJSONObject(i);
                            IdVoiture = object.getString("id");
                            Marque = object.getString("Marque");
                            Modele = object.getString("Modele");


                            Log.v("id : ", IdVoiture);
                            Log.v("Marque :", Marque);
                            Log.v("Modele :", Modele);

                            Spinner spinner = (Spinner) findViewById(R.id.spinnervoiture);
                            // remplir le spinner dans la boucle

                            // Insertion des valeurs dans le spinner pour la selection de la voiture lors de l'ajout d'un entretien

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

    private void InsererFicheEntretien() throws IOException{

        /*Spinner spinner = (Spinner) findViewById(R.id.spinnervoiture);
        String voitureentretien = spinner.getSelectedItem().toString();

        TextView result = (TextView)findViewById(R.id.result);
        result.setText(voitureentretien);*/


        // Recuperer tous les champs dans la fonction directemenrt



        EditText description = (EditText) findViewById(R.id.decription);

        Date actuelle = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dat = dateFormat.format(actuelle);

        //System.out.println("Date"+dat);
        String descriptiontxt = description.getText().toString();
        //Log.v("Description",descriptiontxt);




        // todo Voiture à recuperer avec le spinner


        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("description", descriptiontxt);



        // dynamically add more parameter like this:

        formBuilder.add("date_creation", dat);

        //todo ajouter les champs manquants


        RequestBody formBody = formBuilder.build();

        Request request = new Request.Builder()
                .url(IPSERVEUR+"/addEntretien")
                .post(formBody)
                .build();

        OkHttpClient client = new OkHttpClient();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                call.cancel();


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();


                AddFicheEntretien.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (myResponse.equals("entretien créé")) {

                            Toast.makeText(AddFicheEntretien.this, "Votre fiche d'entretien à bien été crée", Toast.LENGTH_LONG).show();
                           // Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                            //startActivity(myIntent);
                        }


                        if (myResponse.equals("erreur de création")) {

                            Toast.makeText(AddFicheEntretien.this, "Problème lors de l'insertion de la fiche d'entretien", Toast.LENGTH_LONG).show();
                            //Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                            //startActivity(myIntent);
                        }


                    }
                });

            }
        });


    }

}
