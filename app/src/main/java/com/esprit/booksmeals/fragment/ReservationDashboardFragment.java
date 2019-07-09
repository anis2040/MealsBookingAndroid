package com.esprit.booksmeals.fragment;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.esprit.booksmeals.actvity.GridSpacingItemDecoration;
import com.esprit.booksmeals.actvity.MyApplication;
import com.esprit.booksmeals.model.Reservation;
import booksmeals.R;
import com.esprit.booksmeals.adapter.ReservationAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationDashboardFragment extends Fragment {

    public ReservationDashboardFragment() {
        // Required empty public constructor
    }
    private RecyclerView recyclerView;
    private static final String URL = "http://mealsbooking.online/api/reservations";
    private List<Reservation> reservationList;
    private ReservationAdapter mAdapter;

    Fragment fragment;

    ProgressDialog progressDialog;
    private Handler mHandler;
    private boolean isLoaded = false;
    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            mHandler.postDelayed(m_Runnable, 2000);
            if (isLoaded) {
                progressDialog.hide();
                mHandler.removeCallbacksAndMessages(null);
            }
        }

    };




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_reservation_dashboard, container, false);
        this.mHandler = new Handler();
        this.mHandler.postDelayed(m_Runnable,2000);


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        recyclerView = root.findViewById(R.id.recycler_view_reservation);
        reservationList = new ArrayList<>();

        mAdapter = new ReservationAdapter(getActivity(), reservationList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        fetchStoreItems();
        setHasOptionsMenu(true);


        return root;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContainerDashboard, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void fetchStoreItems() {
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        List<Reservation> items = null;
                        try {
                            Log.e("response",response.get("data").toString());
                            items = new Gson().fromJson(response.get("data").toString(), new TypeToken<List<Reservation>>() {
                            }.getType());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        reservationList.clear();
                        reservationList.addAll(items);
                        Log.e("list:" , reservationList.size()+"");
                        // refreshing recycler view
                        mAdapter.notifyDataSetChanged();
                        isLoaded = true;
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        MyApplication.getInstance().addToRequestQueue(request);

    }





    public MenuInflater getMenuInflater() {
        return new MenuInflater(getActivity());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.navigation_discover:
                Toast.makeText(getActivity(), "Calls Icon Click ", Toast.LENGTH_SHORT).show();
                return true;
            
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}
