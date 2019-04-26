package com.dks.cipher.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dks.cipher.R;
import com.dks.cipher.controllers.http.services.CipherInterface;
import com.dks.cipher.controllers.http.services.CipherService;
import com.dks.cipher.helper.SessionManager;
import com.dks.cipher.model.Token;
import com.dks.cipher.model.User;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button buttonConnexion;
    private TextView textViewIdontCompte;
    private SessionManager sessionManager;
    private TextView username;
    private TextView password;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        // create a session manager
        sessionManager = new SessionManager(this);

        context = this;

        Intent intent = getIntent();
        if(intent.hasExtra("user_create")){
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, "Votre compte a été crée avec succès !", duration);
            toast.show();
        }

        buttonConnexion = (Button) findViewById(R.id.buttonConnexion);
        textViewIdontCompte = (TextView) findViewById(R.id.textViewIdontCompte);
        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);

        buttonConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
                  login(username.getText().toString(), password.getText().toString());
            }
        });

        textViewIdontCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, InscriptionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login(final String username, String password){

        boolean isValidation = true;

        if(username.trim().toUpperCase().length()==0){
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "Veillez renseigner un username !", duration);
            toast.show();
            isValidation = false;
        }else if(password.trim().toUpperCase().length()==0){
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "Veillez renseigner un password !", duration);
            toast.show();
            isValidation = false;
        }

        if(isValidation){
            CipherService cipherService = CipherInterface.getClient().create(CipherService.class);

            HashMap<String, Object> m = new HashMap<String, Object>();
//        m.put("username","bootnetcrasher");
//        m.put("password","123456789");
            m.put("username", username);
            m.put("password", password);
            // Call<Token> call = cipherService.getToken(username, password);
            Call<Token> call = cipherService.getToken1(m);
            call.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    if(response.isSuccessful()){
                        Token token = response.body();

                        // save token
                        sessionManager.setToken(token);

                        CipherService cipherService = CipherInterface.getClient().create(CipherService.class);
                        Call<User> getUser = cipherService.getUserDetail("Token "+token.getToken(), username);
                        getUser.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if(response.isSuccessful()){
                                    // save user
                                    User user = response.body();
                                    sessionManager.setUser(user);
                                    Log.d("userId", "l'utilisateur dans loginActivity est "+user.getId());

                                    // change activity
                                    Intent intent = new Intent(LoginActivity.this, MainActivity1.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Log.e("connexion", "Une erreur s'est produite lors de la " +
                                        "recuperation des informations de l'utilisateur , error :"+t.getMessage());
                            }
                        });
                    }else{
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, "Identifiant ou mot de passe incorrect !", duration);
                        toast.show();
                        Log.e("connexion", "Une erreur s'est produite lors de la connexion à l'application cipher");
                    }
                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {
//                    int duration = Toast.LENGTH_SHORT;
//                    Toast toast = Toast.makeText(context, "Identifiant ou mot de passe incorrect !", duration);
//                    toast.show();
                    Log.e("connexion", "Une erreur s'est produite lors de la connexion à l'application cipher, error :"+t.getMessage());
                }
            });
        }

    }
}
