package com.company;

import com.company.Controller.*;
import com.company.DbHelper.*;
import com.company.Models.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant("Restaurant");
        DbConnector dbConnector = new DbConnector();

        Customer customer = identifyUserMenu();

        while (true) {
            mainMenu(restaurant, customer, dbConnector);
        }
    }

    public static Customer identifyUserMenu() {
        Customer customer = null;

        if (inputUser("Willkommen! Haben Sie ein Kundenkonto? (JA/NEIN)").equalsIgnoreCase("Ja")) {
            // If userInput == yes --> show user the loginFunction
            customer = LoginController.executeLogin();
        } else {
            // If userInput == no --> show user the registerFunction
            customer = RegisterController.executeRegister();
        }
        return customer;
    }

    private static void mainMenu(Restaurant restaurant, Customer customer, DbConnector dbConnector) {
        String messageMenu = "\n1. Alle Speisen anzeigen \n2. Bestellung aufgeben \n3. Meine Bestellungen anzeigen";
        boolean removeAnotherIngredient;
        boolean addAnotherIngredient;
        boolean anotherDish = true;

        switch (inputUser(messageMenu)) {
            case "1": // Show dishcard
                DishController.printDishcard();
                break;
            case "2": // New order
                Order order = new Order(customer);
                order.setLocation(LocationController.getLocation(customer));
                DishController.printDishcard();

                while (anotherDish) {
                    // loop for the dish
                    removeAnotherIngredient = true;
                    addAnotherIngredient = true;

                    int userSelection = Integer.parseInt(inputUser("Was wollen Sie bestellen: "));
                    Dish dish = DishController.getDish(userSelection);
                    //ask user if he wants to remove an ingredient; if yes, remove it from the ingredient array (class dish)
                    removeSelectedIngredients(removeAnotherIngredient, dish);
                    //ask user if he wants to add an ingredient; if yes, add it to the ingredient array (class dish)
                    addSelectedIngredients(addAnotherIngredient, order, dish);
                    // Add dish to order
                    order.addDish(dish);
                    // Ask user if he wants to order additionally another dish
                    String userSelectionNewDish = inputUser("Möchten Sie noch etwas bestellen: (JA/NEIN)");
                    if (userSelectionNewDish.equalsIgnoreCase("Nein"))
                        anotherDish = false;
                }
                // Save order to the db
                OrderController.insertOrder(order);
                createInvoice(order);
                break;
            case "3": // Show user orders
                OrderController.printOrders(customer);
                break;
            default:
                System.out.println("Ungültige Eingabe!");
                break;
        }
    }

    private static void addSelectedIngredients(boolean addAnotherIngredient, Order order, Dish dish) {
        // loop for the ingredient (to ADD) in mainMenu
        while (addAnotherIngredient) {
            IngredientController.printAllIngredients();
            if (inputUser("Möchten Sie eine Zutat hinzufügen: (JA/NEIN) ").equalsIgnoreCase("Ja")) {
                int ingredientId = Integer.parseInt(inputUser("Welche Zutat möchten Sie hinzufügen: "));
                Ingredient ingredient = IngredientController.getIngredient(ingredientId);
                dish.addIngredient(ingredient, order);
            } else {
                addAnotherIngredient = false;
            }
        }
    }

    private static void removeSelectedIngredients(boolean removeAnotherIngredient, Dish dish) {
        // loop for the ingredient (to remove) in mainMenu
        while (removeAnotherIngredient) {
            DishController.printIngredientsOfCurrentDish(dish);
            if (inputUser("Möchten Sie eine Zutat entfernen: (JA/NEIN) ").equalsIgnoreCase("Ja")) {
                int ingredientId = Integer.parseInt(inputUser("Welche Zutat möchten Sie entfernen: "));
                DishController.removeIngredient(dish, ingredientId);
            } else {
                removeAnotherIngredient = false;
            }
        }
    }

    private static void createInvoice(Order order) {
        System.out.println("---------Rechnung----------");
        for (int i = 0; i < order.getDishes().size(); i++) {
            System.out.printf("%s\t\t%.2f€\n", order.getDishes().get(i).getType(), order.getDishes().get(i).getPrice());
            for (int j = 0; j < order.getDishes().get(i).getIngredients().size(); j++) {
                System.out.printf("\t%s\t%.2f€\n", order.getDishes().get(i).getIngredients().get(j).getType(),
                        order.getDishes().get(i).getIngredients().get(j).getPrice());
            }
        }
        System.out.printf("----------------------------");
        System.out.printf("\nGesamt\t\t\t\t%.2f€", order.getTotalPrice());
        System.out.printf("\nLieferug\t\t\t%.2f€\n", order.getLocation().getPrice());
        System.out.println("----------------------------");
    }

    private static String inputUser(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        String userInput = scanner.nextLine();
        return userInput;
    }
}