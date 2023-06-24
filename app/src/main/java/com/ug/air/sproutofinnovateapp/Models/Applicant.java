package com.ug.air.sproutofinnovateapp.Models;

public class Applicant {

    String first_name;
    String last_name;
    int age;
    String gender;
    String telephone_number_1;
    String telephone_number_2;

    public Applicant(String first_name, String last_name, int age, String gender, String telephone_number_1, String telephone_number_2) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.gender = gender;
        this.telephone_number_1 = telephone_number_1;
        this.telephone_number_2 = telephone_number_2;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getTelephone_number_1() {
        return telephone_number_1;
    }

    public String getTelephone_number_2() {
        return telephone_number_2;
    }
}
