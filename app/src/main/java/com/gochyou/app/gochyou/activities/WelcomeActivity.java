package com.gochyou.app.gochyou.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gochyou.app.gochyou.HomeActivity;
import com.gochyou.app.gochyou.R;
import com.facebook.FacebookSdk;
import com.gochyou.app.gochyou.helpers.SessionManager;


public class WelcomeActivity extends AppCompatActivity {

     LoginButton loginButton;
     CallbackManager callbackManager;
     SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Session Manager
        session = new SessionManager(getApplicationContext());

        //session.checkLogin();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        callbackManager = CallbackManager.Factory.create();


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code

                        // Use user real data
                        session.createLoginSession("Vinod Kumar", "anroidhive@gmail.com");

                        // Staring homeActivity
                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(i);
                        finish();

                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
