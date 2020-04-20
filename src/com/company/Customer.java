package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Customer {

    private int id;
    private String firstName;
    private String lastName;
    private String city;
    private String adress;
    private Location location;

    public Customer(int id) {
        this.id = id;
        initCustomer();
        addLocation();
    }

    public Customer(String firstName, String lastName, String placeOfDelivery, String adress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = placeOfDelivery;
        this.adress = adress;
        addLocation();
    }

    private void initCustomer() {
        String sqlCommand = "SELECT `first_name`, `last_name`, `city`, `adress` FROM `customer` WHERE `id` = " + this.id;
        setDataToCustomer(sqlCommand);

    }

    public boolean login() {
        boolean isExisting = false;
        String sqlCommand = "SELECT `id` FROM `customer` WHERE `id` = '" + this.id + "'";
        Integer idFromDb = getIdFromDb(sqlCommand);
        if (idFromDb != null)
            isExisting = true;
        return isExisting;
    }

    public void register() {
        // Insert customer to customer table in DB
        String sqlCommand = "INSERT INTO `customer`(`first_name`, `last_name`, `city`, `adress`) " +
                "VALUES ('" + this.firstName + "', '" + this.lastName + "', '" + this.city + "', '" + this.adress + "')";
        executeUpdate(sqlCommand);

        // Set the id of the current user to this.id
        this.id = getIdFromDb("SELECT `id` FROM `customer` " +
                "WHERE `first_name` = '" + this.firstName + "' AND `last_name` = '" + this.lastName + "' " +
                "AND `city` = '" + this.city + "' AND adress = '" + this.adress + "';");
    }

    public void addLocation() {
        Location location = new Location(city);
        this.location = location;
    }

    public void printOrders() {
        // Get all orders from the current user and print it
        String sqlCommand = "SELECT `id`, `customer`, `total_price`, `delivery_cost`, `date` FROM `order_table`";
        getAndPrintOrders(sqlCommand);
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

    private int getIdFromDb(String sqlCommand) {
        int id = 0;
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                id = rs.getInt("id");
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
        return id;
    }

    private void setDataToCustomer(String sqlCommand) {
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String city = rs.getString("city");
                String adress = rs.getString("adress");

                this.firstName = firstName;
                this.lastName = lastName;
                this.city = city;
                this.adress = adress;
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

    private void getAndPrintOrders(String sqlCommand) {
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                int id = rs.getInt("id");
                double totalAmount = rs.getInt("total_price");
                Date date = rs.getDate("date");

                System.out.println(id + ". " + totalAmount + date);
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

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public String getAdress() {
        return adress;
    }

    public Location getLocation() {
        return location;
    }
}
