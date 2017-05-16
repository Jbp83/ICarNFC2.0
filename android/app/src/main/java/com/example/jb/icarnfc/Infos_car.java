package com.example.jb.icarnfc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jb.icarnfc.Requests.RequestInfoCar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Infos_car extends AppCompatActivity {

    String nom,modele,immatriculation,DateImmat,id_proprietaire,marque,photo,cv,idjson;
    ImageView imageView;
    TextView puissanceEdit ;
    TextView dateimmatEdit ;
    TextView marqueEdit;
    TextView modeleEdit ;
    TextView immatriculationEdit ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        //final String iddefault ="1";
        final String iddefault = (String) getIntent().getSerializableExtra("idvoitureselect");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos_car);

        //Log.v("tube",iddefault);

        Intent intent = getIntent();
        final String idea = intent.getStringExtra("id");


        imageView = (ImageView) findViewById(R.id.photo);
        puissanceEdit= (TextView) findViewById(R.id.puissance);
        dateimmatEdit = (TextView) findViewById(R.id.dateimmat);
        marqueEdit =(TextView) findViewById(R.id.tv_marque);
        modeleEdit= (TextView) findViewById(R.id.tv_modele);
        immatriculationEdit = (TextView) findViewById(R.id.tv_immatriculation);

        Infos_car.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GetInfoCar(iddefault);
            }

        });

    }

    private void GetInfoCar(String id) {
        try {


            RequestInfoCar infocar = new RequestInfoCar();
            infocar.getInfosCar(id, new Callback() {
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
                        JSONArray Jarray = Jobject.getJSONArray("Voiture");

                        for (int i = 0; i < Jarray.length(); i++)
                        {
                            JSONObject object     = Jarray.getJSONObject(i);
                            nom=object.getString("nom").toString();
                            cv=object.getString("CV").toString();
                            modele = object.getString("modele").toString();
                            marque= object.getString("marque");toString();
                            immatriculation= object.getString("Immatriculation").toString();
                            idjson = object.getString("id").toString();
                            DateImmat = object.getString("DateImmat").toString();
                            id_proprietaire = object.getString("id_proprietaire").toString();
                            photo=object.getString("Photo");


                            if(photo.matches(""))
                            {
                                Log.v("Photo","Pas de photo");
                            }

                            else
                                {

                                    Infos_car.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                    String[] separated = photo.split(","); // on recupere tout les donnes apres ces caractères.
                                    final String s = separated[1];
                                    Log.v("coup",s);
                                    byte[] decodedString = Base64.decode(s, Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                        imageView.setImageBitmap(decodedByte);

                                    }

                                });

                            }
                            Infos_car.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    puissanceEdit.setText(nom);
                                    marqueEdit.setText(marque);
                                    modeleEdit.setText(modele);
                                    dateimmatEdit.setText(DateImmat);
                                    immatriculationEdit.setText(immatriculation);


                                }
                                });


                            Log.v("cv", cv);
                            Log.v("idjson" ,idjson);
                            Log.v("Nom : ",nom);
                            Log.v("Modele :",modele);
                            Log.v("Modele :",marque);
                            Log.v("Immatriculation: ",immatriculation);
                            Log.v("Photo: ",photo);
                            Log.v("DateImmat :",DateImmat);
                            Log.v("id_proprietaire : ",id_proprietaire);


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
