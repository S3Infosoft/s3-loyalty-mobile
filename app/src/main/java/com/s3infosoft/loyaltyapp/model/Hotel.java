package com.s3infosoft.loyaltyapp.model;

import java.util.List;

public class Hotel {
    long phone_no;
    String id;
    List<String> image_urls;
    String name;
    String address;
    String email;
    String logo_url;
    String manager_name;
    String manager_email;
    String manager_phone_no;

    public Hotel()
    {

    }

    public Hotel(String name, String address, String manager_email, String email,Metadata metadata,
          String logo_url, String manager_name, List<String> image_urls) {
        this.name = name;
        this.address = address;
        this.image_urls = image_urls;
        this.logo_url = logo_url;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getImage_urls() {
        return image_urls;
    }

    public String getLogo_url() {
        return logo_url;
    }
}
