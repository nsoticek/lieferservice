package com.company;

import com.company.Controller.*;
import com.company.DbHelper.*;
import com.company.Models.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant("Restaurant");
        DbConnector dbConnector = new DbConnector();
        CustomerDb customerDb = new CustomerDb();
        RegisterDb registerDb = new RegisterDb();
        LoginDb loginDb = new LoginDb();
        Customer customer = null;

        customer = identifyUserMenu(dbConnector, registerDb, loginDb, customerDb);

        while (true) {
            mainMenu(restaurant, customer, dbConnector);
        }
    }

    public static Customer identifyUserMenu(DbConnector dbConnector, RegisterDb registerDb, LoginDb loginDb, CustomerDb customerDb) {
        Customer customer = null;

        if (inputUser("Willkommen! Haben Sie ein Kundenkonto? (JA/NEIN)").equalsIgnoreCase("Ja")) {
            // If userInput == yes --> show user the loginFunction
            customer = LoginController.executeLogin(dbConnector, loginDb, customer);
        } else {
            // If userInput == no --> show user the registerFunction
            customer = RegisterController.executeRegister(dbConnector, registerDb, customerDb);
        }
        return customer;
    }

    private static void mainMenu(Restaurant restaurant, Customer customer, DbConnector dbConnector) {
        String messageMenu = "1. Alle Speisen anzeigen \n2. Bestellung aufgeben \n3. Meine Bestellungen anzeigen";
        boolean removeAnotherIngredient;
        boolean addAnotherIngredient;
        boolean anotherDish = true;

        RestaurantDb restaurantDb = new RestaurantDb();
        RestaurantController restaurantController = new RestaurantController();
        IngredientController ingredientController = new IngredientController();
        DishController dishController = new DishController();
        DishDb dishDb = new DishDb();
        OrderDb orderDb = new OrderDb();

        switch (inputUser(messageMenu)) {
            case "1": // Show dishcard
                restaurantController.showDishcard(restaurantDb, dbConnector);
                break;
            case "2": // New order
                Order order = new Order(customer);
                order.setLocation(LocationDb.getLocation(customer, dbConnector));
                restaurantController.showDishcard(restaurantDb, dbConnector);

                while (anotherDish) {
                    // loop for the dish
                    removeAnotherIngredient = true;
                    addAnotherIngredient = true;

                    int userSelection = Integer.parseInt(inputUser("Was wollen Sie bestellen: "));
                    Dish dish = dishController.getDish(userSelection, restaurantDb, dishDb, dbConnector);

                    // loop for the ingredient (to remove) in mainMenu
                    while (removeAnotherIngredient) {
                        dishController.printIngredientsOfCurrentDish(dishDb, dish, dbConnector);
                        if (inputUser("Möchten Sie eine Zutat entfernen: (JA/NEIN) ").equalsIgnoreCase("Ja")) {
                            int ingredientId = Integer.parseInt(inputUser("Welche Zutat möchten Sie entfernen: "));
                            dishController.removeIngredient(dish, ingredientId);
                        } else {
                            removeAnotherIngredient = false;
                        }
                    }
                    // loop for the ingredient (to ADD) in mainMenu
                    while (addAnotherIngredient) {
                        restaurantController.printAllIngredients(restaurantDb, dbConnector);
                        if (inputUser("Möchten Sie eine Zutat hinzufügen: (JA/NEIN) ").equalsIgnoreCase("Ja")) {
                            int ingredientId = Integer.parseInt(inputUser("Welche Zutat möchten Sie hinzufügen: "));
                            Ingredient ingredient = ingredientController.getIngredient(ingredientId, restaurantDb, dbConnector);
                            dish.addIngredient(ingredient, order);
                        } else {
                            addAnotherIngredient = false;
                        }
                    }
                    order.addDish(dish);
                    String userSelectionNewDish = inputUser("Möchten Sie noch etwas bestellen: (JA/NEIN)");
                    if (userSelectionNewDish.equalsIgnoreCase("Nein"))
                        anotherDish = false;
                }
                // Save order to the db
                orderDb.insertOrder(order, dbConnector);
                orderDb.insertDishAndIngredient(order, dbConnector);
                createInvoice(order);
                break;
            case "3": // Show user orders
                CustomerController.printOrders(orderDb, customer,dbConnector);
                break;
            default:
                System.out.println("Ungültige Eingabe!");
                break;
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