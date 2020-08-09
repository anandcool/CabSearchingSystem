package com.example.cabbssys.AdminSide;

public class Cab {
    public String id;
    public String name;
    public String number;
    public String latitude;
    public String longitude;
    public String email;
    public String licensenumberplate;

    public Cab(String id,String name, String number, String email, String licensenumberplate, String latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.licensenumberplate = licensenumberplate;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public Cab()
    {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLicensenumberplate() {
        return licensenumberplate;
    }

    public void setLicensenumberplate(String licensenumberplate) {
        this.licensenumberplate = licensenumberplate;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}