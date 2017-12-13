package com.example.andriod.popularmovies;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Harmoush on 12/13/2017.
 */

public class MovieContentProvider extends ContentProvider{

    private  MovieAppHelper movieAppHelper;
    private SQLiteDatabase database;

    public static final int MOVIES = 100;
    public static final int MOVIE_WITH_ID = 101;
    private static final UriMatcher uriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        final String authority = Contract.CONTENT_AUTHORITY;

        matcher.addURI(authority, Contract.MovieTable.TABLE_NAME,MOVIES);
        matcher.addURI(authority, Contract.MovieTable.TABLE_NAME+ "/id", MOVIE_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {

        Context context = getContext();
        movieAppHelper = new MovieAppHelper(context);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        database = movieAppHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case MOVIES: {
                String selectQuery = "select * from " + Contract.MovieTable.TABLE_NAME;
                cursor = database.rawQuery(selectQuery, null);
                break;
            }
            case MOVIE_WITH_ID: {
                String selectQuery = "select * from " + Contract.MovieTable.TABLE_NAME + " where id =" + selection;
                cursor = database.rawQuery(selectQuery, null);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        if (cursor.isAfterLast()) {
            return null;
        }
        database.close();
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
        database = movieAppHelper.getWritableDatabase();
        Uri returnUri;
        long newRowID = database.insert(Contract.MovieTable.TABLE_NAME, null, values);
        if (newRowID > 0) {
            returnUri = ContentUris.withAppendedId(Contract.MovieTable.URI_FOR_SPECIFIC_ROW, newRowID);
            getContext().getContentResolver().notifyChange(returnUri, null);
            return returnUri;
        }
        throw new SQLException("Failed to insert new Movie ");

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        database = movieAppHelper.getWritableDatabase();
        int count = database.delete(Contract.MovieTable.TABLE_NAME, "MovieID = "+ selection, selectionArgs);

        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
