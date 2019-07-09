package com.esprit.booksmeals.fragment;


import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.esprit.booksmeals.TokenManager;
import com.esprit.booksmeals.actvity.MainActivity;
import com.esprit.booksmeals.adapter.RestaurantAdapter;
import com.esprit.booksmeals.adapter.SpinnerDataAdapter;
import com.esprit.booksmeals.model.ItemData;
import com.esprit.booksmeals.model.Reservation;
import com.esprit.booksmeals.model.ReservationJson;
import booksmeals.R;
import com.esprit.booksmeals.adapter.ReservationtTimeAdapter;
import com.esprit.booksmeals.network.ApiService;
import com.esprit.booksmeals.network.RetroFitBuilder;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.esprit.booksmeals.adapter.ReservationtTimeAdapter.timeHours;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reservation2Fragment extends Fragment {

    private String time[]= {"11:30","12:30","01:30","02:30","03:30"};
    ApiService service;
    TokenManager tokenManager;
    Call<ReservationJson> call;
    String day;


    private ArrayList<Reservation> timeReservationList;

    private RecyclerView recyclerView;
    private ReservationtTimeAdapter mAdapter;
    TextView tvBook;
    Button tvOK;


   private CompactCalendarView compactcalendar_view;

    public Reservation2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          View view= inflater.inflate(R.layout.fragment_reservation2, container, false);
        compactcalendar_view= (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
        service = RetroFitBuilder.createService(ApiService.class);

        tvBook = (TextView) view.findViewById(R.id.BtnBook);

        ImageView btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("back","back");
                getFragmentManager().popBackStackImmediate();
            }
        });


        tvBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String finaltime = day + timeHours;
                Log.e("time: ",finaltime + "");
                Log.e("nb personn", SpinnerDataAdapter.nbPerson);


                call = service.reservation("Anis", SpinnerDataAdapter.nbPerson,finaltime,0, RestaurantAdapter.idResto, HomeFragment.user.getId() );
                call.enqueue(new Callback<ReservationJson>() {
                    @Override
                    public void onResponse(Call<ReservationJson> call, Response<ReservationJson> response) {

                        if (response.isSuccessful()) {
                            Log.e("yeessss",response.body().toString());
                        }
                    }
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onFailure(Call<ReservationJson> call, Throwable t) {
                        Log.w( "onFailure: " , t.getMessage());
                    }
                });

                Dialog myDialog = new Dialog(getActivity());
                myDialog.getWindow();
                myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                myDialog.setCancelable(true);
                myDialog.setContentView(R.layout.confirmation_reservation);
                TextView okButton = (TextView) myDialog.findViewById(R.id.okBtn);

                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(getActivity(), MainActivity.class);
                        startActivity(myIntent);
                    }
                });


                myDialog.show();
                Button tvOK = (Button) myDialog.findViewById(R.id.okBtn);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(getActivity(), MainActivity.class);
                        startActivity(myIntent);
                    }
                });

            }
        });

        compactcalendar_view.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {


                DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
                String date = df.format(dateClicked);
              day = date;
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }
        });


        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);

        ArrayList<ItemData> list = new ArrayList<>();

        list.add(new ItemData("Select Person", R.drawable.ic_copuple));
        list.add(new ItemData("1 person", R.drawable.ic_copuple));
        list.add(new ItemData("2 Person", R.drawable.ic_copuple));
        list.add(new ItemData("3 Person", R.drawable.ic_copuple));
        list.add(new ItemData("5 Person", R.drawable.ic_copuple));
        Spinner sp = (Spinner) view.findViewById(R.id.spinner);

        SpinnerDataAdapter adapter = new SpinnerDataAdapter(getActivity(), R.layout.spinner_list, R.id.data, list);
        sp.setAdapter(adapter);
        spinner.setAdapter(adapter);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        timeReservationList = new ArrayList<>();



        for (int i = 0; i < time.length; i++) {
            Reservation beanClassForRecyclerView_contacts = new Reservation(time[i]);
            timeReservationList.add(beanClassForRecyclerView_contacts);
        }

        mAdapter = new ReservationtTimeAdapter(getActivity(),timeReservationList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


          return view;
    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public interface RecyclerViewClickListener {
        public void recyclerViewListClicked(View v, int position);
    }



    public void recyclerViewListClicked(View v, int position){

    }



}
