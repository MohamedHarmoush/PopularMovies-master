package com.example.andriod.popularmovies;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener {

    final static String BASIC_API_URL = "http://api.themoviedb.org/3/movie/";
    final static String API_KEY ="";

    private RecyclerView mMoviesRecyclerView;
    private MoviesAdapter adapter;
    private RecyclerView.ViewHolder holder;
    private GridLayoutManager mgridLayoutManager;
    private int numberOfViews = 2;
    private ProgressBar mLoadingProgressBar;
    private ArrayList<Movie> movies;
    private Menu menu;
    private String mSortType;

    String filmCategory;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMoviesRecyclerView = (RecyclerView)findViewById(R.id.rv_movies);
        movies = new ArrayList<>();
        adapter = new MoviesAdapter(movies,this);
        mgridLayoutManager = new GridLayoutManager(this,numberOfViews,GridLayoutManager.VERTICAL,false);
        mMoviesRecyclerView.setLayoutManager(mgridLayoutManager);
        mMoviesRecyclerView.setAdapter(adapter);
        mLoadingProgressBar =(ProgressBar)findViewById(R.id.pb_loading_indiactor);
       /* if((savedInstanceState != null)&&(savedInstanceState.getParcelable("movies"))!= null) {
            movies  = savedInstanceState.getParcelable("movies");
        }else {
            mSortType ="popular";
            fetchDataFromInternet(mSortType);
        }*/
        mSortType ="popular";
        fetchDataFromInternet(mSortType);
    }

   /* @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("movies", (Parcelable) movies);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        movies  = (ArrayList<Movie>) savedInstanceState.getParcelable("movies");
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_sort_by_most_popular:
                mSortType = "popular";
                break;
            case R.id.action_sort_by_top_rated:
                mSortType = "top_rated";
                break;
        }
        fetchDataFromInternet(mSortType);
        return true;
    }

    private void fetchDataFromInternet(String sortType)
    {

        mLoadingProgressBar.setVisibility(View.VISIBLE);
        movies.clear();

        Ion.with(this)
                .load(BASIC_API_URL + sortType + "?api_key="+API_KEY)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject  result) {
                        // do stuff with the result or error
                        if (e == null) {
                            JsonArray jsonArray= result.getAsJsonArray("results");
                            for(int i =0 ;i<jsonArray.size();i++) {
                                JsonObject movieJsonObject = jsonArray.get(i).getAsJsonObject();

                                String movieTitle = movieJsonObject.get("title").getAsString().replace("\"","");

                                String movieReleaseDate = movieJsonObject.get("release_date").getAsString().replace("\"","");
                                String movieRate = movieJsonObject.get("vote_average").getAsString().replace("\"","");
                                String movieOverview = movieJsonObject.get("overview").getAsString().replace("\"","");
                                String moviePosterImage = movieJsonObject.get("poster_path").getAsString().replace("\"","");
                                String movieId = movieJsonObject.get("id").getAsString().replace("\"","");

                                movies.add(new Movie(movieTitle, movieReleaseDate, movieRate, movieOverview, moviePosterImage,movieId));
                            }
                            adapter.notifyDataSetChanged();
                        }

                    }
                });

        mLoadingProgressBar.setVisibility(View.INVISIBLE);


    }

    @Override
    public void onListItemClickListener(int clikedItemIndex) {
        Movie movie = movies.get(clikedItemIndex);
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movie", movie);
        this.startActivity(intent);
    }
}
