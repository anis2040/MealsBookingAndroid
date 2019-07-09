package com.esprit.booksmeals.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.esprit.booksmeals.model.Restaurant;
import booksmeals.R;

import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class RestaurantDashboardAdapter extends RecyclerView.Adapter<RestaurantDashboardAdapter.MyViewHolder>  {
    private Context context;
    private List<Restaurant> restaurantList;
    Restaurant restaurant;

    public static int id;

    public static boolean showingFirst = true;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnItemClick(int position);
        void OnDeleteClick(int position);
        public void recyclerViewListClicked(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener= listener;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder   {
        public TextView name, maxPrice,minPrice,address,id;
        public ImageView thumbnail;
        public ImageView closeImg;
        public MyViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            name = view.findViewById(R.id.title);
            maxPrice = view.findViewById(R.id.maxPrice);
            minPrice = view.findViewById(R.id.minPrice);
            address = view.findViewById(R.id.address);
            thumbnail = view.findViewById(R.id.thumbnail);
            id = view.findViewById(R.id.id);
            closeImg = view.findViewById(R.id.closeImg);
        }


    }


    public RestaurantDashboardAdapter(Context context, List<Restaurant> restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_dashboard_item_row, parent, false);
        return new MyViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(final RestaurantDashboardAdapter.MyViewHolder holder, int position) {
        restaurant = restaurantList.get(position);
        holder.name.setText(restaurant.getName());
        Glide.with(context)
                .load(restaurant.getPhoto())
                .into(holder.thumbnail);
        holder.address.setText(restaurant.getAddress());
        holder.id.setText(Integer.toString(restaurant.getId()));
        holder.minPrice.setText(Float.toString(restaurant.getMinPrice()));
        holder.maxPrice.setText(Float.toString(restaurant.getMaxPrice()));
        holder.closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PrettyDialog pDialog = new PrettyDialog(context);
                pDialog
                        .setIcon(R.drawable.pdlg_icon_info)
                        .setIconTint(R.color.pdlg_color_red)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this item?")
                        .addButton(
                                "Yes",
                                R.color.pdlg_color_white,
                                R.color.pdlg_color_red,
                                new PrettyDialogCallback() {
                                    @Override
                                    public void onClick() {
                                        pDialog.dismiss();
                                    }
                                }
                        )
                        .addButton(
                                "No",
                                R.color.pdlg_color_white,
                                R.color.pdlg_color_green,
                                new PrettyDialogCallback() {
                                    @Override
                                    public void onClick() {
                                        pDialog.dismiss();
                                    }
                                }
                        )
                        .show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public void loadFragmentFromAdapter(Fragment fragment) {
        FragmentManager manager = ((Activity)context).getFragmentManager();
        manager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null)
                .commit();
    }



}

