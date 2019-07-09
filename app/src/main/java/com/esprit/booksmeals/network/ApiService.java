package com.esprit.booksmeals.network;

import com.esprit.booksmeals.model.AcceptReservation;
import com.esprit.booksmeals.model.AccessToken;
import com.esprit.booksmeals.model.ReservationJson;
import com.esprit.booksmeals.model.Review;
import com.esprit.booksmeals.model.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface ApiService {

    @POST("register") // http://domain.com/api
    @FormUrlEncoded
    Call<AccessToken> register(@Field("name") String name, @Field("email") String email ,
                               @Field("password") String password, @Field("role") String role);

    @POST("login")
    @FormUrlEncoded
    Call<AccessToken> login(@Field("username") String username, @Field("password") String password);

    @POST("social_auth")
    @FormUrlEncoded
    Call<AccessToken> socialAuth(@Field("name") String name,
                                 @Field("email") String email,
                                 @Field("provider") String provider,
                                 @Field("provider_user_id") String providerUserId);

    @POST("refresh")
    @FormUrlEncoded
    Call<AccessToken> refresh(@Field("refresh_token") String refreshToken);

    @POST("reservation")
    @FormUrlEncoded
    Call<ReservationJson> reservation(@Field("name") String name,
                                      @Field("nbperson") String nbperson,
                                      @Field("time") String time,
                                      @Field("approved") int approved,
                                      @Field("restaurant_id") int restaurant_id,
                                      @Field("user_id") int user_id
                                      );

    @PUT("reservation")
    @FormUrlEncoded
    Call<AcceptReservation> acceptReservation(@Field("reservation_id") int reservation_id, @Field("approved") int approved);

    @PUT("edit-user")
    @FormUrlEncoded
    Call<User> editUser(@Field("user_id") int user_id, @Field("name") String name, @Field("email") String email);


    @GET("user")
    Call<User> getUser();

    @POST("logout")
    Call<AccessToken> logout();

    @POST("rating")
    @FormUrlEncoded
    Call<Review> addReview(@Field("review") String review,
                           @Field("rating") String rating,
                           @Field("user_id") int user_id,
                           @Field("restaurant_id") int restaurant_id);

    @Multipart
    @POST("menu")
    Call<AccessToken> addMenu(@Part("food_name") RequestBody food_name, @Part("price") RequestBody price, @Part MultipartBody.Part photo);
}
