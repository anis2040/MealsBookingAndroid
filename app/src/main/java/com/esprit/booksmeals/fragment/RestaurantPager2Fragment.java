package com.esprit.booksmeals.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.esprit.booksmeals.actvity.MyApplication;
import com.esprit.booksmeals.model.Restaurant;
import booksmeals.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//import static ReservationFragment.photo2;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantPager2Fragment extends Fragment {
    public ImageView resto;
    private  ArrayList<Restaurant> restaurantList = new ArrayList<>();
    public   String photo2;
    private int id;


    public RestaurantPager2Fragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public RestaurantPager2Fragment(int id) {
      this.id= id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_restaurant_pager2, container, false);

        resto = view.findViewById(R.id.resto2);

//        Glide.with(getActivity())
//                .load(photo2)
//                .into(resto);
//
//        Log.d("image2",photo2);

        RestaurantById();

        return view;
    }





    private ArrayList<Restaurant> RestaurantById() {

        String url ="http://mealsbooking.online/api/restaurant/" + id;
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // List<Restaurant> items = null;
                        try {
                            Log.e("response",response.get("data").toString());
                          //  JSONObject jsonArray = response.getJSONObject("data");
                            JSONObject jsonArrayy = response.getJSONObject("data").getJSONObject("image");


                            photo2 = (String) jsonArrayy.get("photo2");
                            Log.d("photo1",photo2+"");

                            Glide.with(getActivity())
                                    .load(photo2)
                                    .into(resto);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });


        MyApplication.getInstance().addToRequestQueue(request);
        return restaurantList;
    }



}
