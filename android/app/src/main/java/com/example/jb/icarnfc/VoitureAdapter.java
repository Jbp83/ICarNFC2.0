package com.example.jb.icarnfc;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

/**
 * Created by Mars on 25/04/2017.
 */



public class VoitureAdapter extends ArrayAdapter<Voiture> {

    //tweets est la liste des models à afficher
    public VoitureAdapter(Context context, List<Voiture> tweets) {
        super(context, 0, tweets);
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
            viewHolder.dateimat = (TextView) convertView.findViewById(R.id.dateimat);
            viewHolder.cv = (TextView) convertView.findViewById(R.id.cv);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Voiture voiture = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.nomvoiture.setText(voiture.getNom());
        viewHolder.immatriculation.setText(voiture.getImmatriculation());
        viewHolder.dateimat.setText((CharSequence) voiture.getDateImmat());
        viewHolder.cv.setText(voiture.getCV());
        //viewHolder.avatar.setImageDrawable(new ColorDrawable(voiture.get()));



        return convertView;
    }

    private class VoitureClassHolder{
        TextView nomvoiture;
        public TextView text;
        private ImageView avatar;
        private TextView immatriculation;
        private TextView dateimat;
        private TextView cv;
    }
}