package com.example.user.moviesdb.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 26-01-2017.
 */

public class TvItemList {
    private String name ;
    private int tv_id ;
    private String image_path;

    public TvItemList(){

    }

    public TvItemList(String name, int tv_id, String image_path) {
        this.name = name;
        this.tv_id = tv_id;
        this.image_path = image_path;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
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

    public void setTv_id(int tv_id) {
        this.tv_id = tv_id;
    }

}
