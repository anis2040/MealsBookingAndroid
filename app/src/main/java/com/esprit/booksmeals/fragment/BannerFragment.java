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
public class BannerFragment extends Fragment {


    public BannerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view= inflater.inflate(R.layout.fragment_banner, container, false);

        return view;
    }

}
