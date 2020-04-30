package com.company.Controller;

import com.company.DbHelper.IngredientRepository;
import com.company.Models.Ingredient;
import com.company.View.IngredientView;

import java.util.List;

public class IngredientController {

    public static Ingredient getIngredient(int ingredientId) {
        // Search if ingredient exists; If yes create new ingredient with all attributes
        IngredientRepository ingredientRepository = new IngredientRepository();

        return ingredientRepository.findOne(ingredientId);
    }

    public static void printAllIngredients() {
        // Print all available ingredients
        IngredientView ingredientView = new IngredientView();
        IngredientRepository ingredientRepository = new IngredientRepository();

        List<Ingredient> ingredients = ingredientRepository.findAll();
        ingredientView.printAllIngredients(ingredients);
    }
}
