package com.company.Models;

public class Customer {

    private int id;
    private String firstName;
    private String lastName;
    private String city;
    private String adress;

    public Customer(int id, String firstName, String lastName, String city, String adress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.adress = adress;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public String getAdress() {
        return adress;
    }
}
