package com.esprit.booksmeals.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.esprit.booksmeals.model.Review;
import booksmeals.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>  {


    Context context;
    private ArrayList<Review> reviewsArrayList;



    public ReviewAdapter(Context context, ArrayList<Review> reviewsArrayList) {
        this.context = context;
        this.reviewsArrayList = reviewsArrayList;
    }


    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_item,viewGroup,false);
        return new ReviewAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Review review = reviewsArrayList.get(position);
        holder.tvReview.setText(review.getReview());
        holder.ratingBar.setRating(Float.parseFloat(review.getRating()));
        holder.user.setText(review.getUser());
        holder.timeReview.setText(review.getUser());
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        holder.timeReview.setText(date);
    }

    @Override
    public int getItemCount() {
        return reviewsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvReview,user;
        RatingBar ratingBar;
        TextView timeReview;
        public ViewHolder(View itemView) {
            super(itemView);
            tvReview = itemView.findViewById(R.id.tvReview);
            user = itemView.findViewById(R.id.tvUser);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            timeReview = itemView.findViewById(R.id.timeReview);
        }
    }
}

