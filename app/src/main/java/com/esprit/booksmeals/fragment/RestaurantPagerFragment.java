package com.esprit.booksmeals.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
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

//import static ReservationFragment.photo1;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantPagerFragment extends Fragment {
    public ImageView resto;
    private  ArrayList<Restaurant> restaurantList = new ArrayList<>();
    public   String photo1;
    private int id;


    public RestaurantPagerFragment() {

    }

    @SuppressLint("ValidFragment")
    public RestaurantPagerFragment(int id) {
        this.id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_restaurant_pager, container, false);

         resto = view.findViewById(R.id.resto1);

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
                            JSONObject jsonArray = response.getJSONObject("data");
                            JSONObject jsonArrayy = response.getJSONObject("data").getJSONObject("image");

                            // JSONObject photoo = jsonArrayy.getJSONObject("photo1");
                             photo1 = (String) jsonArrayy.get("photo1");

//                            Log.e("ahhh",photo1+"");
//                            Log.e("baaa",photo2+"");
//                            Log.e("zaaaa",photo3+"");

                            Glide.with(getActivity())
                                    .load(photo1)
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
