package com.example.andriod.popularmovies;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Harmoush on 12/12/2017.
 */

public class MovieContentProvider extends ContentProvider {

    private MovieAppHelper mMovieAppHelper;
    private SQLiteDatabase db;
    private static final UriMatcher uriMatcher = buildUriMatcher();

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;
        // add two matcher in Local Match one for Table and the Another for Columns
        matcher.addURI(authority, MovieContract.MovieTable.Table_name, 10);
        matcher.addURI(authority, MovieContract.MovieTable.Table_name + "/id", 15);
        return matcher;
    }
    @Override
    public boolean onCreate() {
        mMovieAppHelper = new MovieAppHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        db = mMovieAppHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case 10: {
                String selectQuery = "select * from " + MovieContract.MovieTable.Table_name;
                cursor = db.rawQuery(selectQuery, null);
                break;
            }
            case 15: {
                String selectQuery = "select * from " + MovieContract.MovieTable.Table_name + " where id =" + selection;
                cursor = db.rawQuery(selectQuery, null);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        if (cursor.isAfterLast()) {
            return null;
        }
        db.close();
        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        db = mMovieAppHelper.getWritableDatabase();
        long row_ID = db.insert(MovieContract.MovieTable.Table_name, null, values);
 //       long row_ID = db.insertWithOnConflict(MovieContract.MovieTable.Table_name, null, values,SQLiteDatabase.CONFLICT_IGNORE);

        if (row_ID > 0) {
            Uri uri_id = ContentUris.withAppendedId(MovieContract.MovieTable.CONTENT_URI, row_ID);
            getContext().getContentResolver().notifyChange(uri_id, null);
            return uri_id;
        }
        throw new SQLException("Failed to insert new Movie ");

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        db = mMovieAppHelper.getWritableDatabase();
        int x = 0;

        switch (uriMatcher.match(uri)) {
            case 10: {
                x = db.delete(MovieContract.MovieTable.Table_name, "id=" + selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return x;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
