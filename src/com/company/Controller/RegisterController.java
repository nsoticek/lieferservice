package com.company.Controller;

import com.company.Models.Customer;
import com.company.DbHelper.CustomerDb;
import com.company.DbHelper.DbConnector;
import com.company.DbHelper.RegisterDb;
import com.company.Models.RegisterPerson;

import java.util.Scanner;

public class RegisterController {

    public static Customer executeRegister(DbConnector dbConnector, RegisterDb registerDb, CustomerDb customerDb) {
        RegisterPerson registerPerson;

        registerPerson = getUserInputAndCreateRegisterPerson();
        RegisterDb.insertCustomer(registerPerson, dbConnector);
        customerDb.clearCustomers();
        // TODO check if city is existing in DB

        return registerDb.getCustomer(registerPerson, dbConnector);
    }

    private static RegisterPerson getUserInputAndCreateRegisterPerson() {
        String firstName = inputUser("Vorname: ");
        String lastName = inputUser("Nachname: ");
        String city = inputUser("Wohnort: ");
        String adress = inputUser("Adresse: ");
        String password = inputUser("Password festlegen: ");

        RegisterPerson registerPerson = new RegisterPerson(firstName, lastName, city, adress, password);
        System.out.println("Kundenkonto angelegt.");
        return registerPerson;
    }

    private static String inputUser(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        String userInput = scanner.nextLine();
        return userInput;
    }
}
