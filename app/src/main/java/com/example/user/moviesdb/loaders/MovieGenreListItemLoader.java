package com.example.user.moviesdb.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.user.moviesdb.QueryUtils;
import com.example.user.moviesdb.data.MovieGenreItemsDataList;

import java.util.List;

/**
 * Created by user on 18-01-2017.
 */

public class MovieGenreListItemLoader extends AsyncTaskLoader<List<MovieGenreItemsDataList>>{
    private static final String LOG_TAG = MovieGenreListItemLoader.class.getName();
    private String mUrl ;

    public MovieGenreListItemLoader(Context context, String mUrl){
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<MovieGenreItemsDataList> loadInBackground() {
        if(this.mUrl == null){
            return null;
        }
        List<MovieGenreItemsDataList> genre_list = QueryUtils.fetchMovieGenreItemListData(this.mUrl);
        return genre_list;
    }
}



