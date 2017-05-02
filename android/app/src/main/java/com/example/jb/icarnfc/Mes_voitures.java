package com.example.jb.icarnfc;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Mes_voitures extends AppCompatActivity {

    ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_voitures);

        mListView = (ListView) findViewById(R.id.listView);


        //afficherListeVoitures();

        okhttpget example = new okhttpget();
        String response = null;
        try {
            response = example.run("http://192.168.1.100:8080//userCars?UserMail=mars8.6@hotmail.fr");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response);

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

        List<Voiture> voituretest = new ArrayList<Voiture>();
        voituretest.add(new Voiture(1, "Porsche Panamera", "789 LD 83", "Porsche",430));
        voituretest.add(new Voiture(2, "Porsche Turbo S", "859 LD 83", "Porsche",246));
        voituretest.add(new Voiture(3, "Porsche Boxster S", "7454 LD 83", "Porsche",125));

        return voituretest;
    }





}






