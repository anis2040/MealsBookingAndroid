package com.esprit.booksmeals.model;

import java.util.List;

public class Restaurant {

    int id;
    int phone;
    String name;
    String address;
    String description;
    String photo;
    Float priceMin;
    Float priceMax;
    float latitude;
    String category;
    float longitude;
    List<Image> images;


    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Restaurant() {

    }

    public Restaurant(String name, String photo) {
        this.name = name;
        this.photo = photo;
    }

    public Restaurant(int id, int phone, String name, String address, String description, String photo, Float priceMin, Float priceMax, float latitude, String categorie, float longitude) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.description = description;
        this.photo = photo;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.latitude = latitude;
        this.category = categorie;
        this.longitude = longitude;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getLatitude() {
        return latitude;
    }

    public Restaurant(int id, String name, String address, String description, String photo, Float priceMin, Float priceMax) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.photo = photo;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
    }

    public Restaurant(int id, String name, String address, String description, String photo, Float priceMin, Float priceMax, float latitude, String categorie, float longitude, List<Image> images) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.photo = photo;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.latitude = latitude;
        this.category = categorie;
        this.longitude = longitude;
        this.images = images;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setPriceMin(Float priceMin) {
        this.priceMin = priceMin;
    }



    public Restaurant(String name, String address, String photo, Float priceMin) {
        this.name = name;
        this.address = address;
        this.photo = photo;
        this.priceMin = priceMin;
    }



    public Restaurant(String name, String address, String categorie) {
        this.name = name;
        this.address = address;
        this.category = categorie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public Float getMinPrice() {
        return priceMin;
    }

    public void setMinPrice(Float minPrice) {
        this.priceMin = minPrice;
    }

    public Float getMaxPrice() {
        return priceMax;
    }

    public void setMaxPrice(Float maxPrice) {
        this.priceMax = maxPrice;
    }

    public void setAddress(String adresse) {
        this.address = adresse;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String image) {
        this.photo = image;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", phone=" + phone +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                ", priceMin=" + priceMin +
                ", priceMax=" + priceMax +
                ", latitude=" + latitude +
                ", category='" + category + '\'' +
                ", longitude=" + longitude +
                ", images=" + images +
                '}';
    }
}
