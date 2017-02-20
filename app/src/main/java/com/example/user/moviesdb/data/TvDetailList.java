package com.example.user.moviesdb.data;

/**
 * Created by user on 03-02-2017.
 */

public class TvDetailList {
    private String tv_original_name;
    private String tv_overview;
    private String tv_first_air_date;
    private String tv_last_air_date;
    private int tv_no_of_episodes;
    private int tv_no_of_seasons;
    private int vote_average;
    private String tv_in_production;
    private int id;
    private String tv_poster;


    public TvDetailList(int id, String tv_original_name, String tv_overview, String tv_first_air_date, String tv_last_air_date, int vote_average, String tv_in_production, int tv_no_of_episodes, int tv_no_of_seasons, String tv_poster) {

        this.id = id;
        this.tv_original_name = tv_original_name;
        this.tv_overview = tv_overview;
        this.tv_first_air_date = tv_first_air_date ;
        this.tv_last_air_date = tv_last_air_date ;
        this.tv_no_of_episodes = tv_no_of_episodes ;
        this.tv_no_of_seasons = tv_no_of_seasons ;
        this.vote_average = vote_average ;
        this.tv_in_production = tv_in_production ;
        this.tv_poster = tv_poster;
    }

    public int getTv_no_of_seasons() {
        return tv_no_of_seasons;
    }

    public void setTv_no_of_seasons(int tv_no_of_seasons) {
        this.tv_no_of_seasons = tv_no_of_seasons;
    }

    public String getTv_original_name() {
        return tv_original_name;
    }

    public void setTv_original_name(String tv_original_name) {
        this.tv_original_name = tv_original_name;
    }

    public String getTv_overview() {
        return tv_overview;
    }

    public void setTv_overview(String tv_overview) {
        this.tv_overview = tv_overview;
    }

    public String getTv_first_air_date() {
        return tv_first_air_date;
    }

    public void setTv_first_air_date(String tv_first_air_date) {
        this.tv_first_air_date = tv_first_air_date;
    }

    public String getTv_last_air_date() {
        return tv_last_air_date;
    }

    public void setTv_last_air_date(String tv_last_air_date) {
        this.tv_last_air_date = tv_last_air_date;
    }

    public int getTv_no_of_episodes() {
        return tv_no_of_episodes;
    }

    public void setTv_no_of_episodes(int tv_no_of_episodes) {
        this.tv_no_of_episodes = tv_no_of_episodes;
    }

    public int getVote_average() {
        return vote_average;
    }

    public void setVote_average(int vote_average) {
        this.vote_average = vote_average;
    }

    public String getTv_in_production() {
        return tv_in_production;
    }

    public void setTv_in_production(String tv_in_production) {
        this.tv_in_production = tv_in_production;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTv_poster() {
        return tv_poster;
    }

    public void setTv_poster(String tv_poster) {
        this.tv_poster = tv_poster;
    }

}