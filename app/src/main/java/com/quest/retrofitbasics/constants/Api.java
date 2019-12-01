package com.quest.retrofitbasics.constants;

import com.quest.retrofitbasics.models.LoginModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {


    @FormUrlEncoded
    @POST("User/login.php")
    Call<LoginModel> loginUser(
            @Field("email_address") String email_address,
            @Field("password") String password
    );
}
