package com.example.andriod.popularmovies;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Harmoush on 12/12/2017.
 */

public class MovieContract {
    public static final String CONTENT_AUTHORITY = "com.example.andriod.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class MovieTable implements BaseColumns
    {
        public static String Table_name = "FavoriteMovies" ;
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(Table_name).build();
        public static final Uri CONTENT_URI_ID = CONTENT_URI.buildUpon().appendPath("id").build();
        public static Movie favorite = new Movie();
    }
}
