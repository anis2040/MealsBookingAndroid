package com.esprit.booksmeals.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.esprit.booksmeals.model.Reservation;
import com.esprit.booksmeals.model.Restaurant;

import java.util.List;

import booksmeals.R;

public class ReservationCustomerAdapter extends RecyclerView.Adapter<ReservationCustomerAdapter.MyViewHolder> {


    private List<Reservation> reservationList;
    private List<Restaurant> restaurantList;
    Context context;

    @Override
    public ReservationCustomerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reservation_item, parent, false);

        return new ReservationCustomerAdapter.MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Reservation listsReservation = reservationList.get(position);
        Restaurant listsRestaurant = restaurantList.get(position);
        Glide.with(context)
                .load(listsRestaurant.getPhoto())
                .into(holder.resto_img);
        holder.resto_name.setText(listsRestaurant.getName());
        holder.namePersonTv.setText(listsReservation.getName());
        holder.timeTv.setText(listsReservation.getTime());
        holder.nbPersonTv.setText(listsReservation.getNbperson());
        if (listsReservation.getApproved() == 0){
            holder.approved.setBackgroundColor(Color.parseColor("#FF0000"));
        }
        else {
            holder.approved.setBackgroundColor(Color.parseColor("#00B873"));
        }


    }



    @Override
    public int getItemCount() {
        return reservationList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView resto_name,namePersonTv,timeTv,nbPersonTv;
        ImageView resto_img;
        View approved;

        public MyViewHolder(View view) {
            super(view);

            resto_img=(ImageView) view.findViewById(R.id.resto_img);
            resto_name=(TextView) view.findViewById(R.id.restoNameTv);
            namePersonTv=(TextView)view.findViewById(R.id.namePersonTv);
            timeTv=(TextView)view.findViewById(R.id.timeTv);
            nbPersonTv=(TextView) view.findViewById(R.id.nbPersonTv);
            approved = (View) view.findViewById(R.id.approved);

        }

    }


    public ReservationCustomerAdapter(Context context, List<Reservation> reservationList, List<Restaurant> restaurantList) {
        this.reservationList = reservationList;
        this.restaurantList = restaurantList;
        this.context = context;
    }



}
