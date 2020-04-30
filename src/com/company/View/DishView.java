package com.company.View;

import com.company.Models.Dish;
import com.company.Models.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class DishView {

    public void printIngredientsOfCurrentDish(ArrayList<Ingredient> ingredients) {
        for (int i = 0; i < ingredients.size(); i++) {
            System.out.println(ingredients.get(i).getId() + ". " + ingredients.get(i).getType() + " " +
                    ingredients.get(i).getPrice());
        }
    }

    public void printDishcard(List<Dish> dishes) {
        for (int i = 0; i < dishes.size(); i++) {
            System.out.println(dishes.get(i).getId() + ". " + dishes.get(i).getType() + " " + dishes.get(i).getPrice());
        }
    }
}
