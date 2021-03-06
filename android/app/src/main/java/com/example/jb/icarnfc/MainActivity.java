package com.example.jb.icarnfc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.example.jb.icarnfc.common.GlobalVars;
import com.example.jb.icarnfc.common.MD5;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends GlobalVars {


    TextView txtString;
    String status,idrequest,identreprise,nom,prenom,email;
    SharedPreferences Editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(MY_PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("silentMode", "coucou");

        // Commit the edits!
        editor.commit();

        Button button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Authentification();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        txtString= (TextView)findViewById(R.id.mail);

        TextView textview =(TextView) findViewById(R.id.inscription);
        textview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                try {
                    SignUp();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            });
    }


    void SignUp() throws IOException
    {
        Intent myIntent = new Intent(getBaseContext(), SelectStatus.class);
        startActivity(myIntent);
    }



    void Authentification() throws IOException {

// déclare l'édit text, que l'on chercher à partir de son id
        EditText mail = (EditText) findViewById (R.id.email);
        EditText password = (EditText) findViewById (R.id.password);

// Récupére le text présent dans l'edit text
        final String mailtxt = mail.getText().toString();
        final String passwordtxt = password.getText().toString();


        if(mailtxt.matches("") || passwordtxt.matches(""))
        {
           Toast.makeText(MainActivity.this, "Les champs ne sont pas tous remplis", Toast.LENGTH_LONG).show();

        } else

        {
            MD5 md5 = new MD5();
            String pwdmd5= md5.crypt(passwordtxt);

            Log.v("md5crypt",pwdmd5);

            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("UserMail", mailtxt);

// dynamically add more parameter like this:
            formBuilder.add("UserPassword", pwdmd5);

            RequestBody formBody = formBuilder.build();

            Request request = new Request.Builder()
                    .url(IPSERVEUR+"/login")
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
                    Log.v("json", myResponse);


                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            try {

                                JSONObject Jobject = new JSONObject(myResponse);
                                JSONArray Jarray = Jobject.getJSONArray("User");

                                for (int i = 0; i < Jarray.length(); i++)
                                {
                                    JSONObject object     = Jarray.getJSONObject(i);
                                    idrequest=object.getString("id");
                                    status=object.getString("status");
                                    identreprise=object.getString("identreprise");
                                    nom=object.getString("nom");
                                    prenom=object.getString("prenom");
                                    email=object.getString("mail");

                                    Log.v("id", idrequest);
                                    Log.v("status",status);
                                }


                                if(status.equals("Particulier"))
                                {
                                    Intent myIntent = new Intent(getBaseContext(), Mes_voitures.class);
                                    myIntent.putExtra("mailparticulier",mailtxt);



                                    //Variable session

                                    SharedPreferences settings = getSharedPreferences(MY_PREFS_NAME, 0);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString("UserMail", mailtxt);
                                    editor.putString("idUser", idrequest);
                                    editor.putString("id_entreprise", identreprise);
                                    editor.putString("nom", nom);
                                    editor.putString("prenom", prenom);
                                    editor.putString("status", status);


                                    editor.commit();
                                    startActivity(myIntent);
                                    //finish();

                                }

                                if(status.equals("Professionnel"))
                                {
                                    Intent myIntent = new Intent(getBaseContext(), Pro.class);
                                    myIntent.putExtra("mailpro",mailtxt);

                                    SharedPreferences settings = getSharedPreferences(MY_PREFS_NAME, 0);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString("UserMail", mailtxt);
                                    editor.putString("idUser", idrequest);
                                    editor.putString("id_entreprise", identreprise);
                                    editor.putString("nom", nom);
                                    editor.putString("prenom", prenom);
                                    editor.putString("status", status);
                                    editor.commit();
                                    startActivity(myIntent);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (myResponse.equals("error user not found")) {

                                //alert.showAlertDialog(LoginActivity.this, "Login failed..", "Please enter username and password", false);

                                Toast toast = Toast.makeText(MainActivity.this, "Nom d'utilisateur ou mot de passe incorrect !", Toast.LENGTH_LONG);
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

        }



    }

}