package com.s3infosoft.loyaltyapp.model;

public class ReservationHistory {

    String hotel_name;
    String hotel_id;
    int amount;
    String date;

    public ReservationHistory()
    {

    }

    public ReservationHistory(String hotel_name, String hotel_id, int amount, String date) {
        this.hotel_name = hotel_name;
        this.hotel_id = hotel_id;
        this.amount = amount;
        this.date = date;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public int getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
