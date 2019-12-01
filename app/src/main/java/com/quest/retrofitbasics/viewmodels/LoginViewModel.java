package com.quest.retrofitbasics.viewmodels;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableInt;

import com.quest.retrofitbasics.constants.RetrofitClient;

import java.util.Observable;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends Observable {

    private Context context;
    public ObservableInt progressBar;
    SweetAlertDialog pDialog ;


    public LoginViewModel(Context context) {
        this.context = context;
        progressBar=new ObservableInt(View.GONE);
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
    }

    public void sendLoginRequest(String username,String password){
        //showToast("in view model logic runnign");

        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();


        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .loginUser(username,password);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                pDialog.dismiss();

                //TODO ADD LOGIC TO HANDLE JSON REPLY

                Log.d("mwenda", "onResponse: loginvm"+response.body().toString());
                showToast(response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                pDialog.dismiss();
                //TODO ADD ERROR HANDLING LOGIC

                Log.d("mwenda", "onFailure: "+t.getMessage());
                showToast(t.getMessage());

            }
        });
    }

    void showToast(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
