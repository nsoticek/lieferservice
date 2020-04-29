package com.company.Models;

import java.util.Date;

public class FullOrder {

    private int id;
    private Customer customer;
    private double totalPrice;
    private double deliveryCost;
    private Date date;

    public FullOrder(int id, Customer customer, double totalPrice, double deliveryCost, Date date) {
        this.id = id;
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.deliveryCost = deliveryCost;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public Date getDate() {
        return date;
    }
}
