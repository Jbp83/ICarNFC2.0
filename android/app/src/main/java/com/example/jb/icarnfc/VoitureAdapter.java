package com.example.jb.icarnfc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Mars on 25/04/2017.
 */



public class VoitureAdapter extends ArrayAdapter<Voiture> {

    //tweets est la liste des models à afficher
    public VoitureAdapter(Context context, List<Voiture> voitures) {
        super(context, 0, voitures);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_car,parent, false);
        }

        VoitureClassHolder viewHolder = (VoitureClassHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new VoitureClassHolder();
            viewHolder.nomvoiture = (TextView) convertView.findViewById(R.id.nomvoiture);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            viewHolder.immatriculation = (TextView) convertView.findViewById(R.id.immatriculation);
            viewHolder.cv = (TextView) convertView.findViewById(R.id.cv);
            viewHolder.modele= (TextView) convertView.findViewById(R.id.modele);
            viewHolder.idproprio = (TextView) convertView.findViewById(R.id.idproprio);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Voiture voiture = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.nomvoiture.setText(voiture.getNom());
        viewHolder.modele.setText(voiture.getModele());
        viewHolder.immatriculation.setText(voiture.getImmatriculation());
        viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
        viewHolder.cv.setText(String.valueOf(voiture.getCV()));
        viewHolder.idproprio.setText(String.valueOf(voiture.getId_proprietaire()));

        return convertView;
    }

    private class VoitureClassHolder{
        public TextView idproprio;
        public TextView nomvoiture;
        public TextView modele;
        public ImageView avatar;
        public TextView immatriculation;
        public TextView cv;
    }
}