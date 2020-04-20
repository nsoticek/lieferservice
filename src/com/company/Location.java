package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Location {

    private String city;
    private double price;

    public Location(String city) {
        this.city = city;
        initData();
    }

    private void initData() {
        String sqlCommand = "SELECT location.name, delivery_price.price FROM delivery_price " +
                "INNER JOIN location ON delivery_price.location = location.id WHERE location.name = '" + this.city + "'";
        getDataFromDb(sqlCommand);
    }

    private void getDataFromDb(String sqlCommand) {
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                double price = rs.getDouble("price");

                this.price = price;
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

    public String getCity() {
        return city;
    }

    public double getPrice() {
        return price;
    }
}
