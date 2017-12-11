package com.example.andriod.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Harmoush on 12/10/2017.
 */

public class ReviewAdaptor extends RecyclerView.Adapter<ReviewAdaptor.ReviewsViewHolder> {

    private Context context;
    private ArrayList<Movie.Review> mReviews;

    public ReviewAdaptor(ArrayList<Movie.Review> reviews){
        mReviews = reviews;
    }
    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        ReviewAdaptor.ReviewsViewHolder holder = new ReviewAdaptor.ReviewsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( ReviewAdaptor.ReviewsViewHolder holder, final int position) {
        Movie.Review review = mReviews.get(position);
        holder.author.setText(review.getReviewAuthor());
        holder.content.setText(review.getReviewContent());
    }

    @Override
    public int getItemCount() {
        if(mReviews.size() == 0)
            return 0;
        else
            return mReviews.size();
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {

        TextView author;
        TextView content;
        public ReviewsViewHolder(View itemView) {
            super(itemView);
            author = (TextView)itemView.findViewById(R.id.tv_review_author);
            content = (TextView)itemView.findViewById(R.id.tv_review_content);
        }
    }
}
