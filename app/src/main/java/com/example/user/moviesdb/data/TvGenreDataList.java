package com.example.user.moviesdb.data;

/**
 * Created by user on 15-01-2017.
 */

public class TvGenreDataList {
    private String genre;
    private int id;

    public TvGenreDataList(){

    }

    public TvGenreDataList(String genre, int id) {
        this.genre = genre;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
