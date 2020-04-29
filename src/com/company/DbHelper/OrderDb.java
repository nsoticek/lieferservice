package com.company.DbHelper;

import com.company.Models.Dish;
import com.company.Models.Customer;
import com.company.Models.FullOrder;
import com.company.Models.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class OrderDb {

    private ArrayList<FullOrder> orders = new ArrayList<>();

    public ArrayList<FullOrder> getOrders(Customer customer, DbConnector dbConnector) {
        if (orders.isEmpty()) {
            fetchOrders(customer, dbConnector);
        }
        return orders;
    }

    private void fetchOrders(Customer customer, DbConnector dbConnector) {
        // delete all old entries
        orders.clear();

        ResultSet rs = dbConnector.fetchData("SELECT * FROM `order_table` WHERE customer = '" + customer.getId() + "'");
        if (rs == null) {
            System.out.println("Error bei fetchOrders! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                double totalPrice = rs.getDouble("total_price");
                double deliveryCost = rs.getDouble("delivery_cost");
                Date date = rs.getDate("date");

                orders.add(new FullOrder(id, customer, totalPrice, deliveryCost, date));
            }
        } catch (SQLException e) {
            System.out.println("Error bei fetchUser!");
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
    }

    public void insertOrder(Order order, DbConnector dbConnector) {
        // Insert order in order_table --> DB
        boolean isInserted = dbConnector.insertData("INSERT INTO `order_table`(`customer`, `total_price`, `delivery_cost`) " +
                "VALUES ('" + order.getCustomer().getId() + "', " + order.getTotalPrice() + ", " + order.getLocation().getPrice() + ")");
        if (isInserted) {
            System.out.println("Bestellung wurde hinzugefügt");
        }
    }

    public void insertDishAndIngredient(Order order, DbConnector dbConnector) {
        // Insert the orderId & dishId & ingredientId in order_details_change --> DB
        ArrayList<Dish> dishes = order.getDishes();
        boolean isInserted = false;
        int orderId = getId(order, dbConnector);

        for (int i = 0; i < dishes.size(); i++) {
            for (int j = 0; j < order.getDishes().get(i).getIngredients().size(); j++) {
                isInserted = dbConnector.insertData("INSERT INTO `order_details_change`(`order_index`, `dish`, `ingredient`) " +
                        "VALUES (" + orderId + ", " + dishes.get(i).getId() + ", " +
                        dishes.get(i).getIngredients().get(j).getId() + ")");
            }
        }
        if (isInserted)
            System.out.println("Bestellung wurde hinzugefügt");
    }

    public int getId(Order order, DbConnector dbConnector) {
        // Get ID of current order
        int id = 0;
        ResultSet rs = dbConnector.fetchData("SELECT `id` " +
                "FROM `order_table` WHERE customer = " + order.getCustomer().getId() + " AND " +
                "total_price = " + order.getTotalPrice());
        if (rs == null) {
            System.out.println("Error bei getId / Orderdb! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                id = rs.getInt("id");

            }
        } catch (SQLException e) {
            System.out.println("Error bei fetchUser!");
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
        return id;
    }
}
