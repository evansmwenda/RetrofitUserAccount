package com.quest.retrofitbasics;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.quest.retrofitbasics.constants.RetrofitClient;
import com.quest.retrofitbasics.models.LoginModel;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {

    //this is the data that we will fetch asynchronously
    private MutableLiveData<LoginModel> loginResponse;
    private Context context;
    SweetAlertDialog pDialog ;

//    public MainActivityViewModel(Context context) {
//        this.context = context;
//        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//    }

    //we will call this method to get the data
    public LiveData<LoginModel> getHeroes(String email_address,String password) {
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

        Call<LoginModel> call = RetrofitClient
                .getInstance()
                .getApi()
                .loginUser(email_address,password);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                loginResponse.setValue(response.body());
                Log.d("mwenda", "onResponse: "+response.body());
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.d("mwenda", "onFailure: "+t.getMessage());
            }
        });

    }
}