package com.company.Models;

public class Ingredient {

    private int id;
    private String type;
    private double price;

    public Ingredient(int id, String type, double price) {
        this.id = id;
        this.type = type;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }
}
