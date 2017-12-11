package com.example.andriod.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;


/**
 * Created by Harmoush on 10/11/2017.
 */

public class Movie implements Parcelable {
    private String movieTitle;
    private String movieReleaseDate;
    private String movieRate;
    private String movieOverview ;
    private String moviePosterImage;
    private ArrayList<String> movieTrailers;
    private ArrayList<Review> movieReviews;
    private String movieId;

    public Movie() {
    }

    public Movie(String movieTitle, String movieReleaseDate, String movieRate, String movieOverview, String moviePosterImage,String movieId) {
        this.movieTitle = movieTitle;
        this.movieReleaseDate = movieReleaseDate;
        this.movieRate = movieRate;
        this.movieOverview = movieOverview;
        String BASE_URL = "http://image.tmdb.org/t/p/w185/";
        this.moviePosterImage = BASE_URL + moviePosterImage;
        this.movieId = movieId;

    }
    public static class Review implements Parcelable {
        private String reviewAuthor;
        private String reviewContent;

        public Review(String reviewAuthor, String reviewContent) {
            this.reviewAuthor = reviewAuthor;
            this.reviewContent = reviewContent;
        }

        public String getReviewAuthor() {
            return reviewAuthor;
        }

        public String getReviewContent() {
            return reviewContent;
        }

        protected Review(Parcel in) {
            reviewAuthor = in.readString();
            reviewContent = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(reviewAuthor);
            dest.writeString(reviewContent);
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
            @Override
            public Review createFromParcel(Parcel in) {
                return new Review(in);
            }

            @Override
            public Review[] newArray(int size) {
                return new Review[size];
            }
        };
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public String getMovieRate() {
        return movieRate;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public String getMoviePosterImage() {
        return moviePosterImage;
    }

    public ArrayList<String> getMovieTrailers() {
        return movieTrailers;
    }

    public ArrayList<Review> getMovieReviews() {
        return movieReviews;
    }

    public String getMovieId() {
        return movieId;
    }

    protected Movie(Parcel in) {
        movieTitle = in.readString();
        movieReleaseDate = in.readString();
        movieRate = in.readString();
        movieOverview = in.readString();
        moviePosterImage = in.readString();
        if (in.readByte() == 0x01) {
            movieTrailers = new ArrayList<String>();
            in.readList(movieTrailers, String.class.getClassLoader());
        } else {
            movieTrailers = null;
        }
        if (in.readByte() == 0x01) {
            movieReviews = new ArrayList<Review>();
            in.readList(movieReviews, Review.class.getClassLoader());
        } else {
            movieReviews = null;
        }
        movieId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieTitle);
        dest.writeString(movieReleaseDate);
        dest.writeString(movieRate);
        dest.writeString(movieOverview);
        dest.writeString(moviePosterImage);
        if (movieTrailers == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(movieTrailers);
        }
        if (movieReviews == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(movieReviews);
        }
        dest.writeString(movieId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}