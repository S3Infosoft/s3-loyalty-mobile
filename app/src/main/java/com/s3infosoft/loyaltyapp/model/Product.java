package com.s3infosoft.loyaltyapp.model;

public class Product {
    String name;
    String desc;
    String logo_url;
    int points;

    public Product()
    {
        
    }

    public Product(String name, String desc, String logo_url, int points)
    {
        this.name = name;
        this.desc = desc;
        this.logo_url = logo_url;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public int getPoints() {
        return points;
    }
}
