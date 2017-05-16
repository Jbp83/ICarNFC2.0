package com.example.jb.icarnfc.Entreprise;



import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jb.icarnfc.Adapter.UserAdapter;
import com.example.jb.icarnfc.HistoriqueEntretienPro;
import com.example.jb.icarnfc.Object.Utilisateur;
import com.example.jb.icarnfc.Object.Voiture;
import com.example.jb.icarnfc.R;
import com.example.jb.icarnfc.Requests.RequestListCarDb;
import com.example.jb.icarnfc.Requests.RequestUser;
import com.example.jb.icarnfc.common.GlobalVars;
import com.example.jb.icarnfc.common.UserSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static android.R.layout.simple_spinner_item;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AddFicheEntretienClient extends GlobalVars {

    UserSessionManager session;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fiche_entretien);
        mListView = (ListView) findViewById(R.id.UserListview);

                try {
                    RecuperationVoitureDb();
                } catch (IOException e) {
                    e.printStackTrace();
                }

    }

    private void RecuperationVoitureDb() throws IOException {
        try {
            RequestUser reqUser = new RequestUser();
            reqUser.getUserbyEntreprise("1",new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String repUser = response.body().string();

                    try {
                        final List<Utilisateur> Ulist = new ArrayList<Utilisateur>();
                        JSONArray Jarray = new JSONArray(repUser);
                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject object = Jarray.getJSONObject(i);
                            String idUser = object.getString("id");
                            String mail = object.getString("mail");
                            String nom = object.getString("nom");
                            String prenom = object.getString("prenom");
                            String status = object.getString("status");
                            String id_etablissement = object.getString("id_etablissement");
                            Ulist.add(new Utilisateur(idUser,nom,prenom,status,mail,id_etablissement));
                        }

                        AddFicheEntretienClient.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {

                                UserAdapter adapter = new UserAdapter(AddFicheEntretienClient.this, Ulist);
                                mListView.setAdapter(adapter);

                            }

                            });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
        } catch (InterruptedException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }


}
