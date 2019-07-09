package com.esprit.booksmeals.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.booksmeals.ExtraActivity.LeftRoundedCornersBitmap;
import com.esprit.booksmeals.fragment.ReservationFragment;
import com.esprit.booksmeals.model.Restaurant;
import booksmeals.R;
import com.squareup.picasso.Picasso;

import java.util.List;


class RecyclerViewHolderTopTen extends RecyclerView.ViewHolder  implements View.OnClickListener,View.OnLongClickListener {
    ImageView imagee;
    TextView dish_name;
    TextView price,maxPrice,minPrice;;
    private ItemClickListener itemClickListener;
    public RecyclerViewHolderTopTen(@NonNull View itemView) {
        super(itemView);
        dish_name = (TextView) itemView.findViewById(R.id.tv_dish_name);
        imagee = (ImageView) itemView.findViewById(R.id.imagee);
        price = (TextView) itemView.findViewById(R.id.tv_price);
        maxPrice = itemView.findViewById(R.id.maxPrice);
        minPrice = itemView.findViewById(R.id.minPrice);
        dish_name.setOnClickListener(this);
        dish_name.setOnLongClickListener(this);
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


public class TopTenAdapter extends RecyclerView.Adapter<RecyclerViewHolderTopTen>  {
    private List<Restaurant> restaurantList;
    private Context context;
    private Restaurant restaurant;
    public int id;




    public TopTenAdapter(Context context,List<Restaurant> moviesList) {
        this.context = context;
        this.restaurantList = moviesList;
    }

    @Override
    public RecyclerViewHolderTopTen onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_top_ten_list, parent, false);
        return new RecyclerViewHolderTopTen(itemView);

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onBindViewHolder(RecyclerViewHolderTopTen holder, int position) {

        restaurant = restaurantList.get(position);

        holder.dish_name.setText(restaurant.getName());
        holder.price.setText(restaurant.getAddress());
        Picasso.with(context).load(restaurantList.get(position).getPhoto()).transform(new LeftRoundedCornersBitmap()).into(holder.imagee);

        holder.minPrice.setText(Float.toString(restaurant.getMinPrice()));
        holder.maxPrice.setText(Float.toString(restaurant.getMaxPrice()));
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position, boolean isLongClick) {
                if(isLongClick){
                    id = restaurantList.get(position).getId();
                    ReservationFragment rf = new ReservationFragment(id);
                    FragmentManager manager = ((Activity) context).getFragmentManager();
                    manager.beginTransaction().replace(R.id.frame_container, rf).addToBackStack(null).commit();

                }
                else
                {
                    id = restaurantList.get(position).getId();
                    ReservationFragment rf = new ReservationFragment(id);
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




    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imagee;
        TextView dish_name;
        TextView price;
        public TextView  maxPrice,minPrice;
        public MyViewHolder(View view) {
            super(view);
            imagee = (ImageView) view.findViewById(R.id.imagee);
            dish_name = (TextView) view.findViewById(R.id.tv_dish_name);
            price = (TextView) view.findViewById(R.id.tv_price);
            maxPrice = view.findViewById(R.id.maxPrice);
            minPrice = view.findViewById(R.id.minPrice);
        }

    }
}
