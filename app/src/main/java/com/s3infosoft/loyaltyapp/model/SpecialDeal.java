package com.s3infosoft.loyaltyapp.model;

import java.util.List;

public class SpecialDeal {
    String name;
    String description;
    List<String> images_urls;
    int points;

    public SpecialDeal()
    {

    }

    public SpecialDeal(String name, String description, List<String> images_urls, int points)
    {
        this.name = name;
        this.description = description;
        this.images_urls = images_urls;
        this.points = points;
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
