package com.example.jb.icarnfc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jb.icarnfc.R;
import com.example.jb.icarnfc.Requests.RequestInfoCar;
import com.example.jb.icarnfc.Requests.RequestScanPro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ViewCarEntretien extends AppCompatActivity {

    String DateImmat, photo, id_proprietaire, nom, cv, idjson,guid;
    ImageView imageview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_car_entretien);

        Bundle extras = getIntent().getExtras();
        guid= extras.getString("guid");

        //Log.v("guid",guid);



        ViewCarEntretien.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                getinfosCar(guid);

            }
        });


    }


    private void getinfosCar(String guid) {
        try {

            RequestScanPro scanproresult = new RequestScanPro();
            scanproresult.GetCarGuid(guid, new Callback() {
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
                        JSONArray Jarray = Jobject.getJSONArray("Car");

                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject object = Jarray.getJSONObject(i);


                            final String modelejson = object.getString("modele").toString();
                            final String marquejson = object.getString("marque");
                            toString();
                            final String immatriculationjson = object.getString("Immatriculation").toString();
                            photo = object.getString("photo");


                            if (photo.matches("")) {
                                Log.v("Photo", "Pas de photo");
                            } else {

                                ViewCarEntretien.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        TextView marque, modele, immatriculation;


                                        imageview = (ImageView) findViewById(R.id.avatar);
                                        marque = (TextView) findViewById(R.id.tv_marque);
                                        modele = (TextView) findViewById(R.id.tv_modele);
                                        immatriculation = (TextView) findViewById(R.id.tv_immatriculation);

                                        marque.setText(marquejson);
                                        modele.setText(modelejson);
                                        immatriculation.setText(immatriculationjson);

                                        String[] separated = photo.split(","); // on recupere tout les donnes apres ces caractères.
                                        final String s = separated[1];
                                        Log.v("coup", s);
                                        byte[] decodedString = Base64.decode(s, Base64.DEFAULT);
                                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                        imageview.setImageBitmap(decodedByte);

                                    }

                                });

                            }

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
