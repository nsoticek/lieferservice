package com.company.DbHelper;

import com.company.Models.Location;
import com.company.Models.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationDb {

    public static Location getLocation(Customer customer, DbConnector dbConnector) {
        Location location = null;

        ResultSet rs = dbConnector.fetchData("SELECT location.id, location.name, delivery_price.price FROM delivery_price " +
                "INNER JOIN location ON delivery_price.location = location.id WHERE location.name = '" + customer.getCity() + "'");
        if (rs == null) {
            System.out.println("Error bei getLocation! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                int locationId = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");

                location = new Location(locationId, name, price);
            }
        } catch (SQLException e) {
            System.out.println("Error bei fetchUser!");
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
        return location;
    }
}
