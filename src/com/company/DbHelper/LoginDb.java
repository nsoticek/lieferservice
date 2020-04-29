package com.company.DbHelper;

import com.company.Models.Customer;
import com.company.Models.LoginPerson;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDb {

    public static boolean isExisting(LoginPerson loginPerson, DbConnector dbConnector) {
        // Check if username and password are matching with DB data, if yes return true;
        boolean isPersonExisting = false;
        ResultSet rs = dbConnector.fetchData("SELECT id FROM customer " +
                "WHERE id = " + loginPerson.getId() + " AND password = '" + loginPerson.getPassword() + "'");
        if (rs == null) {
            System.out.println("Error bei loginAbfrage! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                Integer id = rs.getInt("id");
                if (id != null)
                    isPersonExisting = true;
            }
        } catch (SQLException e) {
            System.out.println("Error bei login!");
        } finally {
            dbConnector.closeConnection();
        }
        return isPersonExisting;
    }

    public Customer getPersonFromDb(LoginPerson loginPerson, DbConnector dbConnector) {
        Customer customer = null;

        ResultSet rs = dbConnector.fetchData("SELECT * FROM customer " +
                "WHERE id = " + loginPerson.getId() + " AND password = '" + loginPerson.getPassword() + "'");
        if (rs == null) {
            System.out.println("Error bei getPersonFromDb! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String city = rs.getString("city");
                String adress = rs.getString("adress");

                customer = new Customer(id, firstName, lastName, city, adress);
            }
        } catch (SQLException e) {
            System.out.println("Error bei fetchUser!");
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
        return customer;
    }
}
