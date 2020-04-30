package com.company.Controller;

import com.company.Models.Customer;
import com.company.DbHelper.RegisterRepository;
import com.company.Models.RegisterPerson;

import java.util.Scanner;

public class RegisterController {

    public static Customer executeRegister() {
        // Insert new Person in table 'customer' in DB and return new Customer
        RegisterRepository registerRepository = new RegisterRepository();
        RegisterPerson registerPerson;

        registerPerson = getUserInputAndCreateRegisterPerson();
        registerRepository.create(registerPerson);
        // TODO check if city is existing in DB

        int customerId = registerRepository.getCustomer(registerPerson);
        return new Customer(customerId, registerPerson.getFirstName(), registerPerson.getLastName(),
                registerPerson.getCity(), registerPerson.getAdress());
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
        return scanner.nextLine();
    }
}
