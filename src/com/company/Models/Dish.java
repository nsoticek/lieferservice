package com.company.Models;

import java.util.ArrayList;

public class Dish {

    private int id;
    private String type;
    private double price;
    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

    public Dish(int id, String type, double price) {
        this.id = id;
        this.type = type;
        this.price = price;
    }

    public void addIngredient(Ingredient ingredient, Order order) {
        // Use to add additional ingredients
        order.setTotalPrice(order.getTotalPrice() + ingredient.getPrice());
        ingredients.add(ingredient);
    }

    public void addIngredient(Ingredient ingredient) {
        // Use to add fix ingredients
        ingredients.add(ingredient);
    }

    public ArrayList<Ingredient> getIngredients() {
            return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }
}
