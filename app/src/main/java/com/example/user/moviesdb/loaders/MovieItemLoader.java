package com.example.user.moviesdb.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.user.moviesdb.QueryUtils;
import com.example.user.moviesdb.data.MovieItemList;

import java.util.List;

public class MovieItemLoader extends AsyncTaskLoader<List<MovieItemList>> {
    private static final String LOG_TAG = MovieItemLoader.class.getName();
    private String mUrl ;

    public MovieItemLoader(Context context, String mUrl){
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<MovieItemList> loadInBackground() {
        if (this.mUrl == null){
            return null;
        }
        Log.d(LOG_TAG, this.mUrl+"");
        List<MovieItemList> movie_list = QueryUtils.fetchMovieListData(this.mUrl);
        return movie_list;
    }
}
