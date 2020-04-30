package com.company.DbHelper;

import com.company.Models.Customer;
import com.company.Models.FullOrder;
import com.company.Models.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderRepository implements IRepository<Order>{
    private DbConnector connector;

    public OrderRepository() {
        this.connector = DbConnector.getInstance();
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Order findOne(int id) {
        return null;
    }

    @Override
    public boolean create(Order order) {
        boolean isInserted = connector.insertData("INSERT INTO `order_table`(`customer`, `total_price`, `delivery_cost`) " +
                "VALUES ('" + order.getCustomer().getId() + "', " + order.getTotalPrice() + ", " + order.getLocation().getPrice() + ")");
        if (isInserted) {
            System.out.println("Bestellung wurde hinzugefügt");
        }
        return isInserted;
    }

    public ArrayList<FullOrder> getOrdersOfCustomer(Customer customer) {
        ArrayList<FullOrder> orders = new ArrayList<>();

        ResultSet rs = connector.fetchData("SELECT * FROM `order_table` WHERE customer = '" + customer.getId() + "'");
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
            connector.closeConnection();
        }
        return orders;
    }

    public int getId(Order order) {
        // Get ID of current order
        int id = 0;
        ResultSet rs = connector.fetchData("SELECT `id` FROM `order_table` " +
                "WHERE customer = " + order.getCustomer().getId() + " ORDER BY id DESC LIMIT 1");
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
            connector.closeConnection();
        }
        return id;
    }
}
