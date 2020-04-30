package com.company.DbHelper;

import com.company.Models.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class IngredientRepository implements IRepository<Ingredient> {
    private DbConnector connector;

    public IngredientRepository() {
        this.connector = DbConnector.getInstance();
    }

    @Override
    public List<Ingredient> findAll() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        ResultSet rs = connector.fetchData("SELECT * FROM `ingredient`");
        if (rs == null) {
            System.out.println("Error bei fetchIngredients! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                int ingredientId = rs.getInt("id");
                String name = rs.getString("name");
                double extraPrice = rs.getDouble("extraprice");

                ingredients.add(new Ingredient(ingredientId, name, extraPrice));
            }
        } catch (SQLException e) {
            System.out.println("Error bei fetchUser!");
            e.printStackTrace();
        } finally {
            connector.closeConnection();
        }
        return ingredients;
    }

    @Override
    public Ingredient findOne(int id) {
        Ingredient ingredient = null;
        ResultSet rs = connector.fetchData("SELECT * FROM `ingredient` WHERE id = " + id);
        if(rs == null){
            System.out.println("Konnte Daten nicht abrufen!");
        } try {
            while (rs.next()) {
                int ingredientId = rs.getInt("id");
                String name = rs.getString("name");
                double extraPrice = rs.getDouble("extraPrice");

                ingredient = new Ingredient(ingredientId, name, extraPrice);
            }
        } catch (SQLException e) {
            System.out.println("Error bei fetchUser!");
            e.printStackTrace();
        } finally {
            connector.closeConnection();
        }
        return ingredient;
    }

    @Override
    public boolean create(Ingredient entity) {
        return false;
    }

    public ArrayList<Ingredient> getIngredients(int id) {
        // Fetch all ingredients of current Dish
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        ResultSet rs = connector.fetchData("SELECT dish_ingredient.ingredient, ingredient.name, ingredient.extraprice " +
                "FROM `dish_ingredient` " +
                "INNER JOIN `ingredient` ON dish_ingredient.ingredient = ingredient.id " +
                "WHERE `dish` = " + id);
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
            connector.closeConnection();
        }
        return ingredients;
    }
}
