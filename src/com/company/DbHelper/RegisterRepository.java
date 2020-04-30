package com.company.DbHelper;

import com.company.Models.RegisterPerson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RegisterRepository implements IRepository<RegisterPerson>{
    private DbConnector connector;

    public RegisterRepository() {
        this.connector = DbConnector.getInstance();
    }

    @Override
    public List<RegisterPerson> findAll() {
        return null;
    }

    @Override
    public RegisterPerson findOne(int id) {
        return null;
    }

    @Override
    public boolean create(RegisterPerson registerPerson) {
        boolean isInserted = connector.insertData("INSERT INTO `customer`(`password`, `first_name`, `last_name`, `city`, `adress`) " +
                "VALUES ('" + registerPerson.getPassword() + "', '" + registerPerson.getFirstName() + "', '" +
                registerPerson.getLastName() + "', '" + registerPerson.getCity() + "', '" + registerPerson.getAdress() + "' )");
        if(isInserted) {
            System.out.println("Daten wurden aktualisiert");
        }
        return isInserted;
    }

    public int getCustomer(RegisterPerson registerPerson) {
        int id = 0;

        ResultSet rs = connector.fetchData("SELECT `id` FROM `customer` " +
                "WHERE first_name = '" + registerPerson.getFirstName() + "' AND last_name = '" + registerPerson.getLastName() + "' " +
                "AND city = '" + registerPerson.getCity() + "' AND adress = '" + registerPerson.getAdress() + "'");
        if (rs == null) {
            System.out.println("Error bei getId! Konnte keine Daten abrufen.");
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
