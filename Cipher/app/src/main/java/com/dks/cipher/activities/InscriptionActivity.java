package com.dks.cipher.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dks.cipher.AppActivity;
import com.dks.cipher.R;
import com.dks.cipher.controllers.http.services.CipherInterface;
import com.dks.cipher.controllers.http.services.CipherService;
import com.dks.cipher.helper.SessionManager;
import com.dks.cipher.model.User;

import java.util.ConcurrentModificationException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InscriptionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonInscription;
    private TextView textViewIamCompte;
    private TextView textViewUsername;
    private TextView textViewPassword;
    private TextView textViewRetypePassword;
    private TextView textViewEmail;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inscription);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        buttonInscription = (Button) findViewById(R.id.buttonInscription);
        textViewIamCompte = (TextView) findViewById(R.id.textViewIamCompte);
        textViewUsername = (TextView) findViewById(R.id.username);
        textViewPassword = (TextView) findViewById(R.id.password);
        textViewRetypePassword = (TextView) findViewById(R.id.retypePassword);
        textViewEmail = (TextView) findViewById(R.id.email);

        /*buttonInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });*/
        buttonInscription.setOnClickListener(this);

        textViewIamCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InscriptionActivity.this, AppActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private Boolean makeValidation(String username, String password,
                                   String retypePassword, String email){
        boolean isValidation = true;

        CharSequence text = "";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);

       if(email.length() == 0){
           isValidation = false;
           toast.setText("Veillez renseigner un email !");
            toast.show();
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
           isValidation = false;
           toast.setText("L'email saisi est incorrect !");
           toast.show();
       }else if(username.length() == 0){
           isValidation = false;
           toast.setText("Veillez renseigner un username !");
            toast.show();
        }else if(password.length() == 0){
           isValidation = false;
           toast.setText("Veillez renseigner un password !");
            toast.show();
        }else if(retypePassword.length() == 0){
           isValidation = false;
           toast.setText("Veillez confirmer le password !");
            toast.show();
        }else if(!password.trim().toUpperCase().equals(retypePassword.trim().toUpperCase())){
           toast.setText("Désolé les passwords ne correspondent pas !");
           Log.d("password", "password est "+ password);
           Log.d("password", "retype password est "+ retypePassword);
           isValidation = false;
           toast.show();
       }


       return isValidation;
    }

    private void createUser(String username, String password, String retypePassword, String email){
        if(makeValidation(username, password, retypePassword, email)){
            CipherService cipherService = CipherInterface.getClient().create(CipherService.class);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("email", email);
            map.put("username", username);
            map.put("password", password);
            map.put("typeuser", 1);
            Call<User> userCall = cipherService.createUser(map);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, "Votre compte a été crée avec succès !", duration);
                        toast.show();
                        Intent intent = new Intent(InscriptionActivity.this, LoginActivity.class);
                        intent.putExtra("user_create", "user_create");
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("createUser", "une erreur est survenue lors de la création de l'utilisateur, error:"+t.getMessage());
                }
            });

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonInscription:
                CipherService cipherService = CipherInterface.getClient().create(CipherService.class);
                HashMap<String, Object> map = new HashMap<String, Object>();

                String email = textViewEmail.getText().toString();
                String password = textViewPassword.getText().toString();
                String username = textViewUsername.getText().toString();
                String retypePassword = textViewRetypePassword.getText().toString();

                createUser(username, password, retypePassword, email);

                /*new Thread(new Runnable() {
                    public void run() {
                        // createUser();
                        CipherService cipherService = CipherInterface.getClient().create(CipherService.class);
                        HashMap<String, Object> map = new HashMap<String, Object>();

                        String email = textViewEmail.getText().toString();
                        String password = textViewPassword.getText().toString();
                        String username = textViewUsername.getText().toString();
                        String retypePassword = textViewRetypePassword.getText().toString();

                        Log.d("position", "nous sommes dans runnable");

                        if(makeValidation(username, password, retypePassword, email)){

                            Log.d("position", "nous sommes dans la validation");

                            Log.d("Email", "l'email est "+email);
                            Log.d("Password", "le password est "+password);
                            Log.d("Username", "l'username est "+username);
                            // map.put("email", textViewEmail.getText().toString());
//                        map.put("username", textViewPassword.getText());
//                        map.put("password", textViewPassword.getText());

//                          map.put("email", "ddddddd@yopmail.com");
//                          map.put("username", "dddddd");
//                          map.put("password", "dddddd45874dd");

                            map.put("email", email);
                            map.put("username", username);
                            map.put("password", password);

                            map.put("typeuser", 1);
                            Call<User> userCall = cipherService.createUser(map);
                            userCall.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    if(response.isSuccessful()){
                                        int duration = Toast.LENGTH_SHORT;
                                        Toast toast = Toast.makeText(context, "Votre compte a été crée avec succès !", duration);
                                        toast.show();
                                        Intent intent = new Intent(InscriptionActivity.this, MainActivity1.class);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Log.e("createUser", "une erreur est survenue lors de la création de l'utilisateur, error : "+t.getMessage());
                                }
                            });
                        }
                    }
                }).start();*/

        }
    }
}
