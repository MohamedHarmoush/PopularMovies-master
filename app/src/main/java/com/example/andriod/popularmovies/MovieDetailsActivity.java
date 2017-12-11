package com.example.andriod.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;


import org.w3c.dom.Text;

import java.util.ArrayList;



public class MovieDetailsActivity extends AppCompatActivity implements TrailerAdapter.ListItemClickListener{


    final static String BASIC_API_URL = "http://api.themoviedb.org/3/movie/";
    final static String API_KEY ="e7f270eacb1f59d05e70d319d0af3f96";

    ///gs sg
    private Movie mMovie;
    private TextView mMovieTitle;
    private TextView mMovieRate;
    private TextView mMovieReleaseDate;
    private TextView mMovieOverview;
    private ImageView mMoviePoster;
    private RatingBar rb_rating;
    private ArrayList<String> mMovietrailers;
    private ArrayList<Movie.Review> mMovieReviews;
    private RecyclerView trailerRecyclerView;
    private TrailerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView mNoReview;

    private RecyclerView reviewRecyclerView;
    private ReviewAdaptor adapter2;
    private RecyclerView.LayoutManager layoutManager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        mMovie = getIntent().getParcelableExtra("movie");
        mMovieTitle = (TextView)findViewById(R.id.tv_movie_title);
        mMovieTitle.setText(mMovie.getMovieTitle());
        mMovieRate = (TextView)findViewById(R.id.tv_movie_rate);
        mMovieRate.setText(mMovie.getMovieRate()+" \10");
        mMovieReleaseDate = (TextView)findViewById(R.id.tv_movie_release_date);
        mMovieReleaseDate.setText(mMovie.getMovieReleaseDate());
        mMovieOverview = (TextView)findViewById(R.id.tv_overview);
        mMovieOverview.setMovementMethod( new ScrollingMovementMethod());
        mMovieOverview.setText(mMovie.getMovieOverview());
        mMoviePoster = (ImageView)findViewById(R.id.iv_movie_image);
        rb_rating = (RatingBar)findViewById(R.id.ratingBar);
        rb_rating.setRating(Float.parseFloat(mMovie.getMovieRate())/2);
        Picasso.with(MovieDetailsActivity.this).load(mMovie.getMoviePosterImage()).into(mMoviePoster);


        trailerRecyclerView = (RecyclerView)findViewById(R.id.rv_movie_trailers);
        mMovietrailers = new ArrayList<>();
        adapter = new TrailerAdapter(mMovietrailers,this);
        layoutManager = new GridLayoutManager(this,1,GridLayoutManager.VERTICAL, false);
        trailerRecyclerView.setAdapter(adapter);
        mNoReview = (TextView) findViewById(R.id.tv_no_review_avaliable);
        fetchTrailers(mMovie.getMovieId());

        reviewRecyclerView = (RecyclerView)findViewById(R.id.rv_movie_reviews);
        mMovieReviews = new ArrayList<>();
        adapter2 = new ReviewAdaptor(mMovieReviews);
        layoutManager2 = new GridLayoutManager(this,1, GridLayoutManager.VERTICAL, false);
        reviewRecyclerView.setAdapter(adapter2);
        fetchReviews(mMovie.getMovieId());

    }

  /*  @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("Trailers", (Parcelable) mMovietrailers);
        outState.putSerializable("Reviews",mMovieReviews);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //mMovietrailers = savedInstanceState.getSerializable("Trailers");
        //mMovieReviews = savedInstanceState.getSerializable("")
    }*/

    private TrailerAdapter.ListItemClickListener setListner(){
        return  this;
    }
    private  boolean fetchTrailers(String movieId) {
        String url = BASIC_API_URL + movieId + "/videos"+ "?api_key="+API_KEY;
        return Ion.with(this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject results) {
                        if(e == null){
                            JsonArray jsonArray = results.getAsJsonArray("results");
                            for (int i =0 ;i<jsonArray.size();i++){
                                JsonObject movieJsonObject = jsonArray.get(i).getAsJsonObject();
                                String movieKey = movieJsonObject.get("key").getAsString().replace("\"","");
                                Log.i("movieTrailer",movieKey+"\n");
                                mMovietrailers.add(movieKey);
                            }

                            trailerRecyclerView.setLayoutManager(layoutManager);
                            TrailerAdapter.ListItemClickListener listner = setListner();
                            adapter = new TrailerAdapter(mMovietrailers,listner);
                                //adapter.notifyDataSetChanged();
                                trailerRecyclerView.setAdapter(adapter);


                        }
                    }
                }).isDone();

    }
    private  void fetchReviews(String movieId) {
        Ion.with(this)
                .load(BASIC_API_URL +movieId + "/reviews"+ "?api_key="+API_KEY)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(e == null){
                            JsonArray jsonArray = result.getAsJsonArray("results");
                            for (int i =0 ;i<jsonArray.size();i++){
                                JsonObject movieJsonObject = jsonArray.get(i).getAsJsonObject();
                                String reviewAuthor = movieJsonObject.get("author").getAsString().replace("\"","");
                                String reviewContent = movieJsonObject.get("content").getAsString().replace("\"","");
                                mMovieReviews.add(new Movie.Review(reviewAuthor,reviewContent));

                            }
                            if(mMovieReviews.size() !=0) {
                                adapter2 = new ReviewAdaptor(mMovieReviews);
                                reviewRecyclerView.setLayoutManager(layoutManager2);
                                reviewRecyclerView.setAdapter(adapter2);
                            }else
                                mNoReview.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
    @Override
    public void onListItemClickListener(int clikedItemIndex) {
        String trailerKey = mMovietrailers.get(clikedItemIndex);
        Uri uri = Uri.parse("https://www.youtube.com/watch?v="+trailerKey);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }
}
