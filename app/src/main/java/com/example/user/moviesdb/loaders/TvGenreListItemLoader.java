package com.example.user.moviesdb.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.user.moviesdb.QueryUtils;
import com.example.user.moviesdb.data.MovieGenreItemsDataList;
import com.example.user.moviesdb.data.TvGenreItemsDataList;

import java.util.List;

/**
 * Created by user on 18-01-2017.
 */

public class TvGenreListItemLoader extends AsyncTaskLoader<List<TvGenreItemsDataList>>{
    private static final String LOG_TAG = TvGenreListItemLoader.class.getName();
    private String mUrl ;

    public TvGenreListItemLoader(Context context, String mUrl){
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<TvGenreItemsDataList> loadInBackground() {
        if(this.mUrl == null){
            return null;
        }
        List<TvGenreItemsDataList> genre_list = QueryUtils.fetchTvGenreItemListData(this.mUrl);
        return genre_list;
    }
}



