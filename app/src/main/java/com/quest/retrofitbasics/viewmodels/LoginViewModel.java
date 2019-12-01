package com.quest.retrofitbasics.viewmodels;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.quest.retrofitbasics.constants.RetrofitClient;
import com.quest.retrofitbasics.models.LoginModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Observable;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends Observable {

    private Context context;
    SweetAlertDialog pDialog ;
    public ObservableField<String> useremail = new ObservableField<>("");
    public ObservableField<String> userpass = new ObservableField<>("");


    public LoginViewModel(Context context) {
        this.context = context;
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
    }

    public void sendLoginRequest(String useremail,String password){
        //showToast("in view model logic runnign");

        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();


        Call<LoginModel> call = RetrofitClient
                .getInstance()
                .getApi()
                .loginUser(useremail,password);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                pDialog.dismiss();
                Log.d("mwenda", "onResponse: "+response.body());
//                if(response.body().getSuccess()){
//                    Log.d("mwenda", "onResponse: successfule");
//                }else{
//                    Log.d("mwenda", "onResponse: not successful");
//                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                pDialog.dismiss();
                showToast("An error occurred, please try again");
            }
        });

    }

    void showToast(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
