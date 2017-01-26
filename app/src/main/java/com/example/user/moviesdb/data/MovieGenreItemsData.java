package com.example.user.moviesdb.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 17-01-2017.
 */

public class MovieGenreItemsData {
    private static final String[] titles = {
            "Suicide Squad",
            "Rogue One: A Star Wars Story",
            "Jurassic World",
            "Suicide Squad",
            "Rogue One: A Star Wars Story",
            "Jurassic World"
    };

    private static final String[] release_dates = {
            "2016-08-02",
            "2016-12-14",
            "2016-12-21",
            "2016-08-02",
            "2016-12-14",
            "2016-12-21"
    };

    private static final String[] description = {
            "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
            "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
            "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
            "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
            "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
            "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences."
    };

    public static final List<MovieGenreItemsDataList> getMovieGenreItemListData(){
        List<MovieGenreItemsDataList> data = new ArrayList<>();
            for (int i = 0 ; i < titles.length ; i++){
                MovieGenreItemsDataList item = new MovieGenreItemsDataList();
                item.setTitles(titles[i]);
                item.setRelease_date(release_dates[i]);
                item.setDescription(description[i]);
                data.add(item);
            }
        return data;
    }
}
