package com.company.Controller;

import com.company.DbHelper.OrderDishIngredientRepository;
import com.company.DbHelper.OrderRepository;
import com.company.Models.*;
import com.company.View.OrderView;

import java.util.ArrayList;

public class OrderController {

    public static void insertOrder(Order order) {
        // Insert to table 'order_details_change' on DB
        OrderDishIngredientRepository orderDishIngredientRepository = new OrderDishIngredientRepository();
        OrderRepository orderRepository = new OrderRepository();
        // Insert to table 'order_table' on DB
        boolean isInserted = orderRepository.create(order);

        if (isInserted) {
            ArrayList<Dish> dishes = order.getDishes();
            int orderId = orderRepository.getId(order);

            for (int i = 0; i < dishes.size(); i++) {
                for (int j = 0; j < dishes.get(i).getIngredients().size(); j++) {
                    OrderDishIngredient orderDishIngredient = new OrderDishIngredient(orderId,
                            dishes.get(i).getId(), dishes.get(i).getIngredients().get(j).getId());
                    // Insert to table 'order_details_change' on DB
                    orderDishIngredientRepository.create(orderDishIngredient);
                }
            }
        }
    }

    public static void printOrders(Customer customer) {
        // Get all orders from the current user and print them
        OrderView orderView = new OrderView();
        OrderRepository orderRepository = new OrderRepository();

        ArrayList<FullOrder> fullOrders = orderRepository.getOrdersOfCustomer(customer);
        orderView.printOrders(fullOrders);
    }
}
