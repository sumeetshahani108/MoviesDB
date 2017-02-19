package com.example.user.moviesdb.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 19-02-2017.
 */

public class TvItemData {
    private static final String[] tv  = {
            "The Secret Life of Pets","Suicide Squad","The Secret Life of Pets","Suicide Squad","The Secret Life of Pets","Suicide Squad"
    };

    private static final int[] id = {
            1,2,3,4,5,6
    };

    public static List<TvItemList> getTvItemData(){
        List<TvItemList> data = new ArrayList<>();
        for (int i = 0 ; i < tv.length ; i++){
            TvItemList itemList = new TvItemList();
            itemList.setName(tv[i]);
            itemList.setTv_id(id[i]);
            data.add(itemList);
        }
        return data;
    }

}
