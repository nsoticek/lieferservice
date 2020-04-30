package com.company.DbHelper;

import com.company.Models.OrderDishIngredient;

import java.util.List;

public class OrderDishIngredientRepository implements IRepository<OrderDishIngredient>{
    private DbConnector connector;

    public OrderDishIngredientRepository() {
        this.connector = DbConnector.getInstance();
    }

    @Override
    public List<OrderDishIngredient> findAll() {
        return null;
    }

    @Override
    public OrderDishIngredient findOne(int id) {
        return null;
    }

    @Override
    public boolean create(OrderDishIngredient orderDishIngredient) {
        boolean isInserted = false;
        isInserted = connector.insertData("INSERT INTO `order_details_change`(`order_index`, `dish`, `ingredient`) " +
                "VALUES (" + orderDishIngredient.getOrderId() + ", " + orderDishIngredient.getDishId() + ", " +
                orderDishIngredient.getIngredientId() + ")");
        if (isInserted) {
            System.out.println("Bestellung wurde hinzugefügt");
        }
        return isInserted;
    }

}
