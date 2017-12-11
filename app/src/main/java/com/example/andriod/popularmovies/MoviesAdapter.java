package com.example.andriod.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harmoush on 10/11/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {


    private Context context;
    private ArrayList<Movie> movies;
    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClickListener(int clikedItemIndex);
    }

    public MoviesAdapter(ArrayList<Movie> movieList,ListItemClickListener listener){
        mOnClickListener = listener;
        movies = movieList;
    }
    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        MoviesViewHolder holder = new MoviesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MoviesViewHolder holder, final int position) {
        final Movie movie = movies.get(position);
        Picasso.with(context).load(movie.getMoviePosterImage()).into(holder.mMoviePoster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onListItemClickListener(position);
            }
        });
       // holder.mMoviePoster.setImageURI(Uri.parse(movie.getMoviePosterImage()));
       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("movie", movie);
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        if(movies.size() ==0 )
            return 0;
        else
            return movies.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView mMoviePoster;
        public MoviesViewHolder(View itemView) {
            super(itemView);
            mMoviePoster = (ImageView)itemView.findViewById(R.id.movie_poster);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClickListener(clickedPosition);
        }
    }
}
