package com.example.jb.icarnfc;

import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Mars on 18/04/2017.
 */


public interface UsersAPI {


    @FormUrlEncoded
    @POST("/login")
    public void login(@Field("UserLogin") String login, @Field("UserPassword") String password,
                      Callback< User > callback);
}