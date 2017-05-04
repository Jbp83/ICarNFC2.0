package com.example.jb.icarnfc;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import com.example.jb.icarnfc.common.GlobalVars;
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
import com.example.jb.icarnfc.Requests.RequestCarsUser;

public class Mes_voitures extends GlobalVars {

    ListView mListView;
    EditText txtString;
    private static Response response;
    private static final String MAIN_URL = "http://pratikbutani.x10.mx/json_data.json";
    private static final String TAG = "TEST";
    private static final String mailparticuler = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_voitures);

        mListView = (ListView) findViewById(R.id.listView);
        String mailparticuler = (String) getIntent().getSerializableExtra("mailparticulier");
        afficherListeVoitures();

        ImageView poubelle = (ImageView) findViewById(R.id.poubelle);

    }


    private void afficherListeVoitures() {


        List<Voiture> voitures = genererVoitures(mailparticuler);

        VoitureAdapter adapter = new VoitureAdapter(Mes_voitures.this, voitures);
        mListView.setAdapter(adapter);
    }


    private List<Voiture> genererVoitures(String mail) {

        
        List<Voiture> voituretest = new ArrayList<Voiture>();
        voituretest.add(new Voiture(1, "Porsche Panamera", "789 LD 83", "Porsche",430));
        voituretest.add(new Voiture(2, "Porsche Turbo S", "859 LD 83", "Porsche",246));
        voituretest.add(new Voiture(3, "Porsche Boxster S", "7454 LD 83", "Porsche",125));

        return voituretest;
    }




}






