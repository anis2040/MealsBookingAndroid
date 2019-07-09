package com.esprit.booksmeals.model;

public class Menu {
    private int id;
    private String food_name;
    private int price;
    private int restaurant_id;
    private String photo;


    public Menu(String food_name, int price, int restaurant_id, String photo) {
        this.food_name = food_name;
        this.price = price;
        this.restaurant_id = restaurant_id;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", food_name='" + food_name + '\'' +
                ", price='" + price + '\'' +
                ", restaurant_id=" + restaurant_id +
                ", photo='" + photo + '\'' +
                '}';
    }
}
