package com.esprit.booksmeals.fragment;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import booksmeals.R;
import com.esprit.booksmeals.adapter.MenuStaggeredRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryDashboardFragment extends Fragment {

    private static final String TAG = "MainActivity";
    private static final int NUM_COLUMNS = 4;
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();
    View view;
    LinearLayout plus;
    Fragment fragment;

    public GalleryDashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_gallery_dashboard, container, false);
        initImageBitmaps();
        plus = (LinearLayout) view.findViewById(R.id.linear_filter);
        plus.findViewById(R.id.linear_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new AddMenuDashboardFragment();
                loadFragment(fragment);
                Toast.makeText(getActivity(),"Gallery", Toast.LENGTH_LONG).show();
            }
        });



        return view;
    }
    private void initImageBitmaps(){
        mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mNames.add("Havasu Falls");

        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Trondheim");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Portugal");

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Rocky Mountain National Park");


        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Mahahual");

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("Frozen Lake");


        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mNames.add("White Sands Desert");

        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mNames.add("Austrailia");

        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        mNames.add("Washington");

        initRecyclerView();

    }


    private void initRecyclerView(){
        RecyclerView recyclerView = view.findViewById(R.id.recyclerv_view);
        MenuStaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter =
                new MenuStaggeredRecyclerViewAdapter(getActivity(), mNames, mImageUrls);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(staggeredRecyclerViewAdapter);
    }



    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContainerDashboard, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
