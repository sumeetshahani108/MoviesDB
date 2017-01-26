package com.example.user.moviesdb.data;

/**
 * Created by user on 26-01-2017.
 */

public class MovieItemList {
    private String title ;
    private int movie_id ;

    public MovieItemList(){

    }

    public MovieItemList(String title, int movie_id) {
        this.title = title;
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }
}
