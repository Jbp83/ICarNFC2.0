package com.example.jb.icarnfc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        Button button = (Button) findViewById(R.id.butonsignup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Inscription();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        //Récupération du Spinner déclaré dans le fichier main.xml de res/layout
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        //Création d'une liste d'élément à mettre dans le Spinner(pour l'exemple)
        List exempleList = new ArrayList();
        exempleList.add("Particulier");
        exempleList.add("Professionnel");


        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                exempleList
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        spinner.setAdapter(adapter);
    }


    void Inscription() throws IOException {


// déclare l'édit text, que l'on chercher à partir de son id
        EditText login = (EditText) findViewById(R.id.login);
        EditText password = (EditText) findViewById(R.id.password);
        EditText confirmpassword = (EditText) findViewById(R.id.password2);
        EditText email = (EditText) findViewById(R.id.email);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

// Récupére le text présent dans l'edit text
        String logintxt = login.getText().toString();
        String passwordtxt = password.getText().toString();
        String passwordtxt2 = confirmpassword.getText().toString();
        String emailtxt = email.getText().toString();


        String status;
        status = spinner.getSelectedItem().toString();

        Log.i("email",email.toString());

        if (passwordtxt.matches("") || passwordtxt2.matches("") || logintxt.matches("") || emailtxt.matches("")) {


            Toast toast = Toast.makeText(SignUp.this, "Tous les champs ne sont pas remplis !", Toast.LENGTH_LONG);
            LinearLayout layout = (LinearLayout) toast.getView();
            if (layout.getChildCount() > 0) {
                TextView tv = (TextView) layout.getChildAt(0);
                tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            }
            toast.show();


        } else if (passwordtxt.equals(passwordtxt2))


        {
            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("UserLogin", logintxt);


            // dynamically add more parameter like this:
            formBuilder.add("UserMail", emailtxt);
            formBuilder.add("UserPassword", passwordtxt);
            formBuilder.add("UserStatut", status);


            RequestBody formBody = formBuilder.build();

            Request request = new Request.Builder()
                    .url("http://127.0.0.1/subscribe")
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


                    SignUp.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (myResponse.equals("utilisateur créé")) {

                                Toast.makeText(SignUp.this, "Utilisateur bien crée", Toast.LENGTH_LONG).show();

                                Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(myIntent);


                            }


                            if (myResponse.equals("erreur de création")) {

                                Toast toast = Toast.makeText(SignUp.this, "Erreur de création de l'utilisateur", Toast.LENGTH_LONG);
                                LinearLayout layout = (LinearLayout) toast.getView();
                                if (layout.getChildCount() > 0) {
                                    TextView tv = (TextView) layout.getChildAt(0);
                                    tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                }
                                toast.show();
                            }

                            if (myResponse.equals("User Already exist")) {

                                Toast toast = Toast.makeText(SignUp.this, "Il existe deja un utilisateur avec ce login  !", Toast.LENGTH_LONG);
                                LinearLayout layout = (LinearLayout) toast.getView();
                                if (layout.getChildCount() > 0) {
                                    TextView tv = (TextView) layout.getChildAt(0);
                                    tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                }
                                toast.show();
                            }


                            // txtString.setText(myResponse);
                        }
                    });

                }
            });


        } else {
            Toast toast = Toast.makeText(SignUp.this, "Les mots de passe ne sont pas identiques", Toast.LENGTH_LONG);
            LinearLayout layout = (LinearLayout) toast.getView();
            if (layout.getChildCount() > 0) {
                TextView tv = (TextView) layout.getChildAt(0);
                tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            }
            toast.show();
        }

    }
}

