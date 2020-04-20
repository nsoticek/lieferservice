package com.company;

public class Location {

    private String city;
    private double price;

    public Location(String city, double price) {
        this.city = city;
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public double getPrice() {
        return price;
    }
}
