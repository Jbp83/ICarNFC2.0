package com.example.jb.icarnfc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import static android.widget.Toast.LENGTH_LONG;


public class MainActivity extends AppCompatActivity {

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
                    run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        txtString= (TextView)findViewById(R.id.txtString);

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




    void run() throws IOException {

// déclare l'édit text, que l'on chercher à partir de son id
        EditText login = (EditText) findViewById (R.id.login);
        EditText password = (EditText) findViewById (R.id.password);

// Récupére le text présent dans l'edit text
        String logintxt = login.getText().toString();
        String passwordtxt = password.getText().toString();


        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("UserLogin", logintxt);

// dynamically add more parameter like this:
        formBuilder.add("UserPassword", passwordtxt);

        RequestBody formBody = formBuilder.build();

        Request request = new Request.Builder()
                .url("http://192.168.1.15:8080/login")
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

                        if (myResponse.equals("pro")) {

                            Intent myIntent = new Intent(getBaseContext(), LoginActivity.class);
                            startActivity(myIntent);
                        }


                        if (myResponse.equals("particulier")) {

                            Intent myIntent = new Intent(getBaseContext(), MesVoitures.class);
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