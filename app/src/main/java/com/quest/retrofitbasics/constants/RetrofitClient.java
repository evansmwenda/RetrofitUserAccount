package com.quest.retrofitbasics.constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://skytoptrial.000webhostapp.com/functions/";

    private static RetrofitClient retrofitInstance;
    private static Retrofit retrofit;

    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if(retrofitInstance == null){
            retrofitInstance = new RetrofitClient();
        }
        return  retrofitInstance;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
