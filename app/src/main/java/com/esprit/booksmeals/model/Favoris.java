package com.esprit.booksmeals.model;

public class Favoris {

    String name,address,date,minPrice,maxPrice, img;

    public Favoris(String name, String address, String date, String minPrice, String maxPrice, String img) {
        this.name = name;
        this.address = address;
        this.date = date;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.img = img;
    }

    public Favoris(String name, String address,String minPrice, String maxPrice,String date) {
        this.name = name;
        this.address = address;
        this.date = date;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.img = img;
    }
    public Favoris(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
