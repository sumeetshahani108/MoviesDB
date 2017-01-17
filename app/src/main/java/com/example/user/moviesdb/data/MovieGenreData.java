package com.example.user.moviesdb.data;

import java.util.ArrayList;
import java.util.List;


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

    private static final int[] id = {
            1,2,3,4,1,2,3,4,1,2,3,4
    };

    public static List<MovieGenreDataList> getMovieGenreListData(){
        List<MovieGenreDataList> data = new ArrayList<>();
            for (int i=0 ; i < genres.length ; i++){
                MovieGenreDataList item = new MovieGenreDataList();
                item.setGenre(genres[i]);
                item.setId(id[i]);
                data.add(item);
            }
        return data;
    }
}
