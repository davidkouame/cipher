package com.dks.cipher.controllers.http.services;

import com.dks.cipher.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CipherInterface {

    // public static final String BASE_URL = "https://cipher-dks.herokuapp.com/";
    public static final String BASE_URL = "http://192.168.1.39:8000/";
    // public static final String BASE_URL = "http://127.0.0.1:8000";

    private static Retrofit retrofit = null;

//    public static final String AUTHORISATION = "Bearer v1RYfAgZS1YehHdtka13XiyGzYgLO3";

    public static String getBaseUrl(){
        return BASE_URL;
    }

    public static Retrofit getClient() {
        if (retrofit==null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                httpClient.addInterceptor(logging);
            }


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}
