package com.company.Models;

public class RegisterPerson {

    private String firstName;
    private String lastName;
    private String city;
    private String adress;
    private String password;

    public RegisterPerson(String firstName, String lastName, String city, String adress, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.adress = adress;
        this.password = password;
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

    public String getPassword() {
        return password;
    }
}
