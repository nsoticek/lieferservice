package com.company.Controller;

import com.company.DbHelper.*;
import com.company.Models.Dish;
import com.company.Models.Ingredient;
import com.company.View.DishView;

import java.util.ArrayList;
import java.util.List;

public class DishController {

    public static Dish getDish(int dishId) {
        // Search if dishId exists; If yes create new Dish with all attributes and return it
        IngredientRepository ingredientRepository = new IngredientRepository();
        DishRepository dishRepository = new DishRepository();

        Dish dish = dishRepository.findOne(dishId);
        List<Ingredient> ingredients = ingredientRepository.getIngredients(dishId);

        // Add all related ingredients (from DB) to the dish
        for (Ingredient ingredient : ingredients) {
            dish.addIngredient(ingredient);
        }
        return dish;
    }

    public static void printIngredientsOfCurrentDish(Dish dish) {
        DishView dishView = new DishView();
        ArrayList<Ingredient> ingredients = dish.getIngredients();

        dishView.printIngredientsOfCurrentDish(ingredients);
    }

    public static void printDishcard() {
        DishRepository dishRepository = new DishRepository();
        DishView dishView = new DishView();

        List<Dish> dishes = dishRepository.findAll();
        dishView.printDishcard(dishes);

    }

    public static void removeIngredient(Dish dish, int ingredientId) {
        // Remove ingredient if user wants; The ingredient will be removed from the ingredientArrayList in Dish
        ArrayList<Ingredient> ingredients = dish.getIngredients();

        for (int i = 0; i < ingredients.size(); i++) {
            if(ingredients.get(i).getId() == ingredientId) {
                ingredients.remove(i);
                dish.setIngredients(ingredients);
            }
        }
    }
}
