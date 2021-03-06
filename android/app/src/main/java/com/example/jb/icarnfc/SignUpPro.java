package com.example.jb.icarnfc;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jb.icarnfc.Requests.RequestEtablissement;
import com.example.jb.icarnfc.common.GlobalVars;
import com.example.jb.icarnfc.common.MD5;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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

public class SignUpPro extends GlobalVars {

    final static int SELECT_PICTURE = 1;

    String encoded,adresse,telephone,siren,nometablissement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_pro);

        Button button = (Button) findViewById(R.id.butonsignup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SignUpPro.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Inscription();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                });


            }
        });

        SignUpPro.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                GetEtablissement();

            }

        });

        Spinner spinner = (Spinner)findViewById(R.id.entreprise);

        //Création d'une liste d'élément à mettre dans le Spinner
       List exempleList = new ArrayList();
        exempleList.add("test");
        exempleList.add("123");



        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, exempleList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        spinner.setAdapter(adapter);

    }



    private void GetEtablissement(){
        try {
            RequestEtablissement etablissement = new RequestEtablissement();
            etablissement.getEtablissement(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String etablissementjson = response.body().string();

                    Log.v("id", etablissementjson);


                    try {

                        JSONObject Jobject = new JSONObject(etablissementjson);
                        JSONArray Jarray = Jobject.getJSONArray("Entreprise");

                        for (int i = 0; i < Jarray.length(); i++)
                        {
                            JSONObject object     = Jarray.getJSONObject(i);
                            nometablissement=object.getString("Nom");
                            telephone=object.getString("Telephone");
                            siren = object.getString("Siren");
                            adresse= object.getString("Adresse");

                          /*  System.out.println("--------------------");
                            Log.v("Etablissement : ",nometablissement);
                            Log.v("Téléphone :",telephone);
                            Log.v("Siren :",siren);
                            Log.v("Adresse: ",adresse);*/

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



    void Inscription() throws IOException {



        EditText nom = (EditText) findViewById(R.id.mail);
        EditText prenom=(EditText) findViewById(R.id.prenom);
        EditText password = (EditText) findViewById(R.id.password);
        EditText confirmpassword = (EditText) findViewById(R.id.password2);
        EditText email = (EditText) findViewById(R.id.email);
        Spinner spinner = (Spinner) findViewById(R.id.entreprise);

        String nomtxt = nom.getText().toString();
        String prenomtxt=prenom.getText().toString();
        String passwordtxt = password.getText().toString();
        String passwordtxt2 = confirmpassword.getText().toString();
        String emailtxt = email.getText().toString();
        String entreprise = spinner.getSelectedItem().toString();
        Log.v("entreprise",entreprise);

        Log.i("email",email.toString());

        if (passwordtxt.matches("") || passwordtxt2.matches("") || nomtxt.matches("") || emailtxt.matches("")  || prenomtxt.matches("") ) {


            Toast toast = Toast.makeText(SignUpPro.this, "Tous les champs ne sont pas remplis !", Toast.LENGTH_LONG);
            LinearLayout layout = (LinearLayout) toast.getView();
            if (layout.getChildCount() > 0)
            {
                TextView tv = (TextView) layout.getChildAt(0);
                tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            }
            toast.show();


        } else if (passwordtxt.equals(passwordtxt2))


        {
            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("UserName", nomtxt);

            MD5 md5 = new MD5();
            String passwordmd5= md5.crypt(passwordtxt);


            // dynamically add more parameter like this:
            formBuilder.add("UserSurname",prenomtxt);
            formBuilder.add("UserMail", emailtxt);
            formBuilder.add("UserPassword", passwordmd5);
            formBuilder.add("UserStatut", "Professionnel");

            RequestBody formBody = formBuilder.build();

            Request request = new Request.Builder()
                    .url(IPSERVEUR+"/subscribe")
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


                    SignUpPro.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (myResponse.equals("utilisateur créé")) {

                                Toast.makeText(SignUpPro.this, "Utilisateur crée", Toast.LENGTH_LONG).show();
                                Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(myIntent);

                            }


                            if (myResponse.equals("erreur de création")) {

                                Toast toast = Toast.makeText(SignUpPro.this, "Erreur de création de l'utilisateur", Toast.LENGTH_LONG);
                                LinearLayout layout = (LinearLayout) toast.getView();
                                if (layout.getChildCount() > 0) {
                                    TextView tv = (TextView) layout.getChildAt(0);
                                    tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                }
                                toast.show();
                            }

                            if (myResponse.equals("User Already exist")) {

                                Toast toast = Toast.makeText(SignUpPro.this, "Il existe deja un utilisateur avec cette adresse e-mail  !", Toast.LENGTH_LONG);
                                LinearLayout layout = (LinearLayout) toast.getView();
                                if (layout.getChildCount() > 0) {
                                    TextView tv = (TextView) layout.getChildAt(0);
                                    tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                }
                                toast.show();
                            }

                        }
                    });

                }
            });


        } else {
            Toast toast = Toast.makeText(SignUpPro.this, "Les mots de passe ne sont pas identiques", Toast.LENGTH_LONG);
            LinearLayout layout = (LinearLayout) toast.getView();
            if (layout.getChildCount() > 0) {
                TextView tv = (TextView) layout.getChildAt(0);
                tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            }
            toast.show();
        }

    }





  /*  private static String md5(String s) { try {

        // Create MD5 Hash
        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
        digest.update(s.getBytes());
        byte messageDigest[] = digest.digest();

        // Create Hex String
        StringBuffer hexString = new StringBuffer();
        for (int i=0; i<messageDigest.length; i++)
            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
        return hexString.toString();

    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    }
        return "";

    }*/

}
