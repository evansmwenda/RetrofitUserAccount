package com.quest.retrofitbasics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.quest.retrofitbasics.constants.RetrofitClient;
import com.quest.retrofitbasics.models.LoginModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    MainActivityViewModel model;
    EditText editEmail,editPass;
    SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        model =new MainActivityViewModel(this);
        //for the view model
        //MainActivityViewModel model = ViewModelProvider.of(this).get(MainActivityViewModel.class);
        model = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        //MainActivityViewModel

        editEmail=findViewById(R.id.editEmail);
        editPass=findViewById(R.id.editPass);

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);



    }

    public void tryLogin(View view) {
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        String email,password;


        email=editEmail.getText().toString().trim();
        password=editPass.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return ;
        }


        Call<LoginModel> call = RetrofitClient
                .getInstance()
                .getApi()
                .loginUser(email,password);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                //oginResponse.setValue(response.body());
                pDialog.dismiss();
                if(response.isSuccessful()){
                    Log.d("mwenda", "onResponse: "+response.body().getReply());
                    Toast.makeText(MainActivity.this, ""+response.body().getReply(), Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String reply = jsonObject.optString("reply");
                        Log.d("mwenda", ""+reply);
                        Toast.makeText(MainActivity.this, ""+reply, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                pDialog.dismiss();
                Log.d("mwenda", "onFailure: "+t.getMessage());
            }
        });
    }

    void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
