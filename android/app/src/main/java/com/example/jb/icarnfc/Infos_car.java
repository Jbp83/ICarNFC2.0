package com.example.jb.icarnfc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Infos_car extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos_car);


        Intent intent = getIntent();
        final String idextra = intent.getStringExtra("id");
        TextView textView= (TextView) findViewById(R.id.result);
        textView.setText(idextra);

    }


}
