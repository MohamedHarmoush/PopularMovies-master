package com.example.andriod.popularmovies;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Harmoush on 12/12/2017.
 */

public class MovieAppHelper  extends SQLiteOpenHelper {

    private static final String DB_NAME = "MovieDB.db";
    private static final int DB_Version = 1;
    private static final String ColumnType =" TEXT";
    private static final String LOG_TAG = MovieAppHelper.class.getName();

    private static final String SQL_DELETE_DATABASE = "DROP TABLE IF EXIST " + Contract.MovieTable.TABLE_NAME ;

    public MovieAppHelper(Context context) {
        super(context, DB_NAME, null, DB_Version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
         final String SQL_CREATE_DATABASE = "CREATE TABLE " +
                Contract.MovieTable.TABLE_NAME + "(" +
                Contract.MovieTable._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Contract.MovieTable.COULUMN_MOVIE_ID +" INTEGER, " +
                Contract.MovieTable.COULUMN_MOVIE_TITLE +" TEXT NOT NULL, "+
                Contract.MovieTable.COULUMN_MOVIE_RELEASEDATE+" TEXT NOT NULL, "+
                Contract.MovieTable.COULUMN_MOVIE_RATE +" TEXT NOT NULL, "+
                Contract.MovieTable.COULUMN_MOVIE_OVERVIEW + " TEXT NOT NULL, "+
                Contract.MovieTable.COULUMN_MOVIE_POSTERIMAGE + " TEXT NOT NULL, "+
                Contract.MovieTable.COULUMN_MOVIE_REVIEWS + " TEXT NOT NULL, "+
                Contract.MovieTable.COULUMN_MOVIE_TRAILERS + " TEXT NOT NULL "+" );";

        db.execSQL(SQL_CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_DATABASE);
        onCreate(db);

    }
}
