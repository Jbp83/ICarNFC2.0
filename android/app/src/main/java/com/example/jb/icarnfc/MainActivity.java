package com.example.jb.icarnfc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Password md5" ;
    TextView txtString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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
        Intent myIntent = new Intent(getBaseContext(), SignUp.class);
        startActivity(myIntent);
    }




    void Authentification() throws IOException {

// déclare l'édit text, que l'on chercher à partir de son id
        EditText mail = (EditText) findViewById (R.id.mail);
        EditText password = (EditText) findViewById (R.id.password);

// Récupére le text présent dans l'edit text
        String mailtxt = mail.getText().toString();
        String passwordtxt = password.getText().toString();



        MD5 md5 = new MD5();
        String pwdmd5=md5.crypt(passwordtxt);

        Log.v(TAG,pwdmd5);

        if(mailtxt.matches("") || passwordtxt.matches(""))
        {

           Toast.makeText(MainActivity.this, "Les champs ne sont pas tous remplis", Toast.LENGTH_LONG).show();


        } else

        {
            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("UserMail", mailtxt);

// dynamically add more parameter like this:
            formBuilder.add("UserPassword", pwdmd5);

            RequestBody formBody = formBuilder.build();

            Request request = new Request.Builder()
                    .url("http://192.168.7.187:8080/login")
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


                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (myResponse.equals("Professionnel")) {

                                Intent myIntent = new Intent(getBaseContext(), Pro.class);
                                startActivity(myIntent);
                            }


                            if (myResponse.equals("Particulier")) {

                                Intent myIntent = new Intent(getBaseContext(), Mes_voitures.class);
                                startActivity(myIntent);
                            }

                            if (myResponse.equals("fail to login")) {

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