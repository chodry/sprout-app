package com.ug.air.sproutofinnovateapp.Models;

public class Location {

    String village;
    String subcounty;
    String county;
    String parish;
    String district;
    String period_of_stay;
    String geo_point;
    String type;

    public Location(String village, String subcounty, String county, String parish, String district, String period_of_stay, String geo_point, String type) {
        this.village = village;
        this.subcounty = subcounty;
        this.county = county;
        this.parish = parish;
        this.district = district;
        this.period_of_stay = period_of_stay;
        this.geo_point = geo_point;
        this.type = type;
    }

    public String getVillage() {
        return village;
    }

    public String getSubcounty() {
        return subcounty;
    }

    public String getCounty() {
        return county;
    }

    public String getParish() {
        return parish;
    }

    public String getDistrict() {
        return district;
    }

    public String getPeriod_of_stay() {
        return period_of_stay;
    }

    public String getGeo_point() {
        return geo_point;
    }

    public String getType() {
        return type;
    }

    public void setGeo_point(String geo_point) {
        this.geo_point = geo_point;
    }
}
