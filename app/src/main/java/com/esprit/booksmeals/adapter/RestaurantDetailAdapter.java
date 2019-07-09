package com.esprit.booksmeals.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import booksmeals.R;
import com.esprit.booksmeals.model.RestaurantDetail;

import java.util.ArrayList;

public class RestaurantDetailAdapter extends RecyclerView.Adapter<RestaurantDetailAdapter.ViewHolder> {

    Context context;
    private ArrayList<RestaurantDetail> modelArrayList;

    public RestaurantDetailAdapter(Context context, ArrayList<RestaurantDetail> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public RestaurantDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_detail_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantDetailAdapter.ViewHolder holder, int position) {
        RestaurantDetail food5_detail_model = modelArrayList.get(position);
        holder.FoodImage_Id.setImageResource(food5_detail_model.getFoodImage_Id());

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView FoodImage_Id;
        public ViewHolder(View itemView) {
            super(itemView);

            FoodImage_Id = itemView.findViewById(R.id.FoodImage_Id);
        }
    }

}