package com.example.jb.icarnfc;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Mars on 20/03/2017.
 */

interface PostTo {
    @FormUrlEncoded
    @POST("login")
    Call<User> sendUser(@Field("UserLogin") String login,@Field("UserPassword")String password);
}
