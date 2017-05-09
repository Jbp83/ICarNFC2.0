package com.example.jb.icarnfc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.HashMap;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.example.jb.icarnfc.common.GlobalVars;
import com.example.jb.icarnfc.common.UserSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends GlobalVars {



    private static final String TAG = "Password md5" ;
    TextView txtString;
    UserSessionManager session;
    String status,id;




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // User Session Manager
        session = new UserSessionManager(getApplicationContext());


        // Check user login (this is the important point)
        // If User is not logged in , This will redirect user to LoginActivity
        // and finish current activity from activity stack.
        /*if(session.checkLogin())
            finish();*/

        // get user data from session
        HashMap< String, String> user = session.getUserDetails();

        // get id User
        String id = user.get(UserSessionManager.KEY_ID);

        // get email
        String email = user.get(UserSessionManager.KEY_EMAIL);



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
            String pwdmd5=md5.crypt(passwordtxt);

            Log.v(TAG,pwdmd5);

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


                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                       /*     if (myResponse.equals("Professionnel")) {

                                     Intent myIntent = new Intent(getBaseContext(), Pro.class);
                                     myIntent.putExtra("mailpro",mailtxt);
                                     startActivity(myIntent);

                                     session.createUserLoginSession("1",mailtxt);
                                     finish();
                            }


                            if (myResponse.equals("Particulier")) {

                                Intent myIntent = new Intent(getBaseContext(), Mes_voitures.class);
                                myIntent.putExtra("mailparticulier",mailtxt);
                                startActivity(myIntent);
                                session.createUserLoginSession("1",mailtxt);
                                finish();
                            }*/



                            try {

                                JSONObject Jobject = new JSONObject(myResponse);
                                JSONArray Jarray = Jobject.getJSONArray("User");

                                for (int i = 0; i < Jarray.length(); i++)
                                {
                                    JSONObject object     = Jarray.getJSONObject(i);
                                    id=object.getString("id");
                                    status=object.getString("status");

                                    Log.v("id", id);
                                    Log.v("status",status);


                                }


                                if(status.equals("Particulier"))
                                {
                                    Intent myIntent = new Intent(getBaseContext(), Mes_voitures.class);
                                    myIntent.putExtra("mailparticulier",mailtxt);
                                    session.createUserLoginSession(id,mailtxt);
                                    startActivity(myIntent);
                                    finish();
                                }

                                if(status.equals("Professionnel"))
                                {
                                    Intent myIntent = new Intent(getBaseContext(), Pro.class);
                                    myIntent.putExtra("mailpro",mailtxt);
                                    session.createUserLoginSession(id,mailtxt);
                                    startActivity(myIntent);
                                    finish();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (myResponse.equals("fail to login")) {

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