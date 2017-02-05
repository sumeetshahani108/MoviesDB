package com.example.user.moviesdb.data;

/**
 * Created by user on 03-02-2017.
 */

public class MovieDetailList {
    private String movie_title ;
    private String movie_description ;
    private String release_date ;
    private int vote_average ;


    public MovieDetailList(String movie_title, String movie_description, String release_date, int vote_average) {
        this.movie_title = movie_title;
        this.movie_description = movie_description;
        this.release_date = release_date;
        this.vote_average = vote_average;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getMovie_description() {
        return movie_description;
    }

    public void setMovie_description(String movie_description) {
        this.movie_description = movie_description;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getVote_average() {
        return vote_average;
    }

    public void setVote_average(int vote_average) {
        this.vote_average = vote_average;
    }
}
