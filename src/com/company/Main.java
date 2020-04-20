package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant("Restaurant");
        Customer customer = null;
        String messageLogin = "Willkommen! Haben Sie ein Kundenkonto? (JA/NEIN) ";

        if(inputUserStr(messageLogin).equalsIgnoreCase("Ja")){
            customer = login(customer);
        } else {
            customer = registerNewCustomer();
            boolean isLoactionExisting = restaurant.checkIfLocationAvailable(customer);
            if (!isLoactionExisting)
                System.out.println("Wir liefern nicht an deinen Wohnort!");
        }

        while(true) {
            mainMenu(restaurant, customer);
        }
    }

    private static Customer registerNewCustomer() {
        String firstName = inputUserStr("Vorname: ");
        String lastName = inputUserStr("Nachname: ");
        String city = inputUserStr("Wohnort: ");
        String adress = inputUserStr("Adresse: ");
        Customer customer = new Customer(firstName, lastName, city, adress);
        customer.register();
        System.out.println("Kundenkonto angelegt.");
        return customer;
    }

    private static Customer login(Customer customer) {
        boolean isLoggedIn = false;
        while (!isLoggedIn) {
            int id = inputUser("Geben Sie Ihre Kundennr. ein: ");
            customer = new Customer(id);
            isLoggedIn = customer.login();
            if (isLoggedIn) {
                System.out.println("Erfolgreich eingeloggt!");
            } else {
                System.out.println("Kundennr. existiert nicht! Bitte nochmal versuchen.");
            }
        }
        return customer;
    }

    private static void mainMenu(Restaurant restaurant, Customer customer) {
        String messageMenu = "1. Alle Speisen anzeigen \n2. Bestellung aufgeben \n3. Meine Bestellungen anzeigen";
        boolean removeAnotherIngredient;
        boolean addAnotherIngredient;
        boolean anotherDish = true;

        switch (inputUser(messageMenu)) {
            case 1: // Show dishcard
                restaurant.showDishcard();
                break;
            case 2: // New order
                Order order = new Order(customer);

                restaurant.showDishcard();
                while (anotherDish) {
                    // loop for the dish
                    removeAnotherIngredient = true;
                    addAnotherIngredient = true;
                    int userSelection = inputUser("Was wollen Sie bestellen: ");
                    Dish dish = new Dish(userSelection);
                    dish.getIngredientsFromDb();
                    while (removeAnotherIngredient) {
                        // loop for the ingredient (to remove)
                        dish.printIngredients();
                        if (inputUserStr("Möchten Sie eine Zutat entfernen: (JA/NEIN) ").equalsIgnoreCase("Ja")) {
                            int ingredientId = inputUser("Welche Zutat möchten Sie entfernen: ");
                            //Ingredient ingredient = new Ingredient(ingredientId);
                            dish.removeIngredient(ingredientId);
                        } else {
                            removeAnotherIngredient = false;
                        }
                    }
                    while (addAnotherIngredient) {
                        // loop for the ingredient (to remove)
                        restaurant.printAllIngredients();
                        if (inputUserStr("Möchten Sie eine Zutat hinzufügen: (JA/NEIN) ").equalsIgnoreCase("Ja")) {
                            int ingredientId = inputUser("Welche Zutat möchten Sie hinzufügen: ");
                            Ingredient ingredient = new Ingredient(ingredientId);
                            dish.addIngredient(ingredient, order);
                        } else {
                            addAnotherIngredient = false;
                        }
                    }
                    order.addDish(dish);
                    String userSelectionNewDish = inputUserStr("Möchten Sie noch etwas bestellen: (JA/NEIN)");
                    if(userSelectionNewDish.equalsIgnoreCase("Nein"))
                        anotherDish = false;
                }
                // create new order and save it to the db
                order.addOrderToDb();
                order.printSuccessMessage();
                createInvoice(order);
                break;
            case 3: // Show user orders
                customer.printOrders();
                break;
            default:
                System.out.println("Ungültige Eingabe!");
                break;
        }
    }

    private static int inputUser(String message) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(message);
        int userInput = scanner.nextInt();
        return userInput;
    }

    private static String inputUserStr(String message) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(message);
        String userInput = scanner.nextLine();
        return userInput;
    }

    private static void createInvoice(Order order) {
        System.out.println("---------Rechnung----------");
        // ACHTUNG printf noch anpassen, die Platzhalter!
        for (int i = 0; i < order.getDishes().size(); i++) {
            System.out.printf("%s\t\t%.2f€\n", order.getDishes().get(i).getType(), order.getDishes().get(i).getPrice());
            for (int j = 0; j < order.getDishes().get(i).getIngredients().size(); j++) {
                System.out.printf("\t%s\t%.2f€\n", order.getDishes().get(i).getIngredients().get(j).getType(), order.getDishes().get(i).getIngredients().get(j).getPrice());
            }
        }
        System.out.printf("----------------------------");
        System.out.printf("\nGesamt\t\t\t\t%.2f€\n", order.getTotalPrice());
        System.out.println("----------------------------");
    }
}