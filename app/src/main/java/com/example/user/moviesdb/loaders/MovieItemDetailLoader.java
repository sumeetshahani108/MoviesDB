package com.example.user.moviesdb.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.user.moviesdb.QueryUtils;
import com.example.user.moviesdb.data.MovieDetailList;

import java.util.List;

/**
 * Created by user on 03-02-2017.
 */

public class MovieItemDetailLoader extends AsyncTaskLoader<List<MovieDetailList>> {
    private static final String LOG_TAG = MovieItemDetailLoader.class.getName();
    private String mUrl ;

    public MovieItemDetailLoader(Context context, String mUrl){
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<MovieDetailList> loadInBackground() {
        if(this.mUrl == null){
            return null;
        }
        List<MovieDetailList> movie_detail = QueryUtils.fetchMovieDetailData(this.mUrl);
        return movie_detail ;
    }
}
