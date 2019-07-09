package com.esprit.booksmeals.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.esprit.booksmeals.actvity.MyApplication;
import com.esprit.booksmeals.model.Reservation;
import com.esprit.booksmeals.model.Restaurant;
import booksmeals.R;
import com.esprit.booksmeals.adapter.ReservationCustomerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.esprit.booksmeals.fragment.HomeFragment.user;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationCustomerFragment extends Fragment {

    private RecyclerView recyclerView;
    public    View view;

    private ReservationCustomerAdapter mAdapter;
    public JSONArray jsonReservation;


    public ArrayList<Reservation> reservationArrayList = new ArrayList<Reservation>();
    public ArrayList<Restaurant> listRestaurant = new  ArrayList<Restaurant>();
    public ReservationCustomerFragment() {
        // Required empty public constructor
    }

    private ArrayList<Reservation> Reservations() {

        String url ="http://mealsbooking.online/api/users";
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                response = jsonArray.getJSONObject(i);
                                jsonReservation = response.getJSONArray("reservations");
                                for (int j = 0; j < jsonReservation.length(); j++) {
                                    JSONObject jsonobject = jsonReservation.getJSONObject(j);
                                    int id = (int) jsonobject.get("id");
                                    String  name = (String) jsonobject.get("name");
                                    String  time = (String) jsonobject.get("time");
                                    String  nbperson = (String) jsonobject.get("nbperson");
                                    int  approved = (int) jsonobject.get("approved");
                                    int restaurant_id = (int) jsonobject.get("restaurant_id");
                                    int user_id = (int) jsonobject.get("user_id");
                                    JSONObject restoJson= jsonobject.getJSONObject("restaurant");
                                    int idResto = (int) restoJson.get("id");
                                    String nameResto = (String) restoJson.get("name");
                                    String imgResto = (String) restoJson.get("photo");
                                    if (user_id == user.getId()){
                                        if (restaurant_id == idResto){
                                            Restaurant newResto = new Restaurant(nameResto,imgResto);
                                            listRestaurant.add(newResto);
                                            Reservation newReservation = new Reservation(id,name,nbperson,time,approved,restaurant_id,user_id);
                                            reservationArrayList.add(newReservation);
                                        }
                                    }

                                }
                            }

                            mAdapter = new ReservationCustomerAdapter(getActivity(),reservationArrayList,listRestaurant);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);

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
        return reservationArrayList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          view =inflater.inflate(R.layout.fragment_reservation_customer, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        Reservations();

        ImageView btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("back","back");
                getFragmentManager().popBackStackImmediate();
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);


        return view;
    }

}
