package com.esprit.booksmeals.model;

public class Categorie {
    Integer image;
    String name;

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Categorie(Integer image, String name) {
        this.image = image;
        this.name = name;
    }
}
