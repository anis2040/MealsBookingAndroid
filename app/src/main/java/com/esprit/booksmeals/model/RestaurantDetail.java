package com.esprit.booksmeals.model;

public class RestaurantDetail {


    Integer FoodImage_Id,Food_13_Image_Id;

    public Integer getFoodImage_Id() {
        return FoodImage_Id;
    }

    public void setFoodImage_Id(Integer foodImage_Id) {
        FoodImage_Id = foodImage_Id;
    }

    public Integer getFood_13_Image_Id() {
        return Food_13_Image_Id;
    }

    public void setFood_13_Image_Id(Integer food_13_Image_Id) {
        Food_13_Image_Id = food_13_Image_Id;
    }

    public RestaurantDetail(Integer foodImage_Id, Integer food_13_Image_Id) {
        FoodImage_Id = foodImage_Id;
        Food_13_Image_Id = food_13_Image_Id;
    }
}
