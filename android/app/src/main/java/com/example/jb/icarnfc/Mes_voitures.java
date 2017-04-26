package com.example.jb.icarnfc;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Mes_voitures extends AppCompatActivity {

    ListView mList1View;

/*    String[] prenoms = new String[]{
            "Antoine", "Benoit", "Cyril", "David", "Eloise", "Florent",
            "Gerard", "Hugo", "Ingrid", "Jonathan", "Kevin", "Logan",
            "Mathieu", "Noemie", "Olivia", "Philippe", "Quentin", "Romain",
            "Sophie", "Tristan", "Ulric", "Vincent", "Willy", "Xavier",
            "Yann", "Zoé"
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_voitures);

       mList1View = (ListView) findViewById(R.id.listView);

       /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(Mes_voitures.this,
                android.R.layout.simple_list_item_1, prenoms);*/

        //mList1View.setAdapter(adapter);

        List<Voiture> voituretest = genererVoitures();

        VoitureAdapter adapter = new Voiture(Mes_voitures.this, voituretest);
        mList1View.setAdapter(adapter);

    }

    private List<Voiture> genererVoitures(){

      Date date=new Date();

        List<Voiture> voituretest = new ArrayList<Voiture>();
        voituretest.add(new Voiture(1,"x5","x3","x4",date,25));
        voituretest.add(new Voiture(1,"x5","x3","x4",date,25));
        voituretest.add(new Voiture(1,"x5","x3","x4",date,25));
        voituretest.add(new Voiture(1,"x5","x3","x4",date,25));
        voituretest.add(new Voiture(1,"x5","x3","x4",date,25));
        voituretest.add(new Voiture(1,"x5","x3","x4",date,25));

        return voituretest;
    }
}
