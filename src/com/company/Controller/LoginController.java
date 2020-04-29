package com.company.Controller;

import com.company.Models.Customer;
import com.company.DbHelper.DbConnector;
import com.company.DbHelper.LoginDb;
import com.company.Models.LoginPerson;

import java.util.Scanner;

public class LoginController {

    public static Customer executeLogin(DbConnector dbConnector, LoginDb loginDb, Customer customer) {
        LoginPerson loginPerson;
        boolean isLoggedIn = false;

        while (!isLoggedIn) {
            // Get userInput and create a loginPerson;
            loginPerson = getUserInputAndCreateLoginPerson();
            // Check if user exists in DB and check if password is matching
            isLoggedIn = LoginController.isUserExisting(loginPerson, dbConnector);
            // Get all attributs from DB and create Customer with all attributs
            customer = loginDb.getPersonFromDb(loginPerson, dbConnector);
        }
        return customer;
    }

    public static boolean isUserExisting(LoginPerson loginPerson, DbConnector dbConnector) {
        // Check in Db if user exists; if yes - return true in 'isPersonExisting'
        boolean isPersonExisting = LoginDb.isExisting(loginPerson, dbConnector);
        if (isPersonExisting)
            System.out.println("Erfolgreich eingeloggt");
        else
            System.out.println("Da stimmt was nicht! Probier es nochmal.\n");
        return isPersonExisting;
    }

    private static LoginPerson getUserInputAndCreateLoginPerson() {
        int id = Integer.parseInt(inputUser("Kundennr.: "));
        String password = inputUser("Password: ");
        return new LoginPerson(id, password);
    }

    private static String inputUser(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        String userInput = scanner.nextLine();
        return userInput;
    }
}
