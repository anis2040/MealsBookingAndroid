package com.esprit.booksmeals.model;

import com.squareup.moshi.Json;

public class Review {

    @Json(name = "rating")
    String rating;
    @Json(name = "review")
    String review;
    @Json(name = "user_id")
    int user_id;
    @Json(name = "restaurant_id")
    int restaurant_id;
    String user;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Review(String rating, String review, int user_id, int restaurant_id) {
        this.rating = rating;
        this.review = review;
        this.user_id = user_id;
        this.restaurant_id = restaurant_id;
    }

    public Review(String rating, String review) {
        this.rating = rating;
        this.review = review;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Review(String rating, String review, int restaurant_id, String user) {
        this.rating = rating;
        this.review = review;
        this.restaurant_id = restaurant_id;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Review{" +
                "rating='" + rating + '\'' +
                ", review='" + review + '\'' +
                ", user_id=" + user_id +
                ", restaurant_id=" + restaurant_id +
                '}';
    }
}
