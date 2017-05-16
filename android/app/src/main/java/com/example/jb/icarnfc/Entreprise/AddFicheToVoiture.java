package com.example.jb.icarnfc.Entreprise;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jb.icarnfc.Adapter.UserAdapter;
import com.example.jb.icarnfc.Adapter.UserVoitureAdapter;
import com.example.jb.icarnfc.Infos_car;
import com.example.jb.icarnfc.Object.Utilisateur;
import com.example.jb.icarnfc.Object.Voiture;
import com.example.jb.icarnfc.Pro;
import com.example.jb.icarnfc.R;
import com.example.jb.icarnfc.Requests.RequestEtablissement;
import com.example.jb.icarnfc.Requests.RequestUser;
import com.example.jb.icarnfc.common.GlobalVars;
import com.example.jb.icarnfc.common.UserSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Jb on 16/05/2017.
 */

public class AddFicheToVoiture extends GlobalVars{

        UserSessionManager session;
        ListView mListView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_fiche_voiture);

            final EditText editText =(EditText)findViewById(R.id.editText2);
            Intent intent = getIntent();
            final String idClient = intent.getStringExtra("idClient");
            ;
            final String idVoiture = intent.getStringExtra("idVoiture");

            SharedPreferences settings = getSharedPreferences(MY_PREFS_NAME, 0);
            final String id = settings.getString("idUser", "Null");
            final String id_entreprise = settings.getString("id_entreprise", "Null");


            Date actuelle = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            final String dat = dateFormat.format(actuelle);


            Button validate = (Button) findViewById(R.id.btnValid);

            validate.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {



                    String monedit = editText.getText().toString();

                    try {
                        envoieFiche(dat,idVoiture,id_entreprise,idClient,monedit,id);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
            });


        }


    private void envoieFiche(String Date,String idVoiture,String id_etab, String idUser, String description,String id_mecanicien) throws IOException, InterruptedException, NoSuchAlgorithmException {

        RequestEtablissement req = new RequestEtablissement();
        req.setFiche(Date, idVoiture, id_etab, idUser, description, id_mecanicien, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String repUser = response.body().string();

                AddFicheToVoiture.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddFicheToVoiture.this);
                        builder.setMessage("Fiche crée").show();

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent monIntent = new Intent(AddFicheToVoiture.this, AddFicheEntretienClient.class);
                                startActivity(monIntent);
                                finish();
                            }
                        }, 2000);


                    }

                    });
                }
        });
    }
}
