package com.example.user.moviesdb.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 26-01-2017.
 */

public class MovieItemData {
    private static final String[] movies  = {
      "The Secret Life of Pets","Suicide Squad","The Secret Life of Pets","Suicide Squad","The Secret Life of Pets","Suicide Squad"
    };

    private static final int[] id = {
        1,2,3,4,5,6
    };

    public static List<MovieItemList> getMovieItemData(){
        List<MovieItemList> data = new ArrayList<>();
            for (int i = 0 ; i < movies.length ; i++){
                MovieItemList itemList = new MovieItemList();
                itemList.setTitle(movies[i]);
                itemList.setMovie_id(id[i]);
                data.add(itemList);
            }
        return data;
    }

}
