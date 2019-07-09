package com.esprit.booksmeals.model;


public class Reservation {
    int id;
    String name;
    String nbperson;
    String time;
    int approved;
    int restaurant_id;
    int user_id;
    Restaurant restaurant;


    public Reservation(int id, String name, String nbperson, String time, int approved, int restaurant_id, int user_id) {
        this.id = id;
        this.name = name;
        this.nbperson = nbperson;
        this.time = time;
        this.approved = approved;
        this.restaurant_id = restaurant_id;
        this.user_id = user_id;
    }

    public Reservation(String name, String nbperson, String time) {
        this.name = name;
        this.nbperson = nbperson;
        this.time = time;

    }

    public Reservation(String name, String nbperson, int approved) {
        this.name = name;
        this.nbperson = nbperson;
        this.approved = approved;
    }


    public Reservation(String name, String nbperson, String time,int approved) {
        this.name = name;
        this.nbperson = nbperson;
        this.time = time;
        this.approved = approved;
    }

    public Reservation(String time) {
        this.time = time;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNbperson() {
        return nbperson;
    }

    public void setNbperson(String nbperson) {
        this.nbperson = nbperson;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nbperson='" + nbperson + '\'' +
                ", time='" + time + '\'' +
                ", approved='" + approved + '\'' +
                '}';
    }
}
