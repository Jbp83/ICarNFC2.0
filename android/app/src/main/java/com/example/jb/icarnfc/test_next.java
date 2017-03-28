package com.example.jb.icarnfc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class test_next extends AppCompatActivity {


        public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_test_next);
        }

        /** Called when the user taps the Send button */
        public void sendMessage(View view) {
            Intent intent = new Intent(this, DisplayMessageActivity.class);
            TextView editText = (TextView) findViewById(R.id.editText2);
            String message = editText.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
    }

