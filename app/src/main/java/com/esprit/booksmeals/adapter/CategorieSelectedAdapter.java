package com.esprit.booksmeals.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.booksmeals.ExtraActivity.LeftRoundedCornersBitmap;
import booksmeals.R;
import com.esprit.booksmeals.model.Restaurant;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;


class RecyclerViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener,View.OnLongClickListener {


    public TextView text_name,text_location,text_menu2,maxPrice,minPrice;
    ImageView image;

    private ItemClickListener itemClickListener;
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        text_name =  itemView.findViewById(R.id.text_name);
        text_location =  itemView.findViewById(R.id.text_location);
        text_menu2 =  itemView.findViewById(R.id.text_menu2);
        image = itemView.findViewById(R.id.image);
        maxPrice = itemView.findViewById(R.id.maxPriceCat);
        minPrice = itemView.findViewById(R.id.minPriceCat);
        text_name.setOnClickListener(this);
        text_name.setOnLongClickListener(this);

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


public class CategorieSelectedAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements Filterable {
    private Context context;
    private List<Restaurant> categorieList;
    private List<Restaurant> restaurantListFull;




    public CategorieSelectedAdapter(Context context, List<Restaurant> categorieList) {
        this.context = context;
        this.categorieList = categorieList;
        restaurantListFull = new ArrayList<>(categorieList);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categorie_selected_item, parent, false);

        return new RecyclerViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
      Restaurant  restaurant = categorieList.get(position );

        holder.text_name.setText(restaurant.getName());
        holder.text_location.setText(restaurant.getAddress());
        holder.text_menu2.setText(restaurant.getCategory());
        try {
            holder.minPrice.setText(Float.toString(restaurant.getMinPrice()));
            holder.maxPrice.setText(Float.toString(restaurant.getMaxPrice()));
        }catch (Exception e){
        }

        Picasso.with(context).load(categorieList.get(position).getPhoto()).transform(new LeftRoundedCornersBitmap()).into(holder.image);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                    Toast.makeText(context,"Long click: "+categorieList.get(position),Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context," "+categorieList.get(position).getName(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount()   {return categorieList.size();}


    public void filterList(ArrayList<Restaurant> filteredList) {
        categorieList = filteredList;
        notifyDataSetChanged();
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter =new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Restaurant> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(restaurantListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Restaurant item : filteredList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }





        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            categorieList.clear();
            categorieList.addAll((List) results.values);
            notifyDataSetChanged();

        }

    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_name,text_location,text_menu2,minPrice,maxPrice;
        ImageView image;

        public ViewHolder(View view) {
            super(view);

            text_name =  view.findViewById(R.id.text_name);
            text_location =  view.findViewById(R.id.text_location);
            text_menu2 =  view.findViewById(R.id.text_menu2);
            image = view.findViewById(R.id.image);
            minPrice=  view.findViewById(R.id.minPriceCat);
            maxPrice=  view.findViewById(R.id.maxPriceCat);
        }
    }


}


