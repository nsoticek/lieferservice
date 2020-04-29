package com.company.Controller;

import com.company.DbHelper.DbConnector;
import com.company.DbHelper.RestaurantDb;
import com.company.Models.Dish;
import com.company.Models.Ingredient;

import java.util.ArrayList;

public class IngredientController {

    public Ingredient getIngredient(int ingredientId, RestaurantDb restaurantDb, DbConnector dbConnector) {
        // Search if ingredient exists; If yes create new ingredient with all attributes
        ArrayList<Ingredient> ingredients = restaurantDb.getIngredients(dbConnector);
        Ingredient ingredient = null;

        for (int i = 0; i < ingredients.size(); i++) {
            if(ingredients.get(i).getId() == ingredientId) {
                ingredient = new Ingredient(ingredientId, ingredients.get(i).getType(), ingredients.get(i).getPrice());
            }
        }
        return ingredient;
    }
}
