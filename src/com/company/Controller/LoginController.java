package com.company.Controller;

import com.company.Models.Customer;
import com.company.DbHelper.LoginRepository;
import com.company.Models.LoginPerson;

import java.util.Scanner;

public class LoginController {

    public static Customer executeLogin() {
        LoginRepository loginRepository = new LoginRepository();
        LoginPerson loginPerson;
        Customer customer = null;
        boolean isLoggedIn = false;

        while (!isLoggedIn) {
            // Get userInput and create a loginPerson;
            loginPerson = getUserInputAndCreateLoginPerson();
            // Check if user exists in DB and check if password is matching
            isLoggedIn = LoginController.isUserExisting(loginPerson, loginRepository);
            // Get all attributs from DB and create Customer with all attributs
            customer = loginRepository.getPersonFromDb(loginPerson);
        }
        return customer;
    }

    public static boolean isUserExisting(LoginPerson loginPerson, LoginRepository loginRepository) {
        // Check in Db if user exists; if yes - return true in 'isPersonExisting'
        boolean isPersonExisting = loginRepository.isExisting(loginPerson);
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
        return scanner.nextLine();
    }
}
