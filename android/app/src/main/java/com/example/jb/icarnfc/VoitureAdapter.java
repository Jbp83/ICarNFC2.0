package com.example.jb.icarnfc;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jb.icarnfc.Object.Voiture;

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
            viewHolder.immatriculation = (TextView) convertView.findViewById(R.id.tv_immatriculation);
            viewHolder.marque= (TextView) convertView.findViewById(R.id.tv_marque);
            viewHolder.modele= (TextView) convertView.findViewById(R.id.tv_modele);
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
        String[] separated = voiture.getPhoto().split(",");
        final String s = separated[1];
        Log.v("coup",s); // On recupere la chaine aprss la virgule
        byte[] decodedString = Base64.decode(s, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        viewHolder.avatar.setImageBitmap(decodedByte);
        viewHolder.marque.setText(String.valueOf(voiture.getMarque()));
        viewHolder.id.setText(String.valueOf(voiture.getId()));
        viewHolder.BtnInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("bouton appuyé","id : "+voiture.getId());
                String idselect= voiture.getId();
                Log.v("iditemlistview",idselect);
                Intent myIntent = new Intent (activity.getBaseContext(), Infos_car.class);
                myIntent.putExtra("idvoitureselect",idselect);
                activity.startActivity(myIntent);
            }
        });

        viewHolder.imageViewpoubelle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                Log.v("bouton appuyé","id : "+voiture.getId()); // On recupere l'id de la voiture selectionné
                idselect= voiture.getId(); // On met l'id dans un variable de type string
                Log.v("delete",idselect);


                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:

                                DeleteCar(idselect); // Si on appuie sur le DialogAlert sur oui on supprime l'item donc la voiture
                                break;

                            case DialogInterface.BUTTON_NEGATIVE: // Sinon on ne fait rien
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Etes vous sur de vouloir supprimer cette voiture ?").setPositiveButton("Oui", dialogClickListener)
                        .setNegativeButton("Non", dialogClickListener).show(); // Message d'alerte si on supprime ou non la voiture

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