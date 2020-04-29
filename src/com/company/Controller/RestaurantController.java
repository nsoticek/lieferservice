package com.company.Controller;

import com.company.DbHelper.DbConnector;
import com.company.DbHelper.DishDb;
import com.company.DbHelper.RestaurantDb;
import com.company.Models.Dish;
import com.company.Models.Ingredient;

import java.util.ArrayList;

public class RestaurantController {

    public void showDishcard(RestaurantDb restaurantDb, DbConnector dbConnector) {
        ArrayList<Dish> dishes = restaurantDb.getDishes(dbConnector);

        for (int i = 0; i < dishes.size(); i++) {
            System.out.println(dishes.get(i).getId() + ". " + dishes.get(i).getType() + " " + dishes.get(i).getPrice());
        }
    }

    public void printAllIngredients(RestaurantDb restaurantDb, DbConnector dbConnector) {
        // Print all available ingredients
        ArrayList<Ingredient> ingredients = restaurantDb.getIngredients(dbConnector);

        for (int i = 0; i < ingredients.size(); i++) {
            System.out.println(ingredients.get(i).getId() + ". " + ingredients.get(i).getType());
        }
    }

    public void printIngredientsOfDish(DishDb dishDb, int dishId, DbConnector dbConnector) {
        // Print all available ingredients
        ArrayList<Ingredient> ingredients = dishDb.getIngredients(dishId, dbConnector);

        for (int i = 0; i < ingredients.size(); i++) {
            System.out.println(ingredients.get(i).getId() + ". " + ingredients.get(i).getType());
        }
    }
}
