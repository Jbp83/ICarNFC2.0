package com.example.jb.icarnfc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Mars on 25/04/2017.
 */



public class VoitureAdapter extends ArrayAdapter<Voiture> {

    private Activity activity;

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
                String idselect= voiture.getId();
                Log.v("delete",idselect);
                DeleteCar(idselect);

               /* Intent myIntent = new Intent (activity.getBaseContext(), Infos_car.class);
                activity.finishActivity(5);*/

            }
        });

        return convertView;
    }

    public void DeleteCar(String id)
    {
        Log.v("test","testr");

    







        Intent myIntent = new Intent (activity.getBaseContext(), Mes_voitures.class);
        activity.startActivity(myIntent);
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