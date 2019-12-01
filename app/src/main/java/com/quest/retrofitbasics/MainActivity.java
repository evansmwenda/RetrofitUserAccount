package com.quest.retrofitbasics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.quest.retrofitbasics.databinding.ActivityMainBinding;
import com.quest.retrofitbasics.presenters.Presenter;
import com.quest.retrofitbasics.viewmodels.LoginViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    MainActivityViewModel model;
    EditText editEmail,editPass;
    private ActivityMainBinding activityMainBinding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        loginViewModel = new LoginViewModel(this);

        activityMainBinding.setLoginview(loginViewModel);

        activityMainBinding.setPresenter(new Presenter() {
            @Override
            public void loginData() {
                showToast("login activity called");
                loginViewModel.sendLoginRequest("test.user@gmail.com","password");

            }
        });

//        //for the view model
//        //MainActivityViewModel model = ViewModelProvider.of(this).get(MainActivityViewModel.class);
//        model = ViewModelProviders.of(this).get(MainActivityViewModel.class);
//        //MainActivityViewModel
//
//        editEmail=findViewById(R.id.editEmail);
//        editPass=findViewById(R.id.editPass);


    }

//    public void tryLogin(View view) {
//
//        String email,password;
//
//
//        email=editEmail.getText().toString().trim();
//        password=editPass.getText().toString().trim();
//        model.getHeroes(email,password).observe(this, new Observer<ResponseBody>() {
//            @Override
//            public void onChanged(ResponseBody responseBody) {
//                try {
//                    JSONObject jsonObject = new JSONObject(responseBody.string());
//                    boolean status = jsonObject.optBoolean("success",false);
//                    if(status){
//                        Toast.makeText(MainActivity.this, ""+jsonObject.optString("reply"), Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(MainActivity.this, "login is"+status, Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
