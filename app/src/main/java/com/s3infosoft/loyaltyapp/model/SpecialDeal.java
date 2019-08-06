package com.s3infosoft.loyaltyapp.model;

import java.util.List;

public class SpecialDeal {
    String name;
    String description;
    List<String> images_urls;
    String hotelid;
    int points;

    public SpecialDeal()
    {

    }

    public SpecialDeal(String name, String description, List<String> images_urls, String hotelid, int points)
    {
        this.name = name;
        this.description = description;
        this.images_urls = images_urls;
        this.hotelid = hotelid;
        this.points = points;
    }

    public String getHotelid() {
        return hotelid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getImages_urls() {
        return images_urls;
    }

    public int getPoints() {
        return points;
    }
}
