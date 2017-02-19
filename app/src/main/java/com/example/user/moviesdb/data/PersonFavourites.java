package com.example.user.moviesdb.data;

/**
 * Created by user on 19-02-2017.
 */

public class PersonFavourites {
    private String movie_title;
    private String movie_poster_path;

    public PersonFavourites(){

    }

    public PersonFavourites(String movie_title, String movie_poster_path) {
        this.movie_title = movie_title;
        this.movie_poster_path = movie_poster_path;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getMovie_poster_path() {
        return movie_poster_path;
    }

    public void setMovie_poster_path(String movie_poster_path) {
        this.movie_poster_path = movie_poster_path;
    }
}
