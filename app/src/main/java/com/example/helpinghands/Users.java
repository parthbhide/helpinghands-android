package com.example.helpinghands;

public class Users {

    private String firstname;
    private String lastname;
    private String email;
    private String address;
    private String contact;
    private String usertype;

    public Users(){

    }

    public Users(String firstname,String lastname,String email,String address, String contact, String usertype)
    {
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.usertype = usertype;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}
