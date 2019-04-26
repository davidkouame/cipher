package com.dks.cipher.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Publication implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("souscategorie")
    private int sousCategorieId;
    @SerializedName("user")
    private int userId;
    @SerializedName("image")
    private int imageId;

    public Publication() {
    }

    public Publication(int id, int sousCategorieId, int userId, int imageId) {
        this.id = id;
        this.sousCategorieId = sousCategorieId;
        this.userId = userId;
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSousCategorieId() {
        return sousCategorieId;
    }

    public void setSousCategorieId(int sousCategorieId) {
        this.sousCategorieId = sousCategorieId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
