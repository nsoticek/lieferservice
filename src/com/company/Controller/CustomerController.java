package com.company.Controller;

import com.company.DbHelper.DbConnector;
import com.company.DbHelper.OrderDb;
import com.company.Models.Customer;
import com.company.Models.FullOrder;

import java.util.ArrayList;

public class CustomerController {

    public static void printOrders(OrderDb orderDb, Customer customer, DbConnector dbConnector) {
        // Get all orders from the current user and print them
        ArrayList<FullOrder> ordersArr = orderDb.getOrders(customer, dbConnector);

        for (int i = 0; i < ordersArr.size(); i++) {
            // Print all orders of current user
            System.out.println(ordersArr.get(i).getId() + ". Preis: " + ordersArr.get(i).getTotalPrice() + " Lieferkosten: " +
                    ordersArr.get(i).getDeliveryCost() + " " + ordersArr.get(i).getDate());
        }
    }
}
