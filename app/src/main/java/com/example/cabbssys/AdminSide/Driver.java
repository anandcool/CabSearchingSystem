package com.example.cabbssys.AdminSide;

public class Driver{

    String id, name,licensenumber,email,phone,address;

    public Driver(String id, String name, String licensenumber, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.licensenumber = licensenumber;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
    public Driver(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicensenumber() {
        return licensenumber;
    }

    public void setLicensenumber(String licensenumber) {
        this.licensenumber = licensenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
