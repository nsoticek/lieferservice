package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Order {

    private Customer newCostumer;
    private int locationId;
    ArrayList<Dish> dishes = new ArrayList<Dish>();
    private double totalPrice;

    public Order(Customer newCostumer) {
        this.newCostumer = newCostumer;
        setLocationid();
    }

    public void addDish(Dish dish) {
        this.dishes.add(dish);
        this.totalPrice += dish.getPrice();
    }

    public void addOrderToDb() {
        // Insert new order in order_table -> DB
        String sqlCommand = "INSERT INTO `order_table`(`customer`, `total_price`, `delivery_cost`) " +
                "VALUES ('" + newCostumer.getId() + "', " + calculateTotalAmount() + ", " + this.locationId + ")";
        executeUpdate(sqlCommand);

        // Select the id of the current order -> is required for the next command
        String sqlCommandGetOrderId = "SELECT `id` FROM `order_table` ORDER BY `id` DESC LIMIT 1";
        int orderId = getOrderIdFromDb(sqlCommandGetOrderId);

        // Insert the orderId & the dishId in order_details -> Db
        for (int i = 0; i < dishes.size(); i++) {
            String sqlCommandInsertOrderDetails = "INSERT INTO `order_details`(`order_index`, `dish`) " +
                    "VALUES (" + orderId + ", " + dishes.get(i).getId() + ")";
            executeUpdate(sqlCommandInsertOrderDetails);
        }

        // Insert the orderId & dishId & ingredient in order_details_change -> Db
        for (int i = 0; i < dishes.size(); i++) {
            for (int j = 0; j < dishes.get(i).getIngredients().size(); j++) {
                String sqlCommandInsertOrderDetails = "INSERT INTO `order_details_change`(`order_index`, `dish`, `ingredient`) " +
                        "VALUES (" + orderId + ", " + dishes.get(i).getId() + ", " + dishes.get(i).getIngredients().get(j).getId() + ")";
                executeUpdate(sqlCommandInsertOrderDetails);
            }
        }
    }

    private void setLocationid() {
        // get the locationId of the current user from DB & set it to this.locationId in the setIdFromDb() method
        String sqlCommand = "SELECT `id` FROM `location` WHERE `name` = '" + newCostumer.getCity() + "'";
        setIdFromDb(sqlCommand);
    }

    private double calculateTotalAmount() {
        // Calculate the total amount of this Order
        double totalAmount = 0;
        for (int i = 0; i < dishes.size(); i++) {
            totalAmount += dishes.get(i).getPrice();
        }
        return totalAmount;
    }

    public void printSuccessMessage() {
        // Print successMessage after an order has been successfully placed in DB
        System.out.println("Bestellung erfolgreich ausgeführt!");
    }

    private void executeUpdate(String sqlCommand) {
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlCommand);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void setIdFromDb(String sqlCommand) {
        // Get and set this.locationId; This method is related to setLocationid()
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                int id = rs.getInt("id");
                this.locationId = id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private int getOrderIdFromDb(String sqlCommand) {
        int orderId = 0;
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                int id = rs.getInt("id");
                orderId = id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return orderId;
    }

    public Customer getNewCostumer() {
        return newCostumer;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
