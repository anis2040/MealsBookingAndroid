package com.esprit.booksmeals.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import booksmeals.R;

import com.esprit.booksmeals.fragment.ReservationFragment;
import com.esprit.booksmeals.model.Restaurant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

class RecyclerViewHolderRestaurnt extends RecyclerView.ViewHolder  implements View.OnClickListener,View.OnLongClickListener {

    public TextView name, maxPrice,minPrice,address,id;
    public ImageView thumbnail;
    public ImageButton FavorisButtom;

    private ItemClickListener itemClickListener;
    public RecyclerViewHolderRestaurnt(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.title);
        maxPrice = itemView.findViewById(R.id.maxPrice);
        minPrice = itemView.findViewById(R.id.minPrice);
        address = itemView.findViewById(R.id.address);
        thumbnail = itemView.findViewById(R.id.thumbnail);
        FavorisButtom = itemView.findViewById(R.id.favoris);
        FavorisButtom.setOnLongClickListener(this);
        name.setOnClickListener(this);


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

public class RestaurantAdapter extends RecyclerView.Adapter<RecyclerViewHolderRestaurnt>  {
    private Context context;
    private List<Restaurant> restaurantList;

    public ImageButton FavorisButtom;
    public DB_Controller controller;
    TextView tvNameResto,tvAddress,tvReview,tvMinPrice,tvMaxPrice,tvDate;
    Restaurant restaurant;

    public static int idResto;

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
        public ImageButton FavorisButtom;
        public MyViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            name = view.findViewById(R.id.title);
            maxPrice = view.findViewById(R.id.maxPrice);
            minPrice = view.findViewById(R.id.minPrice);
            address = view.findViewById(R.id.address);
            thumbnail = view.findViewById(R.id.thumbnail);
            FavorisButtom = view.findViewById(R.id.favoris);

        }
    }

    public RestaurantAdapter(Context context, List<Restaurant> restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public RecyclerViewHolderRestaurnt onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item_row, parent, false);
        FavorisButtom = (ImageButton) itemView.findViewById(R.id.favoris);
        tvNameResto = itemView.findViewById(R.id.title);
        tvAddress = itemView.findViewById(R.id.address);
        return new RecyclerViewHolderRestaurnt(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    public void onBindViewHolder( RecyclerViewHolderRestaurnt holder,  int position) {
        restaurant = restaurantList.get(position );

        holder.name.setText(restaurant.getName());
        Glide.with(context)
                .load(restaurant.getPhoto())
                .into(holder.thumbnail);
        holder.address.setText(restaurant.getAddress());
        holder.minPrice.setText(Float.toString(restaurant.getMinPrice()) +" DT");
        holder.maxPrice.setText(Float.toString(restaurant.getMaxPrice()) +" DT");
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position, boolean isLongClick) {
                if(isLongClick){
                        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
                        String date = df.format(Calendar.getInstance().getTime());
                        try {
                            controller =new DB_Controller(v.getContext(),"",null,1);
                            controller.insert_favoris(restaurantList.get(position).getName(),restaurantList.get(position).getAddress(),restaurantList.get(position).getMinPrice(),restaurantList.get(position).getMaxPrice(),""+date,restaurantList.get(position).getPhoto());
                            Toast.makeText(v.getContext(), restaurantList.get(position).getName()+" has been added to your favourites" , Toast.LENGTH_SHORT).show();
                        }catch(SQLiteException e ){
                            Toast.makeText(v.getContext(), "Already exists" , Toast.LENGTH_SHORT).show();
                        }

                }
                else
                {
                    idResto = restaurantList.get(position).getId();
                    ReservationFragment   rf = new ReservationFragment(idResto);
                    FragmentManager manager = ((Activity) context).getFragmentManager();
                    manager.beginTransaction().replace(R.id.frame_container, rf).addToBackStack(null).commit();

                }
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

