package com.example.user.moviesdb.data;

/**
 * Created by user on 03-02-2017.
 */

public class CelebrityDetailList {
    private String celebrity_name ;
    private String celebrity_biography ;
    private String celebrity_birthday ;
    private double celebrity_popularity ;
    private String celebrity_placeOfBirth ;


    public CelebrityDetailList(String celebrity_name, String celebrity_biography, String celebrity_birthday, double celebrity_popularity, String celebrity_placeOfBirth) {
        this.celebrity_name = celebrity_name;
        this.celebrity_biography = celebrity_biography;
        this.celebrity_birthday = celebrity_birthday;
        this.celebrity_popularity = celebrity_popularity;
        this.celebrity_placeOfBirth = celebrity_placeOfBirth ;
    }

    public String getCelebrity_name() {
        return celebrity_name;
    }

    public void setCelebrity_name(String celebrity_name) {
        this.celebrity_name = celebrity_name;
    }

    public String getCelebrity_biography() {
        return celebrity_biography;
    }

    public void setCelebrity_biography(String celebrity_biography) {
        this.celebrity_biography = celebrity_biography;
    }

    public String getCelebrity_birthday() {
        return celebrity_birthday;
    }

    public void setCelebrity_birthday(String celebrity_birthday) {
        this.celebrity_birthday = celebrity_birthday;
    }

    public double getCelebrity_popularity() {
        return celebrity_popularity;
    }

    public void setCelebrity_popularity(double celebrity_popularity) {
        this.celebrity_popularity = celebrity_popularity;
    }

    public String getCelebrity_placeOfBirth() {
        return celebrity_placeOfBirth;
    }

    public void setCelebrity_placeOfBirth(String celebrity_placeOfBirth) {
        this.celebrity_placeOfBirth = celebrity_placeOfBirth;
    }
}
