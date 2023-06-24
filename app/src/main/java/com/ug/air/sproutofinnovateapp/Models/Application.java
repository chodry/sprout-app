package com.ug.air.sproutofinnovateapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Application {

    int id;
    int amount;
    int duration_of_payment;
    String collateral;
    String source_of_income;
    int income;
    String weekly_or_monthly;
    String guarantor;
    int interest;
    String guarantor_telephone_number;
    String guarantor_relationship;
    String time_line;

    @SerializedName("applicant")
    Applicant applicant;

    @SerializedName("location")
    Location location;

    public Application(int id, int amount, int duration_of_payment, String collateral, String source_of_income, int income, String weekly_or_monthly, String guarantor, int interest, String guarantor_telephone_number, String guarantor_relationship, String time_line, Applicant applicant, Location location) {
        this.id = id;
        this.amount = amount;
        this.duration_of_payment = duration_of_payment;
        this.collateral = collateral;
        this.source_of_income = source_of_income;
        this.income = income;
        this.weekly_or_monthly = weekly_or_monthly;
        this.guarantor = guarantor;
        this.interest = interest;
        this.guarantor_telephone_number = guarantor_telephone_number;
        this.guarantor_relationship = guarantor_relationship;
        this.time_line = time_line;
        this.applicant = applicant;
        this.location = location;
    }

    public int getId() {
        return id;
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

    public int getIncome() {
        return income;
    }

    public String getWeekly_or_monthly() {
        return weekly_or_monthly;
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

    public Applicant getApplicant() {
        return applicant;
    }

    public Location getLocation() {
        return location;
    }
}

