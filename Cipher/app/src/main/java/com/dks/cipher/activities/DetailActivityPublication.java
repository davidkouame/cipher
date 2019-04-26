package com.dks.cipher.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dks.cipher.AppActivity;
import com.dks.cipher.R;
import com.dks.cipher.controllers.http.services.CipherInterface;
import com.dks.cipher.controllers.http.services.CipherService;
import com.dks.cipher.helper.SessionManager;
import com.dks.cipher.model.Image;
import com.dks.cipher.model.Token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivityPublication extends AppCompatActivity {

    private SessionManager sessionManager;
    private ImageView imageView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_publication);

        sessionManager = new SessionManager(this);
        imageView = findViewById(R.id.imageView);
        context = this;

        Intent intent = getIntent();
        final int imageId = intent.getIntExtra("imageId", 0);
        Token token = sessionManager.getToken();
        // Toolbar toolbar = findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
//        ActionBar ab = getSupportActionBar();
//
//        // Enable the Up button
//        ab.setDisplayHomeAsUpEnabled(true);



        CipherService cipherService = CipherInterface.getClient().create(CipherService.class);
        Call<Image> imageCall = cipherService.getInformationImage("Token "+token.getToken(), imageId);
        imageCall.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                if(response.isSuccessful()){
                    Image image = response.body();

                    // Log.d("image==", CipherInterface.getBaseUrl().substring(0,CipherInterface.getBaseUrl().length()-1)+image.getImage());
                    // Log.d("image==", image.getImage());
                    Glide.with(context).load(image.getImage())
                            .thumbnail(0.5f)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageView);
                }
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {

            }
        });
    }
}
