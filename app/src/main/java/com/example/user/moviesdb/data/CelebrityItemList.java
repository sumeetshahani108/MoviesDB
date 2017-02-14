package com.example.user.moviesdb.data;

/**
 * Created by user on 26-01-2017.
 */

public class CelebrityItemList {
    private String name ;
    private int celebrity_id ;

    public CelebrityItemList(){

    }

    public CelebrityItemList(String name, int celebrity_id) {
        this.name = name;
        this.celebrity_id = celebrity_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCelebrity_id() { return celebrity_id; }

    public void setCelebrity_id(int celebrity_id) {
        this.celebrity_id = celebrity_id;
    }
}
