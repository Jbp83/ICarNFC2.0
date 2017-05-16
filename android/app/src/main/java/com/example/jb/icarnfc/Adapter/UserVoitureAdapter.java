package com.example.jb.icarnfc.Adapter;

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

import com.example.jb.icarnfc.Entreprise.AddFicheEntretienClientVoiture;
import com.example.jb.icarnfc.Entreprise.AddFicheToVoiture;
import com.example.jb.icarnfc.Infos_car;
import com.example.jb.icarnfc.Mes_voitures;
import com.example.jb.icarnfc.Object.Voiture;
import com.example.jb.icarnfc.R;

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
 * Created by Jb on 16/05/2017.
 */

public class UserVoitureAdapter extends ArrayAdapter<Voiture> {


        //tweets est la liste des models à afficher
        public UserVoitureAdapter(Context context, List<Voiture> voitures) {
            super(context, 0, voitures);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_voiture_user,parent, false);
            }

            com.example.jb.icarnfc.Adapter.UserVoitureAdapter.VoitureClassHolder viewHolder = (com.example.jb.icarnfc.Adapter.UserVoitureAdapter.VoitureClassHolder) convertView.getTag();
            if(viewHolder == null){
                viewHolder = new com.example.jb.icarnfc.Adapter.UserVoitureAdapter.VoitureClassHolder();
                viewHolder.avatar = (ImageView) convertView.findViewById(R.id.Vavatar);
                viewHolder.immatriculation = (TextView) convertView.findViewById(R.id.tv_immatriculationV);
                viewHolder.marque= (TextView) convertView.findViewById(R.id.tv_marqueV);
                viewHolder.modele= (TextView) convertView.findViewById(R.id.tv_modeleV);
                viewHolder.BtnSelect = (Button) convertView.findViewById(R.id.btnSelectV);
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
            viewHolder.BtnSelect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String idselect= voiture.getId();
                    String idProp = String.valueOf(voiture.getId_proprietaire());
                    Intent monIntent = new Intent(getContext(), AddFicheToVoiture.class);
                    monIntent.putExtra("idVoiture", idselect );
                    monIntent.putExtra("idClient", idProp );
                    getContext().startActivity(monIntent);

                }
            });


            return convertView;
        }


    private class VoitureClassHolder
    {
        public TextView modele;
        public ImageView avatar;
        public TextView immatriculation;
        public TextView marque;
        public Button BtnSelect;

    }
}


