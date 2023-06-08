package com.ug.air.sproutofinnovateapp.Utils;

import static com.ug.air.sproutofinnovateapp.Activities.SplashActivity.NAME;
import static com.ug.air.sproutofinnovateapp.Activities.SplashActivity.TOKEN;
import static com.ug.air.sproutofinnovateapp.Activities.SplashActivity.TOKEN_FILE;
import static com.ug.air.sproutofinnovateapp.Activities.SplashActivity.USERNAME;

import android.content.Context;
import android.content.SharedPreferences;

public class Token {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String token;
    String name;
    int number;

    public void createToken(Context context, String token, String username, String name){
        sharedPreferences = context.getSharedPreferences(TOKEN_FILE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(TOKEN, token);
        editor.putString(USERNAME, username);
        editor.putString(NAME, name);
        editor.apply();
    }

    public void logout(Context context) {
        sharedPreferences = context.getSharedPreferences(TOKEN_FILE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(TOKEN, "");
        editor.putString(USERNAME, "");
        editor.putString(NAME, "");
        editor.apply();
    }

    public String getName2(Context context){
        sharedPreferences = context.getSharedPreferences(TOKEN_FILE, Context.MODE_PRIVATE);
        name = sharedPreferences.getString(NAME, "");

        return name;
    }

    public void createCounter(Context context, String item){
        sharedPreferences = context.getSharedPreferences(TOKEN_FILE, Context.MODE_PRIVATE);
        number = sharedPreferences.getInt("counter", 0);
        number = number + 1;
        editor = sharedPreferences.edit();
        editor.putString("counter" + number, item);
        editor.putInt("counter", number);
        editor.apply();
    }

    public String getToken(Context context){
        sharedPreferences = context.getSharedPreferences(TOKEN_FILE, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(TOKEN, "");

        return token;
    }

    public String getName(Context context){
        sharedPreferences = context.getSharedPreferences(TOKEN_FILE, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(USERNAME, "");

        return token;
    }

}
