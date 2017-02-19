package com.example.user.moviesdb.data;

/**
 * Created by user on 26-01-2017.
 */

public class TvItemList {
    private String name ;
    private int tv_id ;

    public TvItemList(){

    }

    public TvItemList(String name, int tv_id) {
        this.name = name;
        this.tv_id = tv_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTv_id() {
        return tv_id;
    }

    public void setTv_id(int movie_id) {
        this.tv_id = tv_id;
    }
}
