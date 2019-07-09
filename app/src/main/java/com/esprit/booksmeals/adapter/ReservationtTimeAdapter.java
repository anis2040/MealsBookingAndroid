package com.esprit.booksmeals.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esprit.booksmeals.model.Reservation;
import booksmeals.R;

import java.util.List;

public class ReservationtTimeAdapter extends RecyclerView.Adapter<ReservationtTimeAdapter.MyViewHolder>{

    private List<Reservation> reservationTimeList;
    private Context context;
    public static String timeHours;
    int pos = -1;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView time;
        LinearLayout linearLayout;



        public MyViewHolder(View view) {
            super(view);
            time = (TextView) view.findViewById(R.id.time);
            linearLayout = (LinearLayout) view.findViewById(R.id.linear);

        }

        @Override
        public void onClick(View v) {
        }
    }



    public ReservationtTimeAdapter(Context context, List<Reservation> reservationTimeList) {
        this.reservationTimeList = reservationTimeList;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_time_item, parent, false);
        return new MyViewHolder(itemView);


    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Reservation reservation = reservationTimeList.get(position);
        holder.time.setText(reservation.getTime().toString());

        if (pos == position){

            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.time.setTextColor(Color.parseColor("#373737"));
            timeHours = reservation.getTime();
        }
        else  {
            holder.linearLayout.setVisibility(View.GONE);
            holder.time.setTextColor(Color.parseColor("#a5a5a5"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pos = position;
                notifyDataSetChanged();

            }
        });

    }


    @Override
    public int getItemCount() {
        return reservationTimeList.size();
    }


}
