package com.company.Models;

import com.company.Models.Customer;
import com.company.Models.Dish;
import com.company.Models.Location;

import java.util.ArrayList;

public class Order {

    private Customer customer;
    private Location location;
    private double totalPrice;
    ArrayList<Dish> dishes = new ArrayList<Dish>();

    public Order(Customer customer) {
        this.customer = customer;
    }

    public void addDish(Dish dish) {
        this.totalPrice += dish.getPrice();
        this.dishes.add(dish);
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Location getLocation() {
        return location;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }
}
