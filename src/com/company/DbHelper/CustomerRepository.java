package com.company.DbHelper;

import com.company.Models.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements IRepository<Customer>{
    private DbConnector connector;

    public CustomerRepository() {
        this.connector = DbConnector.getInstance();
    }

    @Override
    public List<Customer> findAll() {
        ArrayList<Customer> customers = new ArrayList<>();

        ResultSet rs = connector.fetchData("SELECT * FROM customer");
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
            connector.closeConnection();
        }
        return customers;
    }

    @Override
    public Customer findOne(int id) {
        return null;
    }

    @Override
    public boolean create(Customer entity) {
        return false;
    }
}