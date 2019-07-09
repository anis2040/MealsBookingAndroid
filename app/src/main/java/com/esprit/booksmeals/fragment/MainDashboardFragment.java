package com.esprit.booksmeals.fragment;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.esprit.booksmeals.TokenManager;
import com.esprit.booksmeals.actvity.WelcomeActivity;
import com.esprit.booksmeals.model.AccessToken;
import booksmeals.R;
import com.esprit.booksmeals.network.ApiService;
import com.esprit.booksmeals.network.RetroFitBuilder;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainDashboardFragment extends Fragment {
    LinearLayout restaurant,reservation,profile,logout,gallery;
    android.app.Fragment fragment;
    Call<AccessToken> callLogout;
    ApiService service;
    TokenManager tokenManager;
    public MainDashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main_dashboard, container, false);

        restaurant = (LinearLayout) root.findViewById(R.id.restaurant);
        reservation = (LinearLayout) root.findViewById(R.id.reservation);
        profile = (LinearLayout) root.findViewById(R.id.profile);
        gallery = (LinearLayout) root.findViewById(R.id.gallery);
        logout = (LinearLayout) root.findViewById(R.id.logout);

        tokenManager = TokenManager.getInstance(this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE));
        service = RetroFitBuilder.createServiceWithAuth(ApiService.class,tokenManager);

        if(tokenManager.getToken() == null){
            startActivity(new Intent(getActivity(), WelcomeActivity.class));
        }


        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new RestaurantDashboardFragment();
                loadFragment(fragment);
                Toast.makeText(getActivity(),"Restaurants list", Toast.LENGTH_LONG).show();

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment = new ProfileDashboardFragment();
                    loadFragment(fragment);
                    Toast.makeText(getActivity(),"My Profile", Toast.LENGTH_LONG).show();

                }
            });



        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AddMenuDashboardFragment
                fragment = new GalleryDashboardFragment();
                loadFragment(fragment);
                Toast.makeText(getActivity(),"Gallery", Toast.LENGTH_LONG).show();


            }
        });



        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ReservationDashboardFragment();
                loadFragment(fragment);
                Toast.makeText(getActivity(),"Reservations list", Toast.LENGTH_LONG).show();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callLogout = service.logout();
                callLogout.enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                        Log.w("Dashboard", "onResponse: " + response );
                        if(response.isSuccessful()){
                            tokenManager.deleteToken();
                            DynamicToast.makeWarning(getActivity(), "Logged out !").show();
                            startActivity(new Intent(getActivity(), WelcomeActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<AccessToken> call, Throwable t) {
                        Log.w("Dashboard", "onFailure: " + t.getMessage() );
                    }
                });
            }
        });

        return root;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContainerDashboard, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
