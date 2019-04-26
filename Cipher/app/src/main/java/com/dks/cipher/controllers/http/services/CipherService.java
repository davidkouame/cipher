package com.dks.cipher.controllers.http.services;

import com.dks.cipher.model.Image;
import com.dks.cipher.model.Publication;
import com.dks.cipher.model.Token;
import com.dks.cipher.model.User;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CipherService {

    @Headers({"content-type: application/json","accept: application/json"})
    @POST("api-token-auth/")
    Call<Token> getToken(
            @Field("username") String username,
            @Field("password") String password);

    @Headers({"content-type: application/json","accept: application/json"})
    @POST("api-token-auth/")
    Call<Token> getToken1(@Body HashMap<String, Object> body);

    @Headers({"content-type: application/json"})
    @GET("api/user/{id}/")
    Call<User> getUser(@Header("Authorization") String authorization, @Path("id") int id);

    @Headers({"content-type: application/json"})
    @GET("api/user/{username}/")
    Call<User> getUserDetail(@Header("Authorization") String authorization, @Path("username") String username);

    @Headers({"content-type: application/json"})
    @POST("api/images/create/")
    Call<Image> postImage(@Header("Authorization") String authorization, @Body HashMap<String, Object> body);

    @Headers({"content-type: application/json"})
    @POST("api/publications/")
    Call<Publication> postPublication(@Header("Authorization") String authorization, @Body HashMap<String, Object> body);

    @GET("api/publications/")
    Call<List<Publication>> getAllPublications(@Header("Authorization") String authorization);

    @GET("api/images/")
    Call<List<Image>> getAllImages(@Header("Authorization") String authorization);

    @GET("api/images/{id}")
    Call<Image> getInformationImage(@Header("Authorization") String authorization, @Path("id") int id);

    @GET("api/images/user/{userId}")
    Call<List<Image>> getAllImagesByUser(@Header("Authorization") String authorization, @Path("userId") int userId);

    @Headers({"content-type: application/json"})
    @POST("api/users/")
    Call<User> createUser(@Body HashMap<String, Object> body);


}
