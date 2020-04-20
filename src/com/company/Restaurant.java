package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Restaurant {

    private String name;

    public Restaurant(String name) {
        this.name = name;
    }

    public void showDishcard() {
        String sqlCommand = "SELECT `id`, `name`, `price` FROM `dish`";
        printDishcard(sqlCommand);
    }

    public void printAllIngredients() {
        String sqlCommand = "SELECT `id`, `name`, `extraprice` FROM `ingredient`";
        getAndPrintAllIngredients(sqlCommand);
    }

    public boolean checkIfLocationAvailable(Customer customer) {
        String sqlCommand = "SELECT `id` FROM `location` WHERE `name` = '" + customer.getCity() + "'";
        boolean isLoactionExisting = isExisting(sqlCommand);
        return isLoactionExisting;
    }

    private void printDishcard(String sqlCommand) {
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                System.out.println(id + ". " + name + "\t" + price);
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

    private boolean isExisting(String sqlCommand) {
        boolean isLocationExisting = false;
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                Integer id = rs.getInt("id");
                if(id != null)
                    isLocationExisting = true;
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
        return isLocationExisting;
    }

    private void getAndPrintAllIngredients(String sqlCommand) {
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double extraPrice = rs.getDouble("extraprice");

                System.out.println(id + ". " + name + " " + extraPrice);
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
}
