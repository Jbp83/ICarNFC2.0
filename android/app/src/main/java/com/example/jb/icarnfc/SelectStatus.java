package com.example.jb.icarnfc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class SelectStatus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_status);


        Button particulier= (Button) findViewById(R.id.particulier);
        particulier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(getBaseContext(), SignUp.class);
                startActivity(myIntent);

            }
        });


        Button pro= (Button) findViewById(R.id.pro);
        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(getBaseContext(), SignUpPro.class);
                startActivity(myIntent);

            }
        });
    }
}
