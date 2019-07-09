package com.esprit.booksmeals.model;

/**
 * Created by Wolf Soft on 8/3/2017.
 */

public class BeanClassForDish {

    Integer image;
    String dish_name;
    String dish_type;
    String price;

    public BeanClassForDish(Integer image, String dish_name, String dish_type, String price) {
        this.image = image;
        this.dish_name = dish_name;
        this.dish_type = dish_type;
        this.price = price;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public String getDish_type() {
        return dish_type;
    }

    public void setDish_type(String dish_type) {
        this.dish_type = dish_type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
