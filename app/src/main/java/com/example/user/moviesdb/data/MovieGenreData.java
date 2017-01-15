package com.example.user.moviesdb.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 15-01-2017.
 */

public class MovieGenreData {
    private static final String[] genres = {
            "Action",
            "Adventure",
            "Animation",
            "Comedy",
            "Action",
            "Adventure",
            "Animation",
            "Comedy",
            "Action",
            "Adventure",
            "Animation",
            "Comedy"
    };

    public static List<MovieGenreDataList> getMovieGenreListData(){
        List<MovieGenreDataList> data = new ArrayList<>();
        for (int i=0 ; i < genres.length ; i++){
            MovieGenreDataList item = new MovieGenreDataList();
            item.setGenre(genres[i]);
        }
        return data;
    }
}
