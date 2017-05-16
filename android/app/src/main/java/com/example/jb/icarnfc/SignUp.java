package com.example.jb.icarnfc;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
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
import com.example.jb.icarnfc.common.GlobalVars;
import com.example.jb.icarnfc.common.MD5;


public class SignUp extends GlobalVars {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        Button button = (Button) findViewById(R.id.butonsignup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SignUp.this.runOnUiThread(new Runnable() {
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

    }


    void Inscription() throws IOException {


    // déclare l'édit text, que l'on chercher à partir de son id
        EditText nom = (EditText) findViewById(R.id.mail);
        EditText prenom=(EditText) findViewById(R.id.prenom);
        EditText password = (EditText) findViewById(R.id.password);
        EditText confirmpassword = (EditText) findViewById(R.id.password2);
        EditText email = (EditText) findViewById(R.id.email);
        // Spinner spinner = (Spinner) findViewById(R.id.spinner);

    // Récupére le text présent dans l'edit text
        String nomtxt = nom.getText().toString();
        String prenomtxt=prenom.getText().toString();
        String passwordtxt = password.getText().toString();
        String passwordtxt2 = confirmpassword.getText().toString();
        String emailtxt = email.getText().toString();
        //String status = spinner.getSelectedItem().toString();

        Log.i("email",email.toString());

        if (passwordtxt.matches("") || passwordtxt2.matches("") || nomtxt.matches("") || emailtxt.matches("")  || prenomtxt.matches("") ) {


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
                    .add("UserName", nomtxt);


            MD5 md5 = new MD5();
            String passwordmd5= md5.crypt(passwordtxt);

            Log.v("md5",passwordmd5);


            // dynamically add more parameter like this:
            formBuilder.add("UserSurname",prenomtxt);
            formBuilder.add("UserMail", emailtxt);
            formBuilder.add("UserPassword", passwordmd5);
            formBuilder.add("UserStatut", "Particulier");



            final RequestBody formBody = formBuilder.build();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
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


                            SignUp.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (myResponse.equals("utilisateur créé")) {

                                        Toast.makeText(SignUp.this, "Utilisateur crée", Toast.LENGTH_LONG).show();
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

                                        Toast toast = Toast.makeText(SignUp.this, "Il existe deja un utilisateur avec cette adresse e-mail  !", Toast.LENGTH_LONG);
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


/*    private static String md5(String s) { try {

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

