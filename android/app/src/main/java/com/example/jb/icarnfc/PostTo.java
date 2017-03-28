package com.example.jb.icarnfc;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Mars on 20/03/2017.
 */

interface PostTo {
    @POST("/login")
    Call<Repo> sendUser(@Body User body);
}
