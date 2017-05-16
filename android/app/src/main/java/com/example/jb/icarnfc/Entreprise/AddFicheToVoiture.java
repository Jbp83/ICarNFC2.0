package com.example.jb.icarnfc.Entreprise;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jb.icarnfc.Adapter.UserAdapter;
import com.example.jb.icarnfc.Object.Utilisateur;
import com.example.jb.icarnfc.R;
import com.example.jb.icarnfc.Requests.RequestEtablissement;
import com.example.jb.icarnfc.Requests.RequestUser;
import com.example.jb.icarnfc.common.GlobalVars;
import com.example.jb.icarnfc.common.UserSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Jb on 16/05/2017.
 */

public class AddFicheToVoiture extends GlobalVars{

        UserSessionManager session;
        ListView mListView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_fiche_voiture);



            RequestEtablissement req = new RequestEtablissement();
            /*req.setFiche();*/
        }






}
