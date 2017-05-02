package com.example.jb.icarnfc;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jb.icarnfc.common.GlobalVars;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Mes_voitures extends GlobalVars {

    ListView mListView;
    private static Response response;
    private static final String MAIN_URL = "http://pratikbutani.x10.mx/json_data.json";
    private static final String TAG = "TEST";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_voitures);

        mListView = (ListView) findViewById(R.id.listView);

        afficherListeVoitures();




        ImageView poubelle = (ImageView) findViewById(R.id.poubelle);
        // set a onclick listener for when the button gets clicked


    /*    poubelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder
                        .setTitle("Erase hard drive")
                        .setMessage("Are you sure?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Yes button clicked, do something
                                Toast.makeText(Mes_voitures.this, "Yes button pressed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", null)	//Do nothing on no
                        .show();
            }

       });*/
    }


    private void afficherListeVoitures() {


        List<Voiture> voitures = genererVoitures();

        VoitureAdapter adapter = new VoitureAdapter(Mes_voitures.this, voitures);
        mListView.setAdapter(adapter);
    }


    private List<Voiture> genererVoitures() {


        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("UserMail", "mars8.6@hotmail.fr");

// dynamically add more parameter like this:


        RequestBody formBody = formBuilder.build();

        Request request = new Request.Builder()
                .url(IPSERVEUR+"/userCars")
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


                Mes_voitures.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String TAG="response";
                        //txtString.setText(myResponse);
                        String data=myResponse;


                    }
                });

            }
        });

        List<Voiture> voituretest = new ArrayList<Voiture>();
        voituretest.add(new Voiture(1, "Porsche Panamera", "789 LD 83", "Porsche",430));
        voituretest.add(new Voiture(2, "Porsche Turbo S", "859 LD 83", "Porsche",246));
        voituretest.add(new Voiture(3, "Porsche Boxster S", "7454 LD 83", "Porsche",125));

        return voituretest;
    }




}






