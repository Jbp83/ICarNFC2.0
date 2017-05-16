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
import com.example.jb.icarnfc.Infos_car;
import com.example.jb.icarnfc.Mes_voitures;
import com.example.jb.icarnfc.Object.Utilisateur;
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

public class UserAdapter extends ArrayAdapter<Utilisateur> {

private Activity activity;
        //tweets est la liste des models à afficher
        public UserAdapter(Context context, List<Utilisateur> user) {
            super(context, 0, user);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_user,parent, false);
            }

            com.example.jb.icarnfc.Adapter.UserAdapter.UserViewHolder viewHolder = (com.example.jb.icarnfc.Adapter.UserAdapter.UserViewHolder) convertView.getTag();
            if(viewHolder == null){
                viewHolder = new com.example.jb.icarnfc.Adapter.UserAdapter.UserViewHolder();

                viewHolder.nom = (TextView) convertView.findViewById(R.id.tv_Nom);
                viewHolder.prenom = (TextView) convertView.findViewById(R.id.tv_prenom);
                viewHolder.BtnInfo = (Button) convertView.findViewById(R.id.btn_select);
                convertView.setTag(viewHolder);
            }

            //getItem(position) va récupérer l'item [position] de la List<Voiture> voiture
            final Utilisateur user = getItem(position);

            //il ne reste plus qu'à remplir notre vue
            String nom = user.getNom();
            String pnom = user.getPrenom();

            viewHolder.nom.setText(nom);
            viewHolder.prenom.setText(pnom);
            viewHolder.BtnInfo.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent monIntent = new Intent(getContext(), AddFicheEntretienClientVoiture.class);
                    monIntent.putExtra("idClient", user.getId() );
                    getContext().startActivity(monIntent);


                }
            });

            return convertView;
            }

    private class UserViewHolder
    {
        public TextView nom;
        public TextView prenom;
        public Button BtnInfo;

    }

 }




