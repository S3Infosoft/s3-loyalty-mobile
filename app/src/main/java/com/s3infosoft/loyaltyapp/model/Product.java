package com.s3infosoft.loyaltyapp.model;

public class Product {
    String name;
    String desc;
    String logo_url;

    public Product()
    {

    }

    public Product(String name, String desc, String logo_url)
    {
        this.name = name;
        this.desc = desc;
        this.logo_url = logo_url;
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
}
