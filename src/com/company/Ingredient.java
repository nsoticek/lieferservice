package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ingredient {

    private int id;
    private String type;
    private double price;

    public Ingredient(int id) {
        this.id = id;
        initData();
    }

    public Ingredient(int id, String type, double price) {
        this.id = id;
        this.type = type;
        this.price = price;
    }

    private void initData() {
        String sqlCommand = "SELECT `name`, `extraprice` FROM `ingredient` WHERE `id` = " + this.id;
        setIngredientData(sqlCommand);
    }

    private void setIngredientData(String sqlCommand) {
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                String name = rs.getString("name");
                double extraPrice = rs.getDouble("extraprice");

                this.type = name;
                this.price = extraPrice;
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

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }
}
