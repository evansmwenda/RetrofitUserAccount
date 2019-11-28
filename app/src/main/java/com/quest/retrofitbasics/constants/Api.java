package com.quest.retrofitbasics.constants;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {


    @FormUrlEncoded
    @POST("User/login.php")
    Call<ResponseBody> loginUser(
            @Field("email_address") String email_address,
            @Field("password") String password
    );
}
