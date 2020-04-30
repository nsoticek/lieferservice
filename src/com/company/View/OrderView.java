package com.company.View;

import com.company.Models.FullOrder;

import java.util.ArrayList;

public class OrderView {

    public void printOrders(ArrayList<FullOrder> fullOrders) {
        for (FullOrder fullOrder : fullOrders) {
            // Print all orders of current user
            System.out.println(fullOrder.getId() + ". Preis: " + fullOrder.getTotalPrice() + " Lieferkosten: " +
                    fullOrder.getDeliveryCost() + " " + fullOrder.getDate());
        }
    }
}
