package com.s3infosoft.loyaltyapp.model;

public class Order {
    String desc;
    int points;

    public Order()
    {

    }

    public Order(String desc, int points)
    {
        this.desc = desc;
        this.points = points;
    }

    public String getDesc() {
        return desc;
    }

    public int getPoints() {
        return points;
    }
}
