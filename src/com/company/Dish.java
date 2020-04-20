package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Dish {

    private int id;
    private String type;
    private double price;
    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

    public Dish(int id) {
        this.id = id;
        setData();
    }

    public Dish(String type, double price) {
        this.type = type;
        this.price = price;
    }

    private void setData() {
        // Set the missing data to dish object
        String sqlCommand = "SELECT `name`, `price` FROM `dish` WHERE `id` = " + this.id;
        setDishData(sqlCommand);
    }

    public void removeIngredient(int ingredientId) {
        for (int i = 0; i < ingredients.size(); i++) {
            if(ingredients.get(i).getId() == ingredientId) {
                ingredients.remove(i);
            } else {
                System.out.println("Der Artikel konnte nicht gefunden werden");
            }
        }
    }

    public void addIngredient(Ingredient ingredient, Order order) {
        order.setTotalPrice(ingredient.getPrice());
        ingredients.add(ingredient);
    }

    public void getIngredientsFromDb() {
        System.out.println("\nZutaten");
        String sqlCommand = "SELECT dish_ingredient.ingredient, ingredient.name, ingredient.extraprice\n" +
                "FROM `dish_ingredient`\n" +
                "INNER JOIN `ingredient` ON dish_ingredient.ingredient = ingredient.id\n" +
                "WHERE `dish` = " + this.id;
        getIngredientsFromDb(sqlCommand);
    }

    public void printIngredients() {
        for (int i = 0; i < ingredients.size(); i++) {
            System.out.println(ingredients.get(i).getId() + ". " + ingredients.get(i).getType());
        }
    }

    private void getIngredientsFromDb(String sqlCommand) {
        // get all igredients from DB and save it to ArrayList
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                Integer id = rs.getInt("ingredient");
                String name = rs.getString("name");
                double extraPrice = rs.getDouble("extraprice");

                // Save all ingredients to this.dish
                Ingredient ingredient = new Ingredient(id, name, extraPrice);
                ingredients.add(ingredient);
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

    private void setDishData(String sqlCommand) {

        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");

                this.type = name;
                this.price = price;
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

    public ArrayList<Ingredient> getIngredients() {
            return ingredients;
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
