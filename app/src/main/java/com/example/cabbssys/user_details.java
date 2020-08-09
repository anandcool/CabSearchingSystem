package com.example.cabbssys;

public class user_details
{
    String name,email,number,s1no,s2no,s3no;

    public user_details(String name,String number,String email,String s1no, String s2no, String s3no)
    {
        this.name=name;
        this.number=number;
        this.email=email;
        this.s1no=s1no;
        this.s2no=s2no;
        this.s3no=s3no;
    }
    public user_details(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getS1no() {
        return s1no;
    }

    public void setS1no(String s1no) {
        this.s1no = s1no;
    }

    public String getS2no() {
        return s2no;
    }

    public void setS2no(String s2no) {
        this.s2no = s2no;
    }

    public String getS3no() {
        return s3no;
    }

    public void setS3no(String s3no) {
        this.s3no = s3no;
    }
}

