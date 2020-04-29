package com.company.DbHelper;

import com.company.Models.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDb {

    private ArrayList<Customer> customers = new ArrayList<>();

    public ArrayList<Customer> getCustomer(DbConnector dbConnector) {
        if (customers.isEmpty()) {
            fetchCustomer(dbConnector);
        }
        return customers;
    }

    public void clearCustomers() {
        customers.clear();
    }

    private void fetchCustomer(DbConnector dbConnector) {
        // delete all old entries
        customers.clear();

        ResultSet rs = dbConnector.fetchData("SELECT * FROM customer");
        if (rs == null) {
            System.out.println("Error bei fetchCustomer! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String city = rs.getString("city");
                String adress = rs.getString("adress");

                customers.add(new Customer(id, firstName, lastName, city, adress));
            }
        } catch (SQLException e) {
            System.out.println("Error bei fetchUser!");
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
    }
}