package com.example.user.moviesdb.loaders;

import 	android.support.v4.content.AsyncTaskLoader ;
import android.content.Context;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.user.moviesdb.QueryUtils;
import com.example.user.moviesdb.data.TvGenreDataList;


import java.util.List;


public class TvGenreListLoader extends AsyncTaskLoader<List<TvGenreDataList>> {
    private static final String LOG_TAG = MovieGenreListLoader.class.getName();
    private String mUrl ;

    public TvGenreListLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<TvGenreDataList> loadInBackground() {
        if(this.mUrl == null){
            return null;
        }
        Log.d(LOG_TAG, this.mUrl);
        List<TvGenreDataList> genre_list = QueryUtils.fetchTvGenreListData(this.mUrl);
        return genre_list;
    }
}
