package com.company.DbHelper;

import com.company.Models.Dish;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DishRepository implements IRepository<Dish> {
    private DbConnector connector;

    public DishRepository() {
        this.connector = DbConnector.getInstance();
    }

    @Override
    public List<Dish> findAll() {
        ArrayList<Dish> dishes = new ArrayList<>();

        ResultSet rs = connector.fetchData("SELECT `id`, `name`, `price` FROM `dish`");
        if (rs == null) {
            System.out.println("Error bei fetchDishes! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");

                dishes.add(new Dish(id, name, price));
            }
        } catch (SQLException e) {
            System.out.println("Error bei fetchUser!");
            e.printStackTrace();
        } finally {
            connector.closeConnection();
        }
        return dishes;
    }

    @Override
    public Dish findOne(int id) {
        Dish dish = null;

        ResultSet rs = connector.fetchData("SELECT `id`, `name`, `price` FROM `dish` WHERE id = " + id);
        if (rs == null) {
            System.out.println("Error bei fetchDishes! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                int DishId = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");

                dish = new Dish(id, name, price);
            }
        } catch (SQLException e) {
            System.out.println("Error bei fetchUser!");
            e.printStackTrace();
        } finally {
            connector.closeConnection();
        }
        return dish;
    }

    @Override
    public boolean create(Dish entity) {
        return false;
    }
}
