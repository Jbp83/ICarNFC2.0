package com.example.jb.icarnfc;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.jb.icarnfc.Requests.RequestListCar;
import com.example.jb.icarnfc.Requests.RequestsHistoriquePro;
import com.example.jb.icarnfc.common.GlobalVars;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Mes_voitures extends GlobalVars {

    ListView mListView;
    EditText txtString;
    private static Response response;
    private static final String TAG = "TEST";
    String mailparticuler ;
    String modele,immatriculation,urlimage,DateImmat,id_proprietaire,id,cv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_voitures);
        mListView = (ListView) findViewById(R.id.listView);
        String mailparticuler = (String) getIntent().getSerializableExtra("mailparticulier");
        getCarUser();
        //afficherListeVoitures();
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

    private void getCarUser() {
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
    }




}






