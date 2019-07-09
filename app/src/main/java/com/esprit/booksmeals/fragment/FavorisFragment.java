package com.esprit.booksmeals.fragment;


import android.app.Fragment;
import android.content.res.Resources;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.booksmeals.ExtraActivity.DeletionSwipeHelper;
import com.esprit.booksmeals.model.Favoris;
import com.esprit.booksmeals.Database.DB_Controller;
import booksmeals.R;
import com.esprit.booksmeals.adapter.FavorisAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavorisFragment extends Fragment implements DeletionSwipeHelper.OnSwipeListener {

    private RecyclerView recyclerView;
    public FavorisAdapter favoris_adapter;
    private ArrayList<Favoris> modelArrayList;



    DB_Controller controller;

    public FavorisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favoris, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_favoris);
        controller =new DB_Controller(view.getContext(),"",null,1);
        favoris_adapter = new FavorisAdapter(getActivity(),controller.display_favourites());
        favoris_adapter.notifyDataSetChanged();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(favoris_adapter);
        recyclerView.setNestedScrollingEnabled(false);
        ItemTouchHelper.Callback callback = new DeletionSwipeHelper(0, ItemTouchHelper.START, getActivity(), this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        ImageView btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });


        return view;

    }



    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int position) {


        try {
            controller =new DB_Controller(getActivity(),"",null,1);
            TextView tvNameResto = ((FavorisAdapter.ViewHolder)viewHolder).itemView.findViewById(R.id.tvNameResto);
            controller.delete_favoris(tvNameResto.getText().toString());
            ((FavorisAdapter.ViewHolder)viewHolder).removeItem(position);
            Toast.makeText(getActivity(), tvNameResto.getText()+" has been deleted from favourites" , Toast.LENGTH_SHORT).show();
        }catch(SQLiteException e ){
            Toast.makeText(getActivity(), "Already deleted" , Toast.LENGTH_SHORT).show();
        }
    }
}
