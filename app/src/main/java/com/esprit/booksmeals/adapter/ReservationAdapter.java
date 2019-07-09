package com.esprit.booksmeals.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.booksmeals.model.AcceptReservation;
import com.esprit.booksmeals.model.Reservation;
import com.esprit.booksmeals.network.ApiService;
import com.esprit.booksmeals.network.RetroFitBuilder;
import booksmeals.R;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class RecyclerViewHolderReservation extends RecyclerView.ViewHolder  implements View.OnClickListener,View.OnLongClickListener {

    public TextView name, nbperson,time,id;
    Button acceptBtn;
    Button refuseBtn;
    View approved;
    private ItemClickListener itemClickListener;
    public RecyclerViewHolderReservation(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.tvName);
        nbperson = itemView.findViewById(R.id.tvNbPerson);
        time = itemView.findViewById(R.id.TvTime);
        acceptBtn = (Button) itemView.findViewById(R.id.acceptBtn);
        refuseBtn = (Button) itemView.findViewById(R.id.refuseBtn);
        approved = (View) itemView.findViewById(R.id.approved);
        acceptBtn.setOnClickListener(this);
        refuseBtn.setOnLongClickListener(this);


    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onItemClick(v,getAdapterPosition(),false);

    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onItemClick(v,getAdapterPosition(),true);
        return false;
    }
}

public class ReservationAdapter extends RecyclerView.Adapter<RecyclerViewHolderReservation> {

    private Context context;
    private List<Reservation> reservationList;
    public TextView name, nbperson,time;
    private int id;
    public Reservation reservation;
    ApiService service;
    Call<AcceptReservation> call;
    Button acceptBtn;
    Button refuseBtn;
    View approved;

    public ReservationAdapter(Context context, List<Reservation> reservationList) {
        this.context = context;
        this.reservationList = reservationList;
    }

    @NonNull
    @Override
    public RecyclerViewHolderReservation onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dashboard_reservation_item_row, viewGroup, false);
        service = RetroFitBuilder.createService(ApiService.class);
        name =  itemView.findViewById(R.id.tvName);
        nbperson = itemView.findViewById(R.id.tvNbPerson);
        time = itemView.findViewById(R.id.TvTime);
        acceptBtn = (Button) itemView.findViewById(R.id.acceptBtn);
        refuseBtn = (Button) itemView.findViewById(R.id.refuseBtn);
        approved = (View) itemView.findViewById(R.id.approved);


        return new RecyclerViewHolderReservation(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolderReservation myViewHolder, int i) {
        reservation = reservationList.get(i);
        myViewHolder.name.setText(reservation.getName());
        myViewHolder.nbperson.setText((reservation.getNbperson()));
        myViewHolder.time.setText((reservation.getTime()));
        if (reservation.getApproved() == 0){
            myViewHolder.approved.setBackgroundColor(Color.parseColor("#FF0000"));
        }
        else {
            myViewHolder.approved.setBackgroundColor(Color.parseColor("#00B873"));
        }
        myViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position, boolean isLongClick) {
                if (isLongClick){
                    id = reservationList.get(position).getId();
                    call = service.acceptReservation(id,0 );
                    call.enqueue(new Callback<AcceptReservation>() {
                        @Override
                        public void onResponse(Call<AcceptReservation> call, Response<AcceptReservation> response) {
                            if (response.isSuccessful()) {
                                DynamicToast.makeError(context, "Refused", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }
                        }
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onFailure(Call<AcceptReservation> call, Throwable t) {
                        }
                    });
                }else{
                    id = reservationList.get(position).getId();
                    call = service.acceptReservation(id,1 );
                    call.enqueue(new Callback<AcceptReservation>() {
                        @Override
                        public void onResponse(Call<AcceptReservation> call, Response<AcceptReservation> response) {
                            if (response.isSuccessful()) {
                                DynamicToast.makeSuccess(context, "Confirmed", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }
                        }
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onFailure(Call<AcceptReservation> call, Throwable t) {
                        }
                    });
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }


}
