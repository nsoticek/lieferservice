package com.company.DbHelper;

import com.company.Models.Dish;
import com.company.Models.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DishDb {

    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    public ArrayList<Ingredient> getIngredients(int dishId, DbConnector dbConnector) {
        // All ingredients of current dish
        // delete old entries
        ingredients.clear();
        fetchIngredients(dishId, dbConnector);
        return ingredients;
    }

    public void fetchIngredients(int dishId, DbConnector dbConnector) {
        // Fetch all ingredients of current Dish
        ResultSet rs = dbConnector.fetchData("SELECT dish_ingredient.ingredient, ingredient.name, ingredient.extraprice " +
                "FROM `dish_ingredient` " +
                "INNER JOIN `ingredient` ON dish_ingredient.ingredient = ingredient.id " +
                "WHERE `dish` = " + dishId);
        if (rs == null) {
            System.out.println("Error bei fetchIngredients! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                int ingredientId = rs.getInt("ingredient");
                String name = rs.getString("name");
                double extraPrice = rs.getDouble("extraprice");

                ingredients.add(new Ingredient(ingredientId, name, extraPrice));
            }
        } catch (SQLException e) {
            System.out.println("Error bei fetchUser!");
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
    }
}
