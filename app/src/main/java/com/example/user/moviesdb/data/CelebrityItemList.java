package com.example.user.moviesdb.data;

/**
 * Created by user on 26-01-2017.
 */

public class CelebrityItemList {
    private String name ;
    private int celebrity_id ;
    private String profile_path;

    public CelebrityItemList(){

    }

    public CelebrityItemList(String name, int celebrity_id, String profile_path) {
        this.name = name;
        this.celebrity_id = celebrity_id;
        this.profile_path = profile_path;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
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
