package com.example.user.moviesdb.data;

/**
 * Created by user on 19-02-2017.
 */

public class PersonFavourites {
    private String movie_title;
    private String movie_poster;

    public PersonFavourites(){

    }

    public PersonFavourites(String movie_title, String movie_poster) {
        this.movie_title = movie_title;
        this.movie_poster = movie_poster;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getMovie_poster() {
        return movie_poster;
    }

    public void setMovie_poster(String movie_poster) {
        this.movie_poster = movie_poster;
    }
}
