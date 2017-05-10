package com.example.jb.icarnfc;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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

        Date actuelle = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); // Recupération de la date pour l'ajout d'une fiche d'entretien
        String dat = dateFormat.format(actuelle);

        result = (TextView) findViewById(R.id.resultat);
        result.setText("Bonjour nous sommes le :  " +dat);



        //Création d'une liste d'élément à mettre dans le Spinner
        List exempleList = new ArrayList();
        exempleList.add("Porsche GT3RS");
        exempleList.add("Megane RS");




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

        Spinner spinner = (Spinner) findViewById(R.id.spinnervoiture);
        String voitureentretien = spinner.getSelectedItem().toString();

        TextView result = (TextView)findViewById(R.id.result);
        result.setText(voitureentretien);
    }





}
