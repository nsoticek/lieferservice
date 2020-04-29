package com.company.Controller;

import com.company.DbHelper.DbConnector;
import com.company.DbHelper.DishDb;
import com.company.DbHelper.OrderDb;
import com.company.DbHelper.RestaurantDb;
import com.company.Models.Dish;
import com.company.Models.Ingredient;

import java.util.ArrayList;

public class DishController {

    public Dish getDish(int dishId, RestaurantDb restaurantDb, DishDb dishDb, DbConnector dbConnector) {
        // Search if dishId exists; If yes create new Dish with all attributes
        ArrayList<Dish> dishes = restaurantDb.getDishes(dbConnector);
        ArrayList<Ingredient> ingredients = dishDb.getIngredients(dishId, dbConnector);
        Dish dish = null;

        for (int i = 0; i < dishes.size(); i++) {
            if(dishes.get(i).getId() == dishId) {
                dish = new Dish(dishId, dishes.get(i).getType(), dishes.get(i).getPrice());
            }
        }

        // Add all related ingredients (from DB) to the dish
        for (int i = 0; i < ingredients.size(); i++) {
            dish.addIngredient(ingredients.get(i));
        }
        return dish;
    }

    public void removeIngredient(Dish dish, int ingredientId) {
        // Remove ingredient if user wants; The ingredient will be removed from the ingredientArrayList in Dish
        ArrayList<Ingredient> ingredients = dish.getIngredients();

        for (int i = 0; i < ingredients.size(); i++) {
            if(ingredients.get(i).getId() == ingredientId) {
                ingredients.remove(i);
                dish.setIngredients(ingredients);
            } else {
                System.out.println("Der Artikel konnte nicht gefunden werden");
            }
        }
    }

    public void printIngredientsOfCurrentDish(DishDb dishDb, Dish dish, DbConnector dbConnector) {
        ArrayList<Ingredient> ingredients = dish.getIngredients();

        for (int i = 0; i < ingredients.size(); i++) {
            System.out.println(ingredients.get(i).getId() + ". " + ingredients.get(i).getType() + " " +
                    ingredients.get(i).getPrice());
        }
    }
}
