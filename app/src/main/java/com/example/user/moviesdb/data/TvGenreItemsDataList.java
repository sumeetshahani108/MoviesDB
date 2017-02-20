package com.example.user.moviesdb.data;

/**
 * Created by user on 17-01-2017.
 */

public class TvGenreItemsDataList {
    private String titles ;
    private String release_date ;
    private String description ;
    private String tv_poster ;

    public TvGenreItemsDataList(){

    }

    public TvGenreItemsDataList(String titles, String release_date, String description, String tv_poster){
        this.titles = titles;
        this.release_date = release_date;
        this.description = description;
        this.tv_poster = tv_poster ;
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


    public String getTv_poster() {
        return tv_poster;
    }

    public void setTv_poster(String tv_poster) {
        this.tv_poster = tv_poster;
    }
}
