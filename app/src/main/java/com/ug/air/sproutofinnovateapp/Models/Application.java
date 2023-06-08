package com.ug.air.sproutofinnovateapp.Models;

public class Application {

     int id;
     String first_name;
     String last_name;
     int age;
     String gender;
     String telephone_number_1;
     String telephone_number_2;
     String village;
     String subcounty;
     String district;
     int amount;
     int duration_of_payment;
     String collateral;
     String source_of_income;
     String guarantor;
     int interest;
     String guarantor_telephone_number;
     String guarantor_relationship;
     String time_line;

    public Application(int id, String first_name, String last_name, int age, String gender, String telephone_number_1, String telephone_number_2, String village, String subcounty, String district, int amount, int duration_of_payment, String collateral, String source_of_income, String guarantor, int interest, String guarantor_telephone_number, String guarantor_relationship, String time_line) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.gender = gender;
        this.telephone_number_1 = telephone_number_1;
        this.telephone_number_2 = telephone_number_2;
        this.village = village;
        this.subcounty = subcounty;
        this.district = district;
        this.amount = amount;
        this.duration_of_payment = duration_of_payment;
        this.collateral = collateral;
        this.source_of_income = source_of_income;
        this.guarantor = guarantor;
        this.interest = interest;
        this.guarantor_telephone_number = guarantor_telephone_number;
        this.guarantor_relationship = guarantor_relationship;
        this.time_line = time_line;
    }

    public int getId() {
        return id;
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

    public String getVillage() {
        return village;
    }

    public String getSubcounty() {
        return subcounty;
    }

    public String getDistrict() {
        return district;
    }

    public int getAmount() {
        return amount;
    }

    public int getDuration_of_payment() {
        return duration_of_payment;
    }

    public String getCollateral() {
        return collateral;
    }

    public String getSource_of_income() {
        return source_of_income;
    }

    public String getGuarantor() {
        return guarantor;
    }

    public int getInterest() {
        return interest;
    }

    public String getGuarantor_telephone_number() {
        return guarantor_telephone_number;
    }

    public String getGuarantor_relationship() {
        return guarantor_relationship;
    }

    public String getTime_line() {
        return time_line;
    }
}
