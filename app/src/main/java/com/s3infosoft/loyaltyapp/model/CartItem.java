package com.s3infosoft.loyaltyapp.model;

public class CartItem {

    String item_name;
    String item_id;
    String item_desc;
    String item_logo_url;
    int quantity;
    int amount;

    public CartItem()
    {

    }

    public CartItem(String item_name, String item_id, String item_desc, String item_logo_url, int quantity, int amount) {
        this.item_name = item_name;
        this.item_id = item_id;
        this.item_desc = item_desc;
        this.item_logo_url = item_logo_url;
        this.quantity = quantity;
        this.amount = amount;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    public String getItem_logo_url() {
        return item_logo_url;
    }

    public void setItem_logo_url(String item_logo_url) {
        this.item_logo_url = item_logo_url;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
