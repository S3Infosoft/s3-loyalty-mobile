package com.icy.chatscreen;

public class GetInfoFIre {

    private String firstname;
    private String city;
    private String email;
    private String lastname;
    private String phonenum;
  public  GetInfoFIre(){

  }

    public String getCity() {
        return city;
    }

    public String getEmail() {

        return email;
    }


    public String getPhonenum() {
        return phonenum;
    }



    public void setCity(String city) {
        this.city = city;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }
}
