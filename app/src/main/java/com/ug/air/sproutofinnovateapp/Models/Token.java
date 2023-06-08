package com.ug.air.sproutofinnovateapp.Models;

public class Token {

    String token;
    String name;

    public Token(String token, String name) {
        this.token = token;
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }
}
