package com.example.jb.icarnfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class ReadTag extends AppCompatActivity {

        IntentFilter[] intentFiltersArray = null;
        NfcAdapter nfcAdapter;
        PendingIntent pendingIntent;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_read_tag);

            nfcAdapter = NfcAdapter.getDefaultAdapter(this);
            pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        }

        public void onResume() {
            super.onResume();
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, null);
        }


        public void onPause() {
            super.onPause();
            nfcAdapter.disableForegroundDispatch(this);
        }

        public void onNewIntent(Intent intent) {
            String action = intent.getAction();
            if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action) || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
                try {
                    resolveIntent(intent);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        String getTextData(byte[] payload) {
            String texteCode = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
            int langageCodeTaille = payload[0] & 0077;
            try
            {
                return new String(payload, langageCodeTaille + 1, payload.length - langageCodeTaille - 1, texteCode);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return "";
        }

        public static String byteArrayToHexString(byte[] bArray) {
//        if (bArray.length==0) {
//            throw new ByteArrayToHexaStringException();
//        }

            StringBuilder sb = new StringBuilder(bArray.length * 2);
            for (byte b : bArray)
            {
                sb.append(String.format("%02X ", b));
            }
            return sb.toString();
        }

        public void resolveIntent(Intent intent) throws UnsupportedEncodingException {

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            byte[] id = tag.getId();
            String strr = byteArrayToHexString(id);
            String str = new String(id, "UTF-8"); // for UTF-8 encoding
            Log.i("GUID", strr + " / " + String.valueOf(strr));
            String[] technologies = tag.getTechList();
            int content = tag.describeContents();
            Ndef ndef = Ndef.get(tag);
            boolean isWritable = ndef.isWritable();
            boolean canMakeReadOnly = ndef.canMakeReadOnly();

            
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs;
            String message = null;
            if (rawMsgs != null){
                msgs = new NdefMessage[rawMsgs.length];

                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                    NdefRecord record = msgs[i].getRecords()[i];
                    byte[] idRec = record.getId();
                    short tnf = record.getTnf();
                    byte[] type = record.getType();
                    message = getTextData(record.getPayload());

                    Log.i("id", String.valueOf(idRec));
                    Log.i("tnf", String.valueOf(tnf));
                    Log.i("type", String.valueOf(type));
                }
            }

            Log.i("msg", message);
        }


    }


