package com.dks.cipher;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.dks.cipher.activities.LoginActivity;
import com.dks.cipher.activities.MainActivity1;
import com.dks.cipher.helper.SessionManager;
import com.dks.cipher.model.User;

public class AppActivity extends AppCompatActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create a session manager
        sessionManager = new SessionManager(this);

//        User user = new User();
//        user.setId(1);
//        user.setUsername("djnjnsfds");
//        user.setEmail("dndjdd");
//        user.setFirst_name("ddd");

//        sessionManager.setUser(user);
//
//        Log.d("user@", "@@@@@@@@@"+sessionManager.getUser());


        if(sessionManager.getUser()!=null){
            Intent intent = new Intent(this, MainActivity1.class);
             startActivity(intent);
             finish();
        }else{
            // call login page
            callLoginActivity();
        }

    }

    public void callLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
