package com.example.jb.icarnfc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Pro extends AppCompatActivity {

     TextView textView;
     //String TAG="test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro);
        String mail = (String) getIntent().getSerializableExtra("mailpro");

        //Log.v(TAG,value);
        textView=(TextView) findViewById(R.id.mailpro);
        textView.setText("Bonjour "+mail);

    }
}
