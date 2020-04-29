package com.company.Models;

public class Location {

    private int id;
    private String city;
    private double price;

    public Location(int id, String city, double price) {
        this.id = id;
        this.city = city;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public double getPrice() {
        return price;
    }
}
