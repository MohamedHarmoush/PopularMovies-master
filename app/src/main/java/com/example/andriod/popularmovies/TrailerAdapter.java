package com.example.andriod.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Harmoush on 12/9/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailersViewHolder>{

    private Context context;
    private ArrayList<String> trailers;
    private TrailerAdapter.ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClickListener(int clikedItemIndex);
    }

    public TrailerAdapter(ArrayList<String> trailerList,TrailerAdapter.ListItemClickListener listener){
        mOnClickListener = listener;
        trailers = trailerList;
    }
    public TrailerAdapter(ArrayList<String> trailerList){
        trailers = trailerList;
    }
    @Override
    public TrailersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        TrailerAdapter.TrailersViewHolder holder = new TrailerAdapter.TrailersViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final TrailersViewHolder holder, final int position) {
        final String trailer = trailers.get(position);
        Picasso.with( holder.itemView.getContext())
                .load("https://img.youtube.com/vi/"+trailer +"/0.jpg")
                .into(holder.imageView);
       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onListItemClickListener(position);
                Uri uri = Uri.parse("https://www.youtube.com/watch?v="+trailer);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                v.getContext().startActivity(intent);
            }
        });*/
    }
    public void updateList(ArrayList<String> data) {
        trailers = data;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if(trailers.size() == 0)
            return 0;
        else
            return trailers.size();
    }

    public class TrailersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        //String trailerId;
        public TrailersViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.iv_trailer);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClickListener(clickedPosition);
        }
    }
}

