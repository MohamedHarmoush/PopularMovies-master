package com.example.andriod.popularmovies;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Harmoush on 12/12/2017.
 */

public class MovieAppHelper  extends SQLiteOpenHelper {

    private static final String DB_NAME = "MovieDB";
    private static final int DB_Version = 1;
    private static final String ColumnType =" TEXT";
    private static final String LOG_TAG = MovieAppHelper.class.getName();
    private static final String SQL_CREATE_DATABASE = "CREATE TABLE MovieDB (\n" +
            "    Id               INTEGER    PRIMARY KEY ASC,\n" +
            "    movieId          TEXT (20)," +
            "    movieTitle       TEXT (50)," +
            "    movieReleaseDate TEXT (20)," +
            "    movieRate        TEXT (20)," +
            "    movieOverview    TEXT (500)," +
            "    moviePosterImage TEXT (50)," +
            "    movieReviews     TEXT (500)," +
            "    movieTrailers    TEXT (300) " +
            ");\n" +
            "CREATE TABLE "+ "FavoriteMovies"+ " ( "+
          /*  MovieContract.MovieTable.favorite.movieId + ColumnType +
            "," + MovieContract.MovieTable.favorite.movieTitle+ ColumnType +
            MovieContract.MovieTable.favorite.movieRate + ColumnType +
            "," + MovieContract.MovieTable.favorite.moviePosterImage + ColumnType +
            "," + MovieContract.MovieTable.favorite.movieReviews_ + ColumnType+
            "," + MovieContract.MovieTable.favorite.movieTrailers_ + ColumnType +
            "," + MovieContract.MovieTable.favorite.movieReleaseDate+ ColumnType +
            "," + MovieContract.MovieTable.favorite.movieOverview+ ColumnType  +*/" ); ";

    private static final String SQL_DELETE_DATABASE = "DROP TABLE IF EXIST " + "FavoriteMovies" ;

    public MovieAppHelper(Context context) {
        super(context, DB_NAME, null, DB_Version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL(SQL_DELETE_DATABASE);
            onCreate(db);
        }
    }
}
