package com.esprit.booksmeals.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import booksmeals.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingTableFragment extends Fragment {

    private String time[]= {"11:30","12:30","01:30","02:30","03:30"};

    public BookingTableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_table, container, false);
    }

}
