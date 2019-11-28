package com.quest.retrofitbasics;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.quest.retrofitbasics.constants.Api;
import com.quest.retrofitbasics.constants.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityViewModel extends ViewModel {

    //this is the data that we will fetch asynchronously
    private MutableLiveData<ResponseBody> loginResponse;

    //we will call this method to get the data
    public LiveData<ResponseBody> getHeroes(String email_address,String password) {
        //if the list is null
        if (loginResponse == null) {
            loginResponse = new MutableLiveData<>();
            //we will load it asynchronously from server in this method
            loadHeroes(email_address,password);
        }

        //finally we will return the list
        return loginResponse;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadHeroes(String email_address,String password) {
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .loginUser(email_address,password);


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loginResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}