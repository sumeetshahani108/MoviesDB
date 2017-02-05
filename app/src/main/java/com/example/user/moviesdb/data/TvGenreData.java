package com.example.user.moviesdb.data;

import java.util.ArrayList;
import java.util.List;


public class TvGenreData {
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

    public static List<TvGenreDataList> getTvGenreDataList(){
        List<TvGenreDataList> data = new ArrayList<>();
        for (int i=0 ; i < genres.length ; i++){
            TvGenreDataList item = new TvGenreDataList();
            item.setGenre(genres[i]);
            item.setId(id[i]);
            data.add(item);
        }
        return data;
    }
}
