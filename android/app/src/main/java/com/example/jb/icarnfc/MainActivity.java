package com.example.jb.icarnfc;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.jb.icarnfc.GitHubService1.retrofit;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        public void sendUserInfo(View view)
    {

        String API_URL = "https://example.server.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }



          User user = new User("macha", "da costa");

    public void sendUserInfo(View view) {

        PostTo post = retrofit.create(PostTo.class);

        Call<Repo> call = post.sendUser(user);

        call.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Response<Repo> response, Retrofit retrofit) {
                Toast.makeText(getActivity(), String.format("OK"), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), String.format("KO"), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
