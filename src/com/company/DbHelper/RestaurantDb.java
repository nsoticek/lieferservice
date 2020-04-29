package com.company.DbHelper;

import com.company.Models.Dish;
import com.company.Models.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RestaurantDb {

    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private ArrayList<Dish> dishes = new ArrayList<>();

    public ArrayList<Ingredient> getIngredients(DbConnector dbConnector) {
        // All ingredients from Db
        if (ingredients.isEmpty()) {
            fetchIngredients(dbConnector);
        }
        return ingredients;
    }

    public ArrayList<Dish> getDishes(DbConnector dbConnector) {
        // All dishes from Db
        if (dishes.isEmpty()) {
            fetchDishes(dbConnector);
        }
        return dishes;
    }

    public void fetchIngredients(DbConnector dbConnector) {
        // Get all ingredients from DB
        // delete all old entries
        ingredients.clear();

        ResultSet rs = dbConnector.fetchData("SELECT `id`, `name`, `extraprice` FROM `ingredient`");
        if (rs == null) {
            System.out.println("Error bei fetchIngredients / IngredientsDb! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double extraPrice = rs.getDouble("extraprice");

                ingredients.add(new Ingredient(id, name, extraPrice));
            }
        } catch (SQLException e) {
            System.out.println("Error bei fetchUser!");
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
    }

    public void fetchDishes(DbConnector dbConnector) {
        // Get all dishes from DB
        // delete all old entries
        dishes.clear();

        ResultSet rs = dbConnector.fetchData("SELECT `id`, `name`, `price` FROM `dish`");
        if (rs == null) {
            System.out.println("Error bei fetchDishes / restaurantDb! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");

                dishes.add(new Dish(id, name, price));
            }
        } catch (SQLException e) {
            System.out.println("Error bei fetchUser!");
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
    }
}
