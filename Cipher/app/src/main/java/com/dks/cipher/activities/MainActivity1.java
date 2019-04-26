package com.dks.cipher.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.dks.cipher.R;
import com.dks.cipher.adapter.GalleryAdapter;
import com.dks.cipher.app.AppController;
import com.dks.cipher.controllers.http.services.CipherInterface;
import com.dks.cipher.controllers.http.services.CipherService;
import com.dks.cipher.helper.SessionManager;
import com.dks.cipher.model.Image;
import com.dks.cipher.model.Publication;
import com.dks.cipher.model.Token;
import com.dks.cipher.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity1 extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private static final String endpoint = "https://api.androidhive.info/json/glide.json";
    private ArrayList<Image> images, imagesAllPublications;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private GalleryAdapter galleryAdapterAllPublications;
    private RecyclerView recyclerViewMyPublication;
    private RecyclerView recyclerViewAllPublications;
    private SessionManager sessionManager;
    private CipherService cipherService;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // create a session manager
        sessionManager = new SessionManager(this);

        // get token
        // getToken();

        Log.d("user", "user" + sessionManager.getUser());

        setContentView(R.layout.activity_main1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewMyPublication = (RecyclerView) findViewById(R.id.recyclerview_my_images);
        recyclerViewAllPublications = (RecyclerView) findViewById(R.id.recyclerview_all_images);

        pDialog = new ProgressDialog(this);
        images = new ArrayList<>();
        imagesAllPublications = new ArrayList<>();
        mAdapter = new GalleryAdapter(getApplicationContext(), images);
        galleryAdapterAllPublications = new GalleryAdapter(getApplicationContext(), imagesAllPublications );

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity1.this, AddPublicationActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewMyPublication.setLayoutManager(mLayoutManager);
        recyclerViewMyPublication.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMyPublication.setAdapter(mAdapter);

        RecyclerView.LayoutManager mLayoutManagerAllPublications = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewAllPublications.setLayoutManager(mLayoutManagerAllPublications);
        recyclerViewAllPublications.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAllPublications.setAdapter(galleryAdapterAllPublications);

        recyclerViewMyPublication.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerViewMyPublication, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                bundle.putInt("position", position);

                Intent intent = new Intent(MainActivity1.this, DetailActivityPublication.class);
                // intent.putExtra("imageId", images.get(position).getPk());
//                Log.d("imaeId", "@@@@@@@@@@@@@@@@@@@@@@@ pk"+ images.get(position).getPk());
//                Log.d("imaeId", "@@@@@@@@@@@@@@@@@@@@@@@ medium"+ images.get(position).getMedium());
                intent.putExtra("imageId", 12);
                startActivity(intent);

//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
//                newFragment.setArguments(bundle);
//                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.all_publications:
                        recyclerViewMyPublication.setVisibility(View.GONE);
                        recyclerViewAllPublications.setVisibility(View.VISIBLE);
                        break;
                    case R.id.my_publication:
                        recyclerViewMyPublication.setVisibility(View.VISIBLE);
                        recyclerViewAllPublications.setVisibility(View.GONE);
                        break;
                }
                return false;
            }
        });

        fetchImages();
    }


    private void fetchImages2(){
        pDialog.setMessage("Downloading json...");
        pDialog.show();

        final Token token = sessionManager.getToken();
        final User user = sessionManager.getUser();
        cipherService = CipherInterface.getClient().create(CipherService.class);
        // String strToken = "19eaee456cb667cb6f6bf3b1b331ac60cbbdc38f";
        Log.d("userId", "l'id de l'utilisateur dans mainActivity1 est "+user.getId());
        Call<List<Image>> listCall = cipherService.getAllImagesByUser("Token "+token.getToken(), user.getId());
        // Call<List<Image>> listCall = cipherService.getAllImagesByUser("Token "+strToken, 1);
        listCall.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, retrofit2.Response<List<Image>> response) {
                if(response.isSuccessful()){

                    Log.d(TAG, response.toString());
                    pDialog.hide();

                    images.clear();

                    List<Image> imageList = response.body();
                    for (Image image : imageList) {

                        Image imageAdapter = new Image();
                        // imageAdapter.setName("dd");
                        imageAdapter.setPk(image.getPk()
                        );

                        // imageAdapter.setSmall(CipherInterface.getBaseUrl().substring(0,CipherInterface.getBaseUrl().length()-1)+image.getImage());
                        imageAdapter.setMedium(CipherInterface.getBaseUrl().substring(0,CipherInterface.getBaseUrl().length()-1)+image.getImage());
                        // image.setMedium("https://api.androidhive.info/images/glide/medium/bvs.jpg");
                        // image.setMedium("http://192.168.1.100:8000/media/images/2019/04/12/bvs.jpg");
                        // image.setMedium("http://192.168.1.100:8000/media/images/2019/04/12/bvs.jpg");
                        // image.setMedium("http://192.168.1.100:8000/media/images/2019/04/12/4afdaf89-9ac.png");
                        // imageAdapter.setLarge(CipherInterface.getBaseUrl().substring(0,CipherInterface.getBaseUrl().length()-1)+image.getImage());
                        // imageAdapter.setTimestamp("ddd");

                        images.add(imageAdapter);
                    }

                    mAdapter.notifyDataSetChanged();

                    Log.d("loading", "le chargement des publications a été effectué avec succès");
                }
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                Log.e("loading", "le chargement des publications généré une erreur");
            }
        });
    }

    private void fetchAllPublications(){
        pDialog.setMessage("Downloading publications ...");
        pDialog.show();

        final Token token = sessionManager.getToken();
        final User user = sessionManager.getUser();
        cipherService = CipherInterface.getClient().create(CipherService.class);
        Call<List<Image>> listCall = cipherService.getAllImages("Token "+token.getToken());
        listCall.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, retrofit2.Response<List<Image>> response) {
                if(response.isSuccessful()){

                    Log.d(TAG, response.toString());
                    pDialog.hide();

                    imagesAllPublications.clear();
                    List<Image> imageList = response.body();

                    for(Image image: imageList){

                        Image imageAdapter = new Image();
                        imageAdapter.setPk(image.getPk());
                        imageAdapter.setMedium(image.getImage());

                        imagesAllPublications.add(imageAdapter);
                    }

                    galleryAdapterAllPublications.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                Log.e("loading", "le chargement des publications généré une erreur");
            }
        });

        /*listCall.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, retrofit2.Response<List<Image>> response) {
                if(response.isSuccessful()){

                    Log.d(TAG, response.toString());
                    pDialog.hide();

                    images.clear();

                    List<Image> imageList = response.body();
                    for (Image image : imageList) {

                        Image imageAdapter = new Image();
                        // imageAdapter.setName("dd");
                        imageAdapter.setPk(image.getPk()
                        );

                        // imageAdapter.setSmall(CipherInterface.getBaseUrl().substring(0,CipherInterface.getBaseUrl().length()-1)+image.getImage());
                        imageAdapter.setMedium(CipherInterface.getBaseUrl().substring(0,CipherInterface.getBaseUrl().length()-1)+image.getImage());
                        // image.setMedium("https://api.androidhive.info/images/glide/medium/bvs.jpg");
                        // image.setMedium("http://192.168.1.100:8000/media/images/2019/04/12/bvs.jpg");
                        // image.setMedium("http://192.168.1.100:8000/media/images/2019/04/12/bvs.jpg");
                        // image.setMedium("http://192.168.1.100:8000/media/images/2019/04/12/4afdaf89-9ac.png");
                        // imageAdapter.setLarge(CipherInterface.getBaseUrl().substring(0,CipherInterface.getBaseUrl().length()-1)+image.getImage());
                        // imageAdapter.setTimestamp("ddd");

                        images.add(imageAdapter);
                    }

                    mAdapter.notifyDataSetChanged();

                    Log.d("loading", "le chargement des publications a été effectué avec succès");
                }
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                Log.e("loading", "le chargement des publications généré une erreur");
            }
        });*/
    }

    private void fetchImages() {
        // fecthImages3();
        fetchImages2();
        fetchAllPublications();
    }

    private void fecthImages3(){
        pDialog.setMessage("Downloading json...");
        pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(endpoint,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();

                        images.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Image image = new Image();
                                image.setName(object.getString("name"));

                                // JSONObject url = object.getJSONObject("url");
                                // image.setSmall(url.getString("small"));
                                // image.setMedium(url.getString("medium"));
                                image.setMedium("https://api.androidhive.info/images/glide/medium/bvs.jpg");
                                // image.setLarge(url.getString("large"));
                                // image.setTimestamp(object.getString("timestamp"));

                                images.add(image);

                                // Log.d("@@@@@@@@@", String.valueOf(response.getJSONObject(i)));

                                // } catch (JSONException e) {
                            } catch (Exception e) {
                                Log.e(TAG, "Json parsing error: " + e.getMessage());
                            }
                        }

                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    // get token
//    private void getToken(){
//        try{
//            CipherService cipherService = CipherInterface.getClient().create(CipherService.class);
//
//            HashMap<String, Object> m = new HashMap<String, Object>();
//            m.put("username","bootnetcrasher");
//            m.put("password","123456789");
//            Call<Token> call = cipherService.getToken1(m);
//            call.enqueue(new Callback<Token>() {
//                @Override
//                public void onResponse(Call<Token> call, retrofit2.Response<Token> response) {
//                    if(response.isSuccessful()){
//                        Token token = response.body();
//                        // save token
//                        sessionManager.setToken(token);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Token> call, Throwable t) {
//                    Log.e("token", "Une erreur est survenue lors de la génération de token, error :"+t.getMessage());
//                }
//            });
//        }catch (Exception e){
//            Log.e("token", "Une erreur est survenue lors de la génération de token, error :"+e.getMessage());
//        }
//    }

    @Override
    public void onResume() {
        Log.d("restart", "nous sommes dans le resume");
        super.onResume();
    }

    @Override
    public void onRestart() {
         fetchImages2();
        Log.d("restart", "nous sommes dans le restart");
        super.onRestart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_about){
            return true;
        }

        if(id == R.id.action_deconnexion){

            // remove session
            sessionManager.remove();

            Intent intent = new Intent(MainActivity1.this, LoginActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        this.finish();
//        /*count++;
//        if (count >=1) {
//            Intent intent = new Intent(ResetActivity.this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//            overridePendingTransition
//                    (R.anim.push_left_in, R.anim.push_left_out);
//            finishAffinity();
//        } else {
//            Toast.makeText(this, "Press back again to Leave!", Toast.LENGTH_SHORT).show();
//
//            // resetting the counter in 2s
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    count = 0;
//                }
//            }, 2000);
//        }*/
//        // super.onBackPressed();
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}