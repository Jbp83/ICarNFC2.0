package com.example.jb.icarnfc;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.jb.icarnfc.Requests.RequestListCarDb;
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
import java.util.HashMap;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static android.R.layout.simple_spinner_item;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AddFicheEntretien extends GlobalVars {

    UserSessionManager session;

    String IdVoiture,Marque,Modele;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fiche_entretien);

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


        AddFicheEntretien.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    RecuperationVoitureDb();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void RecuperationVoitureDb() throws IOException {
        try {
            RequestListCarDb listcardb = new RequestListCarDb();
            listcardb.getCarsDb(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String ReponseVoiture = response.body().string();

                   // Log.v("StringVoiture", ReponseVoiture);


                    try {

                        JSONObject Jobject = new JSONObject(ReponseVoiture);
                        JSONArray Jarray = Jobject.getJSONArray("Cars");

                        spinner = (Spinner) findViewById(R.id.spinnervoiture);
                        List<String> list = new ArrayList<String>();

                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject object = Jarray.getJSONObject(i);
                            IdVoiture = object.getString("id");


                            int idcar = new Integer(IdVoiture);
                            Marque = object.getString("Marque");
                            Modele = object.getString("Modele");


                            /*Log.v("id : ", IdVoiture);
                            Log.v("Marque :", Marque);
                            Log.v("Modele :", Modele);*/

                            list.add(Marque+" "+Modele);
                           // spinner.setId(idcar);

                        }

                        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddFicheEntretien.this, simple_spinner_item, list);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                        AddFicheEntretien.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {
                                spinner.setAdapter(dataAdapter); // Ajout d'un thread pour l'insertion dans le spinner
                            }

                            });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
        } catch (InterruptedException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    private void InsererFicheEntretien() throws IOException{

        /*Spinner spinner = (Spinner) findViewById(R.id.spinnervoiture);
        String voitureentretien = spinner.getSelectedItem().toString();

        TextView result = (TextView)findViewById(R.id.result);
        result.setText(voitureentretien);*/


        // Recuperer tous les champs dans la fonction directemenrt



        EditText description = (EditText) findViewById(R.id.decription);

        Date actuelle = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dat = dateFormat.format(actuelle);

        //System.out.println("Date"+dat);
        String descriptiontxt = description.getText().toString();
        //Log.v("Description",descriptiontxt);




        // todo Voiture à recuperer avec le spinner


        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("description", descriptiontxt);

        int idvoitureentretien = 1;
        int idetablissement =2;
        int idutilisateur=3;
        int id_mecanicien=34;
        session = new UserSessionManager(getApplicationContext());


        // On verifie si l'utilisateur est logué ou non
       /* if(session.checkLogin())
            finish();*/



        HashMap<String, String> user = session.getUserDetails(); // Récuperation des variables sessions


        String idsession = user.get(UserSessionManager.KEY_ID);
        String email = user.get(UserSessionManager.KEY_EMAIL);



        /*formBuilder.add("date_creation", dat);
        formBuilder.add("id_voiture", id);
        formBuilder.add("id_etablissement", id);
        formBuilder.add("id_utilisateur",id);
        formBuilder.add("id_mecanicien", idsession);*/


        RequestBody formBody = formBuilder.build();

        Request request = new Request.Builder()
                .url(IPSERVEUR+"/addEntretien")
                .post(formBody)
                .build();

        OkHttpClient client = new OkHttpClient();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                call.cancel();


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();


                AddFicheEntretien.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (myResponse.equals("entretien créé")) {

                            Toast.makeText(AddFicheEntretien.this, "Votre fiche d'entretien à bien été crée", Toast.LENGTH_LONG).show();
                           Intent myIntent = new Intent(getBaseContext(), HistoriqueEntretienPro.class);
                            startActivity(myIntent);
                        }


                        if (myResponse.equals("erreur de création")) {

                            Toast.makeText(AddFicheEntretien.this, "Problème lors de l'insertion de la fiche d'entretien", Toast.LENGTH_LONG).show();
                            //Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                            //startActivity(myIntent);
                        }


                    }
                });

            }
        });


    }


}
