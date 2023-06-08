package com.ug.air.sproutofinnovateapp.Models;

public class Appraisal {

    String name;
    String date;

    public Appraisal(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
