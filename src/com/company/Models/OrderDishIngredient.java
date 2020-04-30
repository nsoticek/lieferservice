package com.company.Models;

public class OrderDishIngredient {

    private int orderId;
    private int dishId;
    private int ingredientId;

    public OrderDishIngredient(int orderId, int dishId, int ingredientId) {
        this.orderId = orderId;
        this.dishId = dishId;
        this.ingredientId = ingredientId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getDishId() {
        return dishId;
    }

    public int getIngredientId() {
        return ingredientId;
    }
}
