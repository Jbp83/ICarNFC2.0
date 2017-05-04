package com.example.jb.icarnfc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.jb.icarnfc.common.GlobalVars;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Add_car extends GlobalVars {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        Button button= (Button) findViewById(R.id.addfiche);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AddCar();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    void AddCar() throws IOException
    {
        EditText nom = (EditText) findViewById (R.id.nom);
        EditText marque = (EditText) findViewById (R.id.marque);
        EditText model = (EditText) findViewById (R.id.modele);
        EditText immat = (EditText) findViewById (R.id.immat);
        EditText cv = (EditText) findViewById (R.id.cv);

        final String nomtxt = nom.getText().toString();
        final String marquetxt = marque.getText().toString();
        final String modeltxt = model.getText().toString();
        final String immattxt = immat.getText().toString();
        final String cvtxt = cv.getText().toString();

        if(nomtxt.matches("") || marquetxt.matches("") || modeltxt.matches("") ||immattxt.matches("")  )
        {

            Toast.makeText(Add_car.this, "Les champs ne sont pas tous remplis", Toast.LENGTH_LONG).show();

        }
        else
        {

            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("GUID", "1");

            // dynamically add more parameter like this:
            formBuilder.add("UserID", "11");
            formBuilder.add("CarImmat", immattxt);
            formBuilder.add("CarName", nomtxt);
            formBuilder.add("CarBrand", marquetxt);
            formBuilder.add("CarModel", modeltxt);
            formBuilder.add("DateImmat", "2017-04-10");
            formBuilder.add("CV","1000");


            RequestBody formBody = formBuilder.build();

            Request request = new Request.Builder()
                    .url(IPSERVEUR+"/addcar")
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


                    Add_car.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (myResponse.equals("voiture crée")) {

                                Toast.makeText(Add_car.this, "Voiture enregistré avec succés", Toast.LENGTH_LONG).show();
                                Intent myIntent = new Intent(getBaseContext(), Mes_voitures.class);
                                //myIntent.putExtra("mailpro",mailtxt); // On transmet la variable à la nouvelle activité
                                startActivity(myIntent);
                            } if (myResponse.equals("erreur de création"))
                            {
                                Toast.makeText(Add_car.this, "La voiture n'a pas été enregistré", Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                }
            });

        }


    }
}
