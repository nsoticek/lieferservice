package com.company.View;

import com.company.Models.Ingredient;

import java.util.List;

public class IngredientView {

    public void printAllIngredients(List<Ingredient> ingredients) {
        for (int i = 0; i < ingredients.size(); i++) {
            System.out.println(ingredients.get(i).getId() + ". " + ingredients.get(i).getType());
        }
    }
}
