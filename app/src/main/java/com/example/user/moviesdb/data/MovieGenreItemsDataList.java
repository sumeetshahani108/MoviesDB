package com.example.user.moviesdb.data;

/**
 * Created by user on 17-01-2017.
 */

public class MovieGenreItemsDataList {
    private String titles ;
    private String release_date ;
    private String description ;

    public MovieGenreItemsDataList(){

    }

    public MovieGenreItemsDataList(String titles, String release_date, String description){
        this.titles = titles;
        this.release_date = release_date;
        this.description = description;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
