package com.example.user.moviesdb.data;

/**
 * Created by user on 18-02-2017.
 */

public class TvYoutubeVideoList {
    private String name;
    private String key;
    private String type;

    public TvYoutubeVideoList(){

    }

    public TvYoutubeVideoList(String key, String type, String name) {
        this.key = key;
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
