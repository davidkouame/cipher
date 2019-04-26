package com.dks.cipher.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.dks.cipher.model.Token;
import com.dks.cipher.model.User;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "CipherSession";

    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    private static final String TOKEN_TOKEN = "token";

    private static final String USER_ID = "user_id";

    private static final String USER_USERNAME = "username";

    private static final String USER_EMAIL = "email";

    private static final String USER_FIRST_NAME = "first_name";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    // setters
    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        // commit changes
        editor.commit();
    }

    public void setToken(Token token){
        editor.putString(TOKEN_TOKEN, token.getToken());
        editor.commit();
    }

    public void setUser(User user){
        editor.putInt(USER_ID, user.getId());
        editor.putString(USER_USERNAME, user.getUsername());
        editor.putString(USER_EMAIL, user.getEmail());
        editor.putString(USER_FIRST_NAME, user.getFirst_name());
        editor.commit();
    }

    // getters
    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public Token getToken(){
        String token = pref.getString(TOKEN_TOKEN, null);
        return new Token(token);
    }

    public User getUser(){
        Log.d("userId", "l'id de l'utilisateur est "+pref.getInt(USER_ID, 0));
        if(pref.getInt(USER_ID, 0)==0){
            return null;
        }else{
            int user_id = pref.getInt(USER_ID, 0);
            String username = pref.getString(USER_USERNAME, null);
            String email = pref.getString(USER_EMAIL, null);
            String first_name = pref.getString(USER_FIRST_NAME, null);
            return new User(user_id, username, email, first_name);
        }
    }

    public void remove(){
        editor.clear();
        editor.commit();
    }
}
