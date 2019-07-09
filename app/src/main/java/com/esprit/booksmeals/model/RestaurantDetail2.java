package com.esprit.booksmeals.model;

public class RestaurantDetail2 {
    String Food5_RestaurantName_Id2;
    String price;

    public RestaurantDetail2(String food5_RestaurantName_Id2, String price) {
        Food5_RestaurantName_Id2 = food5_RestaurantName_Id2;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFood5_RestaurantName_Id2() {
        return Food5_RestaurantName_Id2;
    }

    public void setFood5_RestaurantName_Id2(String food5_RestaurantName_Id2) {
        Food5_RestaurantName_Id2 = food5_RestaurantName_Id2;
    }

    public RestaurantDetail2(String food5_RestaurantName_Id2) {
        Food5_RestaurantName_Id2 = food5_RestaurantName_Id2;
    }

}
