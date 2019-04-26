package com.dks.cipher.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dks.cipher.R;
import com.dks.cipher.controllers.http.services.CipherInterface;
import com.dks.cipher.controllers.http.services.CipherService;
import com.dks.cipher.helper.SessionManager;
import com.dks.cipher.model.Image;
import com.dks.cipher.model.Publication;
import com.dks.cipher.model.Token;
import com.dks.cipher.model.User;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPublicationActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;
    private Button btnPublier;
    private Bitmap imageBitmap;
    private SessionManager sessionManager;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_publication);

        // create a session manager
        sessionManager = new SessionManager(this);

        imageView = findViewById(R.id.imageView);
        btnPublier = findViewById(R.id.btnPublier);

        context = getApplicationContext();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        btnPublier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getToken
                // getToken();

                if(imageBitmap != null){
                    final Token token = sessionManager.getToken();

                    final CipherService cipherService = CipherInterface.getClient().create(CipherService.class);
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("image", encodeTobase64(imageBitmap));

                    Call<Image> callImage = cipherService.postImage("Token "+token.getToken(), map);
                    callImage.enqueue(new Callback<Image>() {
                        @Override
                        public void onResponse(Call<Image> call, Response<Image> response) {
                            if(response.isSuccessful()){
                                Image image = response.body();

                                final User user = sessionManager.getUser();

                                HashMap<String, Object> map = new HashMap<String, Object>();
                                map.put("souscategorie", 1);
                                map.put("user", user.getId());
                                map.put("image", image.getPk());
                                Call<Publication> publicationCall = cipherService.postPublication("Token "+token.getToken(), map);
                                publicationCall.enqueue(new Callback<Publication>() {
                                    @Override
                                    public void onResponse(Call<Publication> call, Response<Publication> response) {
                                        if(response.isSuccessful()){
                                            Publication publication = response.body();
                                            Log.d("publication", "la publication a été " +
                                                    "effectué avec succès");

                                            // change activity
                                            Intent intent = new Intent(AddPublicationActivity.this, MainActivity1.class);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Publication> call, Throwable t) {
                                        Log.e("publication", "une erreur est survenue lors de " +
                                                "la publication");
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<Image> call, Throwable t) {
                            Log.e("uploadImage", "une erreur est survenue lors de " +
                                    "l'upload de l'image");
                        }
                    });
                }else{

                    CharSequence text = "Veillez uploader une image !";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }

            }
        });

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }

    private byte[] convertBitmapToBase64(Bitmap bitmap){
        byte[] byteArray = null;
        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byteArray = byteArrayOutputStream .toByteArray();
        }catch (Exception e){
            Log.e("convertion", "une erreur s'est produite lors de la conversion de l'image");
        }
        return  byteArray;
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }
}
