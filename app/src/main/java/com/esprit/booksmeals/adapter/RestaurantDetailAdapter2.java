package com.esprit.booksmeals.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.esprit.booksmeals.model.Menu;
import booksmeals.R;
import com.makeramen.roundedimageview.RoundedImageView;


import java.util.ArrayList;



public class RestaurantDetailAdapter2 extends RecyclerView.Adapter<RestaurantDetailAdapter2.ViewHolder> {

    Context context;
    private ArrayList<Menu> modelArrayList;

    public RestaurantDetailAdapter2(Context context, ArrayList<Menu> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public RestaurantDetailAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_detail_item2,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Menu food5_detail_model2 = modelArrayList.get(position);

        holder.Food5_RestaurantName_Id2.setText(food5_detail_model2.getFood_name());
        holder.price.setText(String.valueOf(food5_detail_model2.getPrice())+ " DT");
        Glide.with(context)
                .load(food5_detail_model2.getPhoto())
                .into(holder.food_image);

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Food5_RestaurantName_Id2,price;
        RoundedImageView food_image;
        public ViewHolder(View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.price);
            Food5_RestaurantName_Id2 = itemView.findViewById(R.id.Food5_RestaurantName_Id2);
            food_image = itemView.findViewById(R.id.food_image);

        }
    }
}
