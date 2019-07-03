package com.s3infosoft.loyaltyapp.model;

public class Metadata {
    String a;
    String b;
    String c;

    public Metadata()
    {

    }

    public Metadata(String created_at, String created_by, String modified_at)
    {
        this.a = created_at;
        this.b = created_by;
        this.c = modified_at;
    }
}
