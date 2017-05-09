package com.example.jb.icarnfc;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.TextView;
import com.example.jb.icarnfc.common.GlobalVars;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AddFicheEntretien extends GlobalVars {

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fiche_entretien);

        Date actuelle = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); // Recupération de la date pour l'ajout d'une fiche d'entretien
        String dat = dateFormat.format(actuelle);
        
        result = (TextView) findViewById(R.id.resultat);
        result.setText("\nil est " +dat);
    }



}
