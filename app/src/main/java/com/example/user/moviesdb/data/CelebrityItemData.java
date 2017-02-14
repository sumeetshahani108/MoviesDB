package com.example.user.moviesdb.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 26-01-2017.
 */

public class CelebrityItemData {
    private static final String[] name  = {
            "David Beckham","Taylor Lautner","Leonardo Dicaprio"
    };

    private static final int[] id = {
            1,2,3
    };

    public static List<CelebrityItemList> getCelebrityItemData(){
        List<CelebrityItemList> data = new ArrayList<>();
        for (int i = 0 ; i < name.length ; i++){
            CelebrityItemList itemList = new CelebrityItemList();
            itemList.setName(name[i]);
            itemList.setCelebrity_id(id[i]);
            data.add(itemList);
        }
        return data;
    }

}
