package org.example;

import java.util.StringJoiner;

public class Customer extends Person implements ICustomer {

    private String email;
    private String phoneNumber;

    public Customer(String name, String email, String phoneNumber, boolean allowNull) throws Exception {
        setName(name, allowNull);
        setEmail(email, allowNull);
        setPhoneNumber(phoneNumber, allowNull);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email, boolean allowNull) throws Exception {

        if (allowNull && (email == null || email.isEmpty()))
            this.email = null;
        else if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
            this.email = email;
        else
            throw new Exception("Invalid email");
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber, boolean allowNull) throws Exception {
        if (allowNull && (phoneNumber == null || phoneNumber.isEmpty()))
            this.phoneNumber = null;
        else if (phoneNumber.matches("^[0-9]{10}$"))
            this.phoneNumber = phoneNumber;
        else
            throw new Exception("Invalid phone number");
    }

    @Override
    public boolean updateInfo() {
        return false;
    }

    @Override
    public void showInfo() {
        System.out.println("Tên: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("SĐT: " + getPhoneNumber());
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
