package com.example.andriod.popularmovies;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Harmoush on 12/12/2017.
 */

public class Contract {

    public static final String CONTENT_AUTHORITY = "com.example.andriod.popularmovies";  // Authority
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES ="FavoriteMovies";
    public static final class MovieTable implements BaseColumns
    {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();
        public static final Uri URI_FOR_SPECIFIC_ROW = CONTENT_URI.buildUpon().appendPath("id").build();

        public static final String TABLE_NAME = "FavoriteMovies" ;

        public static final String COULUMN_MOVIE_ID ="MovieID";
        public static final String COULUMN_MOVIE_TITLE ="MovieTitle";
        public static final String COULUMN_MOVIE_RELEASEDATE ="MovieReleaseDate";
        public static final String COULUMN_MOVIE_RATE ="MovieRate";
        public static final String COULUMN_MOVIE_OVERVIEW ="MovieOverview";
        public static final String COULUMN_MOVIE_POSTERIMAGE ="MoviePosterImage";
        public static final String COULUMN_MOVIE_REVIEWS ="MovieReviews";
        public static final String COULUMN_MOVIE_TRAILERS ="MovieTrailers";

    }
}
