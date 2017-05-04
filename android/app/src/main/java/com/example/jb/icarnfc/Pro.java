package com.example.jb.icarnfc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.jb.icarnfc.common.GlobalVars;

public class Pro extends GlobalVars {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro);


        Button button= (Button) findViewById(R.id.scanpro);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), ScanParticulier.class);
                startActivity(myIntent);
            }
        });

        Button fiche= (Button) findViewById(R.id.addfiche);
        fiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), AddFicheEntretien.class);
                startActivity(myIntent);
            }
        });

        Button historique= (Button) findViewById(R.id.historique);
        historique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), HistoriqueEntretienPro.class);
                startActivity(myIntent);
            }
        });



    }
}
