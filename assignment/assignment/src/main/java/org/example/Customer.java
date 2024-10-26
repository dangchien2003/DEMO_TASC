package org.example;

import java.util.StringJoiner;

public class Customer {

    private String email;
    private String phoneNumber;
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Customer(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner("-");
        stringJoiner.add(getName());
        stringJoiner.add(getEmail());
        stringJoiner.add(getPhoneNumber());
        return stringJoiner.toString();
    }

}
