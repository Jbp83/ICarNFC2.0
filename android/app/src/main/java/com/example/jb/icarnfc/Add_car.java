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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jb.icarnfc.common.GlobalVars;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Add_car extends GlobalVars {

    String encoded;
    final static int SELECT_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        Button button= (Button) findViewById(R.id.addfiche);
        TextView textView =(TextView)findViewById(R.id.guid);
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

        String str = (String) getIntent().getSerializableExtra("guid");
        textView.setText(str);


        Button bt_avatar = (Button)findViewById(R.id.AddPhotoCar) ;
        bt_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btGalleryClick(v);
            }
        });


    }


    public void btGalleryClick(View v) {
        //Création puis ouverture de la boite de dialogue
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, ""), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //ImageView mImageView = (ImageView) findViewById(R.id.Avatar);
        //TextView textViewstatus = (TextView) findViewById(R.id.tvStatus);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_PICTURE:
                    String path = getRealPathFromURI(data.getData());
                    Log.d("Choose Picture", path);
                    //Transformer la photo en Bitmap
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    //Afficher le Bitmap
                    //mImageView.setVisibility(View.VISIBLE);
                    //mImageView.setImageBitmap(bitmap);


                   /* Base64Convertor convertavatar = new Base64Convertor();
                    convertavatar.encodeTobase64(android.graphics.bitmap);*/

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream .toByteArray();
                    encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    Log.v("Encoded64",encoded);


                   /* //On renseigne les informations sur la photo séléctionné
                    textViewstatus.setText("");
                    textViewstatus.append("Fichier: " + path);
                    textViewstatus.append(System.getProperty("line.separator"));
                    textViewstatus.append("Taille: " + bitmap.getWidth() + "px X " + bitmap.getHeight() + " px");
                    break;*/
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;

        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    void AddCar() throws IOException
    {
        TextView result_guid =(TextView)findViewById(R.id.guid);
        EditText nom = (EditText) findViewById (R.id.nom);
        EditText marque = (EditText) findViewById (R.id.tv_marque);
        EditText model = (EditText) findViewById (R.id.tv_modele);
        EditText immat = (EditText) findViewById (R.id.immat);
        EditText cv = (EditText) findViewById (R.id.cv);

        final String guidtxt = result_guid.getText().toString();
        final String nomtxt = nom.getText().toString();
        final String marquetxt = marque.getText().toString();
        final String modeltxt = model.getText().toString();
        final String immattxt = immat.getText().toString();
        final String cvtxt = cv.getText().toString();

        if(nomtxt.matches("") || marquetxt.matches("") || modeltxt.matches("") ||immattxt.matches("") ||guidtxt.matches("") )
        {

            Toast.makeText(Add_car.this, "Les champs ne sont pas tous remplis", Toast.LENGTH_LONG).show();

        }
        else
        {

            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("GUID", guidtxt);

            // dynamically add more parameter like this:
            formBuilder.add("UserID", "11");
            formBuilder.add("CarImmat", immattxt);
            formBuilder.add("CarName", nomtxt);
            formBuilder.add("CarBrand", marquetxt);
            formBuilder.add("CarModel", modeltxt);
            formBuilder.add("DateImmat", "2017-04-10");
            formBuilder.add("CV",cvtxt);

            String img= "data:image/png;base64,"+encoded;
            formBuilder.add("Photo",img);


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
                    Log.v("Voiture",myResponse);


                    Add_car.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (myResponse.equals("voiture crée")) {

                                Toast.makeText(Add_car.this, "Voiture enregistré avec succés", Toast.LENGTH_LONG).show();
                                Intent myIntent = new Intent(getBaseContext(), Mes_voitures.class);
                                //myIntent.putExtra("mailpro",mailtxt); // On transmet la variable à la nouvelle activité
                                startActivity(myIntent);
                            }
                            if (myResponse.equals("erreur de création")) {
                                Toast.makeText(Add_car.this, "La voiture n'a pas été enregistré", Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                }
            });

        }


    }
}
