package com.dks.cipher.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Token implements Serializable {

    @SerializedName("expires_in")
    private Long expires_in;
    @SerializedName("refresh_token")
    private String refresh_token;
    @SerializedName("access_token")
    private String access_token;
    @SerializedName("token_type")
    private String token_type;

    @SerializedName("token")
    private String token;
    @SerializedName("scope")
    private String scope;

    public Token(Long expires_in, String refresh_token, String access_token, String token_type, String scope) {
        this.expires_in = expires_in;
        this.refresh_token = refresh_token;
        this.access_token = access_token;
        this.token_type = token_type;
        this.scope = scope;
    }

    public Token(String token){
        this.token = token;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}