package com.example.jb.icarnfc;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Mars on 13/03/2017.
 */

public interface GitHubService1 {
    @GET("/users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // GitHubService1 service = retrofit.create(GitHubService1.class);



}