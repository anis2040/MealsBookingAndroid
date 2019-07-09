package com.esprit.booksmeals.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.esprit.booksmeals.Database.DB_Controller;
import com.esprit.booksmeals.model.Favoris;
import booksmeals.R;

import java.util.ArrayList;


public class FavorisAdapter extends RecyclerView.Adapter<FavorisAdapter.ViewHolder> {


    ImageButton btnClose;
    TextView tvNameResto;
    DB_Controller controller;
    private Context context;
    private ArrayList<Favoris> modelArrayList;



    public FavorisAdapter(Context context, ArrayList<Favoris> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favoris_item,parent,false);

        btnClose =(ImageButton) view.findViewById(R.id.btnClose);
        tvNameResto =(TextView) view.findViewById(R.id.tvNameResto);



        return new ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Favoris favoris_model = modelArrayList.get(position);

        holder.tvNameResto.setText(favoris_model.getName());
        holder.tvAddress.setText(favoris_model.getAddress());
        holder.tvDate.setText(favoris_model.getDate());
        holder.tvMinPrice.setText(favoris_model.getMinPrice());
        holder.tvMaxPrice.setText(favoris_model.getMaxPrice());

        Glide.with(context)
                .load(favoris_model.getImg())
                .into(holder.favorisImage);


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    controller =new DB_Controller(v.getContext(),"",null,1);
                    controller.delete_favoris(tvNameResto.getText().toString());
                    modelArrayList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(v.getContext(), tvNameResto.getText()+" has been deleted from favourites" , Toast.LENGTH_SHORT).show();
                }catch(SQLiteException e ){
                    System.out.println("ghalta"+e);
                    Toast.makeText(v.getContext(), "Already deleted" , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNameResto,tvAddress,tvReview,tvMinPrice,tvMaxPrice,tvDate;
        ImageView favorisImage,History_Id;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNameResto = itemView.findViewById(R.id.tvNameResto);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvReview = itemView.findViewById(R.id.tvReview);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvMinPrice = itemView.findViewById(R.id.tvMinPrice);
            tvMaxPrice = itemView.findViewById(R.id.tvMaxPrice);
            favorisImage = itemView.findViewById(R.id.favorisImage);


        }

        public void removeItem(int position)
        {
            modelArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }
}
