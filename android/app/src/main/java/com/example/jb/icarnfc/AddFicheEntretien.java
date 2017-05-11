package com.example.jb.icarnfc;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.jb.icarnfc.common.GlobalVars;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AddFicheEntretien extends GlobalVars {

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fiche_entretien);


       // Affichage de la date dans un textview
        /*result = (TextView) findViewById(R.id.resultat);
        result.setText("Bonjour nous sommes le :  " +dat);*/

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


    }

    private void InsererFicheEntretien() throws IOException{

        /*Spinner spinner = (Spinner) findViewById(R.id.spinnervoiture);
        String voitureentretien = spinner.getSelectedItem().toString();

        TextView result = (TextView)findViewById(R.id.result);
        result.setText(voitureentretien);*/


        // Recuperer tous les champs dans la fonction directemenrt

        EditText description = (EditText) findViewById(R.id.decription);


        Date actuelle = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); // Recupération de la date pour l'ajout d'une fiche d'entretien
        String dat = dateFormat.format(actuelle);

        Log.v("Dateactuelle",dat);


        String descriptiontxt = description.getText().toString();

        // Voiture à recuperer avec le spinner



        /*//Methode pour envoyer un mail quand on crée une fiche d'entretien
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"mars8.6@hotmail.fr"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Bonjour");
        startActivity(Intent.createChooser(i, "Test:"));*/


    }





}
