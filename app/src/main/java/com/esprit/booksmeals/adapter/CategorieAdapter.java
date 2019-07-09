package com.esprit.booksmeals.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.esprit.booksmeals.fragment.ListCategorieFragment;
import com.esprit.booksmeals.model.Categorie;
import booksmeals.R;

import java.util.ArrayList;

public class CategorieAdapter extends RecyclerView.Adapter<CategorieAdapter.ViewHolder> {

    Context context;
    private ArrayList<Categorie> categorieArrayList;
    public ImageButton resimage;
    public Categorie categorie;
    public static String cat;
    int pos=-1;

    public CategorieAdapter(Context context, ArrayList<Categorie> cat) {
        this.context = context;

        this.categorieArrayList = cat;
    }



    @Override
    public CategorieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categorie_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategorieAdapter.ViewHolder holder, final int position) {

        categorie =  categorieArrayList.get(position);

        holder.resimage.setImageResource(categorie.getImage());
        holder.typeCuisine.setText(categorie.getName());


            holder.typeCuisine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cat = categorie.getName();
                    ListCategorieFragment lc = new ListCategorieFragment();
                    FragmentManager manager = ((Activity) context).getFragmentManager();
                    manager.beginTransaction().replace(R.id.frame_container, lc).addToBackStack(null).commit();


                }
            });

            holder.resimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cat = categorie.getName();
                    ListCategorieFragment lc = new ListCategorieFragment();
                    FragmentManager manager = ((Activity) context).getFragmentManager();
                    manager.beginTransaction().replace(R.id.frame_container, lc).addToBackStack(null).commit();

                }
            });




    }

    @Override
    public int getItemCount() {
        return categorieArrayList.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton resimage;
        public   TextView typeCuisine;
        public ViewHolder(View itemView) {
            super(itemView);
            resimage=itemView.findViewById(R.id.resimage);
            typeCuisine=itemView.findViewById(R.id.txtregulargift);
        }
    }
}
