package com.example.jb.icarnfc;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Mes_voitures extends AppCompatActivity {

    ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_voitures);

        mListView = (ListView) findViewById(R.id.listView);
        afficherListeVoitures();

    }


    private List<Voiture> genererVoitures() {


        Calendar c = Calendar.getInstance();
        Date date = c.getTime();


        List<Voiture> voituretest = new ArrayList<Voiture>();
        voituretest.add(new Voiture(1, "Porsche Panamera", "789 LD 83", "Porsche",430));
        voituretest.add(new Voiture(2, "Porsche Turbo S", "859 LD 83", "Porsche",246));
        voituretest.add(new Voiture(3, "Porsche Boxster S", "7454 LD 83", "Porsche",125));

        return voituretest;
    }

    private void afficherListeVoitures() {
        List<Voiture> voitures = genererVoitures();

        VoitureAdapter adapter = new VoitureAdapter(Mes_voitures.this, voitures);
        mListView.setAdapter(adapter);
    }
}
