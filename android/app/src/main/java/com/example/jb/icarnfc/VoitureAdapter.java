package com.example.jb.icarnfc;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.jb.icarnfc.common.GlobalVars.IPSERVEUR;

/**
 * Created by Mars on 25/04/2017.
 */



public class VoitureAdapter extends ArrayAdapter<Voiture> {

    private Activity activity;
    String idselect;

    //tweets est la liste des models à afficher
    public VoitureAdapter(Context context, List<Voiture> voitures, Activity activity) {
        super(context, 0, voitures);

        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_car,parent, false);
        }

        VoitureClassHolder viewHolder = (VoitureClassHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new VoitureClassHolder();
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            viewHolder.immatriculation = (TextView) convertView.findViewById(R.id.immatriculation);
            viewHolder.marque= (TextView) convertView.findViewById(R.id.marque);
            viewHolder.modele= (TextView) convertView.findViewById(R.id.modele);
            viewHolder.id = (TextView) convertView.findViewById(R.id.idvoiture);
            viewHolder.BtnInfo = (Button) convertView.findViewById(R.id.btnInfo);
            viewHolder.imageViewpoubelle = (ImageView) convertView.findViewById(R.id.poubelle);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Voiture> voiture
        final Voiture voiture = getItem(position);

        //il ne reste plus qu'à remplir notre vue

        viewHolder.modele.setText(voiture.getModele());
        viewHolder.immatriculation.setText(voiture.getImmatriculation());
        viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
        viewHolder.marque.setText(String.valueOf(voiture.getMarque()));
        viewHolder.id.setText(String.valueOf(voiture.getId()));

        viewHolder.BtnInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("bouton appuyé","id : "+voiture.getId());

                String idselect= voiture.getId();
                Log.v("coucou",idselect);

                Intent myIntent = new Intent (activity.getBaseContext(), Infos_car.class);
                activity.startActivity(myIntent);

            }
        });

        viewHolder.imageViewpoubelle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                Log.d("bouton appuyé","id : "+voiture.getId());
                idselect= voiture.getId();
                Log.v("delete",idselect);
                //DeleteCar(idselect);

               /* Intent myIntent = new Intent (activity.getBaseContext(), Infos_car.class);
                activity.finishActivity(5);*/


                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:

                                DeleteCar(idselect);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Etes vous sur de vouloir supprimer cette voiture ?").setPositiveButton("Oui", dialogClickListener)
                        .setNegativeButton("Non", dialogClickListener).show();

            }
        });


        return convertView;
    }

    public void DeleteCar(String id)
    {
        Log.v("id",id);

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("idVoiture", id);

        // dynamically add more parameter like this:


        RequestBody formBody = formBuilder.build();

        Request request = new Request.Builder()
                .url(IPSERVEUR+"/deleteCar")
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
                Log.v("reponse", myResponse);

            }
        });



        activity.finish();
        Intent myIntent = new Intent (activity.getBaseContext(), Mes_voitures.class);
        activity.startActivity(myIntent);
        //Toast.makeText(myIntent, "Les champs ne sont pas tous remplis", Toast.LENGTH_LONG).show();

    }


    private class VoitureClassHolder
    {
        public TextView id;
        public TextView modele;
        public ImageView avatar;
        public TextView immatriculation;
        public TextView marque;
        public Button BtnInfo;
        public ImageView imageViewpoubelle;
    }
}