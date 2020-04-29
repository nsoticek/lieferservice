package com.company.DbHelper;

import com.company.Models.Customer;
import com.company.Models.RegisterPerson;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterDb {

    public Customer getCustomer(RegisterPerson registerPerson, DbConnector dbConnector) {
        Customer customer = null;

        ResultSet rs = dbConnector.fetchData("SELECT `id` FROM `customer` " +
                "WHERE first_name = '" + registerPerson.getFirstName() + "' AND last_name = '" + registerPerson.getLastName() + "' " +
                "AND city = '" + registerPerson.getCity() + "' AND adress = '" + registerPerson.getAdress() + "'");
        if (rs == null) {
            System.out.println("Error bei getId! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                int id = rs.getInt("id");

                customer = new Customer(id, registerPerson.getFirstName(), registerPerson.getLastName(),
                        registerPerson.getCity(), registerPerson.getAdress());
            }
        } catch (SQLException e) {
            System.out.println("Error bei fetchUser!");
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
        return customer;
    }

    public static void insertCustomer(RegisterPerson registerPerson, DbConnector dbConnector){
        boolean isInserted = dbConnector.insertData("INSERT INTO `customer`(`password`, `first_name`, `last_name`, `city`, `adress`) " +
                "VALUES ('" + registerPerson.getPassword() + "', '" + registerPerson.getFirstName() + "', '" +
                registerPerson.getLastName() + "', '" + registerPerson.getCity() + "', '" + registerPerson.getAdress() + "' )");
        if(isInserted) {
            System.out.println("Daten wurden aktualisiert");
        }
    }
}
