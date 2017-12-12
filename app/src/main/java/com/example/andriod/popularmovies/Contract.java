package com.example.andriod.popularmovies;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Harmoush on 12/12/2017.
 */

public class Contract {
    public static final class MovieTable implements BaseColumns
    {
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
