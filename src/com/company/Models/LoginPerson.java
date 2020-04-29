package com.company.Models;

public class LoginPerson {
    private int id;
    private String password;

    public LoginPerson(int id, String password) {
        this.id = id;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
