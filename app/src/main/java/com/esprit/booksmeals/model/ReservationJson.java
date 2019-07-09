package com.esprit.booksmeals.model;

import com.squareup.moshi.Json;

public class ReservationJson {

    @Json(name = "name")
    String name;
    @Json(name = "nbperson")
    String nbperson;
    @Json(name = "time")
    String time;
    @Json(name = "approved")
    int approved;
    @Json(name = "restaurant_id")
    int restaurant_id;
    @Json(name = "user_id")
    int user_id;

    public String getNbperson() {
        return nbperson;
    }

    public void setNbperson(String nbperson) {
        this.nbperson = nbperson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
