package com.example.jb.icarnfc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jb.icarnfc.Requests.RequestListCar;
import com.example.jb.icarnfc.common.Base64Convertor;
import com.example.jb.icarnfc.common.GlobalVars;
import com.example.jb.icarnfc.common.UserSessionManager;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Mes_voitures extends GlobalVars {

    ListView mListView;
    String nom,modele,immatriculation,urlimage,DateImmat,id_proprietaire,marque,mailparticulier,emailsession,idsession,photo;
    UserSessionManager session;
    //ImageView photovoiture;


    int cv,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_voitures);
        //setContentView(R.layout.row_car);
        //ImageView photovoiture = (ImageView) findViewById(R.id.avatar);


        session = new UserSessionManager(getApplicationContext());


        Button button= (Button) findViewById(R.id.addcar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(getBaseContext(), ReadTag.class);
                startActivity(myIntent);
                finish();

            }
        });
        mListView = (ListView) findViewById(R.id.listView);

        ImageView poubelle = (ImageView) findViewById(R.id.poubelle);



        String mailparticulier = (String) getIntent().getSerializableExtra("mailparticulier");


        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // get id User
        idsession = user.get(UserSessionManager.KEY_ID);

        // get email
        emailsession = user.get(UserSessionManager.KEY_EMAIL);


        try {
            afficherListeVoitures();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                Intent myIntent = new Intent(getBaseContext(), Infos_car.class);
                myIntent.putExtra("id", id);
                startActivity(myIntent);


                /*String selectedFromList = (mListView.getItemAtPosition(position).toString());
                Toast.makeText(Mes_voitures.this, selectedFromList, Toast.LENGTH_LONG).show();*/
            }
        });


    }

    public void DeleteCar(View v)
    {

        Toast.makeText(v.getContext(), "The favorite list would appear on clicking this icon", Toast.LENGTH_LONG).show();
    }



  private void afficherListeVoitures() throws InterruptedException, NoSuchAlgorithmException, IOException {


        List<Voiture> voitures = GenererVoiture();
        VoitureAdapter adapter = new VoitureAdapter(Mes_voitures.this, voitures);
        mListView.setAdapter(adapter);


    }



    private List <Voiture> GenererVoiture() throws InterruptedException, NoSuchAlgorithmException, IOException

    {



        final List<Voiture> voituretest = new ArrayList<Voiture>();
        RequestListCar test = new RequestListCar();
        test.getCarsUser(emailsession, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String infosProfil = response.body().string();
                Log.v("InfosProfil",infosProfil);

                try {

                    JSONObject Jobject = new JSONObject(infosProfil);
                    JSONArray Jarray = Jobject.getJSONArray("Cars");

                    for (int i = 0; i < Jarray.length(); i++)
                    {
                        JSONObject object     = Jarray.getJSONObject(i);
                        nom=object.getString("nom");
                        cv=object.getInt("CV");
                        modele = object.getString("modele");
                        marque= object.getString("marque");
                        immatriculation= object.getString("Immatriculation");
                        urlimage = object.getString("Blob");
                        id = object.getInt("id");
                        DateImmat = object.getString("DateImmat");
                        id_proprietaire = object.getString("id_proprietaire");
                        photo=object.getString("Blob");


                        /*String[] separated = photo.split(",");
                        final String s = separated[1];// this will contain "Fruit"

                        Log.v("coup",s);

                        byte[] decodedString = Base64.decode(s, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        Log.v("Bitmap",decodedByte);


                        avatar.setImageBitmap(decodedByte);*/


                        Log.v(getClass().getName(), String.format("value = %d", cv));
                        Log.v(getClass().getName(), String.format("value = %d", id));
                        Log.v("Nom : ",nom);
                        Log.v("Modele :",modele);
                        Log.v("Immatriculation: ",immatriculation);
                        Log.v("blob: ",urlimage);
                        Log.v("DateImmat :",DateImmat);
                        Log.v("id_proprietaire : ",id_proprietaire);
                        voituretest.add(new Voiture(i,nom,immatriculation,modele,marque,DateImmat,urlimage,cv,id));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
       return voituretest;
    }



}






