package com.esprit.booksmeals.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import booksmeals.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Pub1Fragment extends Fragment {


    public Pub1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pub1, container, false);
    }

}
